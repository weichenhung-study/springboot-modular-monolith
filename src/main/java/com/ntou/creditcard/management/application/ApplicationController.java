package com.ntou.creditcard.management.application;

import com.ntou.db.cuscredit.CuscreditSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    CuscreditSvc cuscreditSvc;

    @PostMapping("/Application")
    public ResponseEntity<ApplicationRes> doController(@RequestBody ApplicationReq req) throws Exception {
        return new Application().doAPI(req, cuscreditSvc);
    }
}