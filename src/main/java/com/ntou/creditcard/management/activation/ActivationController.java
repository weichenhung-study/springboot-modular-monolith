package com.ntou.creditcard.management.activation;

import com.ntou.db.cuscredit.CuscreditSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivationController {

    @Autowired
    CuscreditSvc cuscreditSvc;

    @PutMapping("/Activation")
    public ResponseEntity<ActivationRes> doController(@RequestBody ActivationReq req) throws Exception {
        return new Activation().doAPI(req, cuscreditSvc);
    }
}