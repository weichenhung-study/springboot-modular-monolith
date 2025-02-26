package com.ntou.creditcard.transactions.transaction;

import com.ntou.db.billrecord.Billrecord;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.Cuscredit;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.DateTool;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/** 使用信用卡購物 */
@Log4j2
@NoArgsConstructor
public class Transaction {
    public ResponseEntity<TransactionRes> doAPI(TransactionReq req
            , BillrecordSvc billrecordSvc, CuscreditSvc cuscreditSvc) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        TransactionRes res = new TransactionRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, TransactionRC.T141A.getCode(), TransactionRC.T141A.getContent(), req.getErrMsg());

        Cuscredit voCuscredit = cuscreditSvc.findCardHolderActivated(req.getCid(), req.getCardType(), req.getCardNum(), req.getSecurityCode());

        //check客戶是否存在且開卡完成
        if(voCuscredit==null)
            ResTool.commonThrow(res, TransactionRC.T141D.getCode(), TransactionRC.T141D.getContent());

        billrecordSvc.saveBillrecord(voBillrecordInsert(req));

        MailVO vo = new MailVO();
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("您今天有一筆信用卡消費");
        vo.setContent("<h1>您消費紀錄如下</h1>" +
                "<h2>"
                + "消費時間：" + req.getBuyDate()     +"<br>"
                + "消費店家：" + req.getShopId()      +"<br>"
                + "消費幣別：" + req.getBuyCurrency() +"<br>"
                + "消費金額：" + req.getBuyAmount()   +"<br>"
                +"</h2>"
        );
        new JavaMail().sendMail(vo);

        ResTool.setRes(res, TransactionRC.T1410.getCode(), TransactionRC.T1410.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    private Billrecord voBillrecordInsert(TransactionReq req){
        Billrecord vo = new Billrecord();
        vo.setUuid				(UUID.randomUUID().toString());//交易編號UUID
        vo.setBuyChannel		(req.getBuyChannel		());//消費通路(00:實體, 01:線上)
        vo.setBuyDate			(req.getBuyDate			());//消費時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setReqPaymentDate	(req.getReqPaymentDate	());//請款時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setCardType			(req.getCardType		());//卡別
        vo.setShopId			(req.getShopId			());//消費店家(統編)
        vo.setCid				(req.getCid				());//消費者(身分證)
        vo.setBuyCurrency		(req.getBuyCurrency		());//消費幣別
        vo.setBuyAmount			(req.getBuyAmount		());//消費金額
        vo.setDisputedFlag		(req.getDisputedFlag	());//爭議款項註記(00:正常,01異常)
        vo.setStatus			(req.getStatus			());//狀態(00:正常,99:註銷)
        vo.setActuallyDate		(DateTool.getDateTime());//交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setRemark			(req.getRemark			());//備註
        vo.setIssuingBank		(req.getIssuingBank		());//發卡銀行(swiftCode)
        vo.setCardNum			(req.getCardNum			());//卡號
        vo.setSecurityCode		(req.getSecurityCode	());//安全碼
        return vo;
    }
}
