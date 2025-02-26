package com.ntou.db.billofmonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillofmonthRepo extends JpaRepository<Billofmonth, String>{
    List<Billofmonth> findByCidAndCardTypeAndBillMonth(String cid, String cardType, String billMonth);
    Billofmonth findByUuid(String uuid);
}
