package com.ntou.creditcard.billing.feepayment;

import com.ntou.db.billofmonth.Billofmonth;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.cuscredit.Cuscredit;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.DateTool;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/** 客戶繳交信用卡費 */
@Log4j2
@NoArgsConstructor
public class FeePayment {
    public ResponseEntity<FeePaymentRes> doAPI(FeePaymentReq req, BillofmonthSvc billofmonthSvc
            , CuscreditSvc cuscreditSvc) throws Exception {
		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());

        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        FeePaymentRes res = new FeePaymentRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, FeePaymentRC.T171A.getCode(), FeePaymentRC.T171A.getContent(), req.getErrMsg());
        
		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());
        Billofmonth vo = setUpdatePayDate(req);
        List<Billofmonth> listBillofmonth = billofmonthSvc.findBills(vo);
        if (listBillofmonth.size() == 1){
            int notPaidAmount = Integer.parseInt(listBillofmonth.get(0).getAmt()) - Integer.parseInt(req.getPayAmt());
            vo.setNotPaidAmount(String.valueOf(notPaidAmount));

            billofmonthSvc.updatePaid(vo, listBillofmonth.get(0).getUuid());
            sendMail(req, listBillofmonth.get(0), cuscreditSvc);
        } else
            ResTool.commonThrow(res, FeePaymentRC.T171D.getCode(), FeePaymentRC.T171D.getContent());
        ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());

        ResTool.setRes(res, FeePaymentRC.T1710.getCode(), FeePaymentRC.T1710.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());
        ExecutionTimer.exportTimings(this.getClass().getSimpleName() + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    private void sendMail(FeePaymentReq req, Billofmonth key, CuscreditSvc cuscreditSvc) {
        MailVO vo = new MailVO();
        Cuscredit voCuscredit = cuscreditSvc.selectKey(req.getCid(), req.getCardType());
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("信用卡繳費通知");
        vo.setContent("<h1>收到您的信用卡費</h1>" +
                "帳單月份：" + key.getBillMonth() +"<br>" +
                "當月帳單應繳金額：" + key.getAmt() +"<br>" +
                "當月繳款金額：" + key.getPaidAmount() +"<br>" +
                "剩餘未繳金額：" + key.getNotPaidAmount() +"<br>" +
                "繳款時間：" + key.getPayDate() +"<br>"
        );
        new JavaMail().sendMail(vo);
    }
    private Billofmonth setUpdatePayDate(FeePaymentReq req){
        Billofmonth vo = new Billofmonth();
        vo.setCid(req.getCid());
        vo.setCardType(req.getCardType());
        vo.setPayDate(DateTool.getDateTime());
        vo.setPaidAmount(req.getPayAmt());
        vo.setBillMonth(req.getPayDate());
        return vo;
    }
}
