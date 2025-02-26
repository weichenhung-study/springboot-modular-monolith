package com.ntou.db.billrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillrecordRepo extends JpaRepository<Billrecord, String>{
    List<Billrecord> findByBuyDateBetweenAndDisputedFlag(
            String startDate, String endDate, String disputedFlag);
    List<Billrecord> findByCidAndCardTypeAndBuyDateBetweenOrderByBuyDateDesc(
            String cid, String cardType, String startDate, String endDate);
    Billrecord findByUuid(String uuid);
}
