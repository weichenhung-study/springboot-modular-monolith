package com.ntou.creditcard.transactions.transaction;

import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.CuscreditSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    BillrecordSvc billrecordSvc;

    @Autowired
    CuscreditSvc cuscreditSvc;

    @PostMapping("/Transaction")
    public ResponseEntity<TransactionRes> doController(@RequestBody TransactionReq req) throws Exception {
        return new Transaction().doAPI(req, billrecordSvc, cuscreditSvc);
    }
}