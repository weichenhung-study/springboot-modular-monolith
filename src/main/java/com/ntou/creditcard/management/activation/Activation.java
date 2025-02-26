package com.ntou.creditcard.management.activation;

import com.ntou.db.cuscredit.Cuscredit;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditTool;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** 信用卡開卡 */
@Log4j2
@NoArgsConstructor
public class Activation {
    public ResponseEntity<ActivationRes> doAPI(ActivationReq req, CuscreditSvc cuscreditSvc) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        ActivationRes res = new ActivationRes();

         if(!req.checkReq())
             ResTool.regularThrow(res, ActivationRC.T131A.getCode(), ActivationRC.T131A.getContent(), req.getErrMsg());

         Cuscredit voCuscredit = cuscreditSvc.selectKey(req.getCid(), req.getCardType());
         cuscreditSvc.updateActivationRecord(CuscreditTool.ActivationRecord.OPEN.getValue(),voCuscredit);
         MailVO vo = new MailVO();
         vo.setEmailAddr(voCuscredit.getEmail());
         vo.setSubject("信用卡開卡完成");
         vo.setContent("<h1>您申請的信用卡已開卡完成</h1><h2>歡迎使用!</h2>");
         new JavaMail().sendMail(vo);

        ResTool.setRes(res, ActivationRC.T1310.getCode(), ActivationRC.T1310.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
