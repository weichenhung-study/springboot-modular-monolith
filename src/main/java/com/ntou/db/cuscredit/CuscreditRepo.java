package com.ntou.db.cuscredit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuscreditRepo extends JpaRepository<Cuscredit, String>{
    Cuscredit findByCidAndCardType(String cid, String cardType);
    Cuscredit findByCidAndCardTypeAndCardNum(String cid, String cardType, String cardNum);
}
