package com.ntou.creditcard.dispute.disputenotation;

import com.ntou.db.billrecord.Billrecord;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.tool.Common;
import com.ntou.tool.DateTool;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** 爭議款項申請:上註記 */
@Log4j2
@NoArgsConstructor
public class DisputeNotation {
    public ResponseEntity<DisputeNotationRes> doAPI(DisputeNotationReq req, BillrecordSvc billrecordSvc) throws Exception {
        ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());

		log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        DisputeNotationRes res = new DisputeNotationRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, DisputeNotationRC.T162A.getCode(), DisputeNotationRC.T162A.getContent(), req.getErrMsg());

        ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());
        Billrecord updateResult = billrecordSvc.updateDisputedFlag(voBillrecordSelect(req));
        if(updateResult == null)
            ResTool.commonThrow(res, DisputeNotationRC.T162C.getCode(), DisputeNotationRC.T162C.getContent());
        ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());
		
		ResTool.setRes(res, DisputeNotationRC.T1620.getCode(), DisputeNotationRC.T1620.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());
        ExecutionTimer.exportTimings(this.getClass().getSimpleName() + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
		return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    private Billrecord voBillrecordSelect(DisputeNotationReq req){
        Billrecord vo = new Billrecord();
        vo.setUuid		(req.getUuid());
        vo.setDisputedFlag("01");
        return vo;
    }
}
