package com.ntou.creditcard.billing.feepayment;

import com.ntou.creditcard.billing.feepayment.FeePaymentRes;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.cuscredit.CuscreditSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeePaymentController {

    @Autowired
    BillofmonthSvc billofmonthSvc;

    @Autowired
    CuscreditSvc cuscreditSvc;

    @PostMapping("/FeePayment")
    public ResponseEntity<FeePaymentRes> doController(@RequestBody FeePaymentReq req) throws Exception {
        return new FeePayment().doAPI(req, billofmonthSvc, cuscreditSvc);
    }
}