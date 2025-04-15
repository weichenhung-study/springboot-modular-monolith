package com.ntou.creditcard.billing.generatebill;

import com.ntou.db.billofmonth.Billofmonth;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.billrecord.Billrecord;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.Cuscredit;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.DateTool;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/** 結信用卡費：該月1日至月底之交易金額 */
@Log4j2
@NoArgsConstructor
public class GenerateBill {
    public ResponseEntity<GenerateBillRes> doAPI(
            BillofmonthSvc billofmonthSvc, BillrecordSvc billrecordSvc
            , CuscreditSvc cuscreditSvc) throws Exception {
		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());

        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        GenerateBillRes res = new GenerateBillRes();

		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());

//      1. 到資料庫找到要寄送帳單的所有客戶
        List<Billrecord> billList = billrecordSvc.selectCusBill(voBillrecordSelect(), DateTool.getFirstDayOfMonth(), DateTool.getLastDayOfMonth());
//      2. 整理,將資料以卡身分證和卡別分組
        Map<String, List<Billrecord>> groupedData = billList.stream()
                .collect(Collectors.groupingBy(t -> t.getCid() + t.getCardType()));//.collect(Collectors.groupingBy(Billrecord::getCid));

		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());

        String yyyymm = DateTool.getFirstDayOfMonth().substring(0,7);
        MailVO vo = new MailVO();
        for (Map.Entry<String, List<Billrecord>> entry : groupedData.entrySet()) {
            String key = entry.getKey();
            List<Billrecord> value = entry.getValue();
            String cid = key.substring(0, 10);
            String cardType = key.substring(10, 11);
            Cuscredit voCuscredit = cuscreditSvc.selectKey(cid, cardType);

            vo.setEmailAddr(voCuscredit.getEmail());
            vo.setSubject(cid + "卡別" + cardType + "的" + yyyymm + "月信用卡帳單");
            StringBuilder resultStr = new StringBuilder("<h1>您" + yyyymm + "月帳單，消費紀錄如下</h1>");
            AtomicLong amt = new AtomicLong();

            for (Billrecord action : value) {
                String str = "消費時間：" + action.getBuyDate() + "<br>"
                        + "消費店家：" + action.getShopId() + "<br>"
                        + "消費幣別：" + action.getBuyCurrency() + "<br>"
                        + "消費金額：" + action.getBuyAmount() + "<br>"
                        + "<br>";
                resultStr.append(str);
                amt.addAndGet(Integer.parseInt(action.getBuyAmount()));
            }
            resultStr.append("消費總金額為：").append(amt.get());
            vo.setContent(String.valueOf(resultStr));
            new JavaMail().sendMail(vo);

            Billofmonth voBillofmonth = setBillofmonthVO(cid, cardType, value, String.valueOf(amt), yyyymm);
            List<Billofmonth> bills = billofmonthSvc.findBills(voBillofmonth);
            if (!bills.isEmpty())
                ResTool.commonThrow(res, GenerateBillRC.T151C.getCode(), GenerateBillRC.T151C.getContent());

            billofmonthSvc.saveBillofmonth(voBillofmonth);
        }

        ResTool.setRes(res, GenerateBillRC.T1510.getCode(), GenerateBillRC.T1510.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());
        ExecutionTimer.exportTimings(this.getClass().getSimpleName() + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    private Billofmonth setBillofmonthVO(String cid, String cardType, List<Billrecord> billList, String amt, String yyyymm){
        Billofmonth vo = new Billofmonth();
        vo.setUuid	            (UUID.randomUUID().toString());//VARCHAR(36)	交易編號UUID
        vo.setCid	            (cid			);//VARCHAR(10)	消費者(身分證)
        vo.setCardType	        (cardType		);//VARCHAR(5)	卡別
        vo.setWriteDate	        (DateTool.getDateTime		());//VARCHAR(23)	寫入時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setBillData	        (billList.toString      ());//VARCHAR(255)	當月所有帳單資訊
        vo.setBillMonth          (yyyymm);
        vo.setAmt	            (amt);//VARCHAR(255)	帳單金額
        vo.setPaidAmount	    ("");//VARCHAR(255)	當月繳款金額
        vo.setNotPaidAmount	    ("");//VARCHAR(255)	剩餘未繳金額
        vo.setCycleRate	        ("");//VARCHAR(100)	循環利率
        vo.setCycleAmt	        ("");//VARCHAR(255)	循環金額
        vo.setSpaceCycleRate	("");//VARCHAR(100)	討論循環利率
        vo.setSpaceAmt	        ("");//VARCHAR(255)	討論金額
        vo.setPayDate("");
        return vo;
    }
    private Billrecord voBillrecordSelect(){
        Billrecord vo = new Billrecord();
        vo.setDisputedFlag      ("00");
        return vo;
    }
}
