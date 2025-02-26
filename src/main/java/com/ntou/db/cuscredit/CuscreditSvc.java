package com.ntou.db.cuscredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuscreditSvc {
    @Autowired
    private CuscreditRepo repo;

    public Cuscredit selectKey(String cid, String cardType) {
        return repo.findByCidAndCardType(cid, cardType);
    }
    public Cuscredit findCardHolderActivated(String cid, String cardType, String cardNum, String securityCode) {
        Cuscredit cuscreditVO  = repo.findByCidAndCardType(cid, cardType);
        if(cuscreditVO.getCardNum().equals(cardNum) && cuscreditVO.getSecurityCode().equals(securityCode)) {
            return cuscreditVO;
        }
        return null;
    }
    public void saveCuscredit(Cuscredit model) {
        repo.save(model);
    }

    public void updateCardApprovalStatus(Cuscredit vo) {
        Cuscredit condition = repo.findByCidAndCardType(vo.getCid(),vo.getCardType());
        condition.setCardApprovalStatus (vo.getCardApprovalStatus());
        condition.setCardNum            (vo.getCardNum           ());
        condition.setSecurityCode       (vo.getSecurityCode      ());
        condition.setApplyRemark        (vo.getApplyRemark       ());
        condition.setStatus		        (vo.getStatus		     ());
        repo.save(condition);
    }
    public void updateActivationRecord(String activationRecord, Cuscredit condition) {
        condition.setActivationRecord(activationRecord);
        repo.save(condition);
    }
}