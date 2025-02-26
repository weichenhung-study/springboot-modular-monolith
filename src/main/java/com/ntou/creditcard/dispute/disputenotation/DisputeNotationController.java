package com.ntou.creditcard.dispute.disputenotation;

import com.ntou.db.billrecord.BillrecordSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisputeNotationController {

    @Autowired
    BillrecordSvc billrecordSvc;

    @PutMapping("/DisputeNotation")
    public ResponseEntity<DisputeNotationRes> doController(@RequestBody DisputeNotationReq req) throws Exception {
        return new DisputeNotation().doAPI(req, billrecordSvc);
    }
}