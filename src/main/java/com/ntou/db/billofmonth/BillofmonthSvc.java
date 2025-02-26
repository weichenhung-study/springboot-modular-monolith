package com.ntou.db.billofmonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillofmonthSvc {
    @Autowired
    private BillofmonthRepo repo;

    public void saveBillofmonth(Billofmonth model) {
        repo.save(model);
    }
    public List<Billofmonth> findBills(Billofmonth vo) {
        return repo.findByCidAndCardTypeAndBillMonth(vo.getCid(), vo.getCardType(), vo.getBillMonth());
    }
    public Billofmonth findKey(String uuid) {
        return repo.findByUuid(uuid);
    }
    public void updatePaid(Billofmonth vo, String uuid) {
        Billofmonth condition = repo.findByUuid(uuid);
        condition.setCid        (vo.getCid       ());
        condition.setCardType   (vo.getCardType  ());
        condition.setPayDate    (vo.getPayDate   ());
        condition.setPaidAmount (vo.getPaidAmount());
        condition.setBillMonth  (vo.getBillMonth ());
        repo.save(condition);
    }
}