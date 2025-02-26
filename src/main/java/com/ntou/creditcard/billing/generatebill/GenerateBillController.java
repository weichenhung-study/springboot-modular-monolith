package com.ntou.creditcard.billing.generatebill;

import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.creditcard.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateBillController extends BaseController {

    @Autowired
    BillofmonthSvc billofmonthSvc;

    @Autowired
    BillrecordSvc billrecordSvc;
    
	@Autowired
    CuscreditSvc cuscreditSvc;

    public ResponseEntity<GenerateBillRes> doController() throws Exception {
        return new GenerateBill().doAPI(billofmonthSvc, billrecordSvc, cuscreditSvc);
    }
}