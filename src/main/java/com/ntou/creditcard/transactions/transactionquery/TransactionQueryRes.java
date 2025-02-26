package com.ntou.creditcard.transactions.transactionquery;

import com.ntou.db.billrecord.Billrecord;
import com.ntou.spec.SvcRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionQueryRes extends SvcRes {
    private List<Billrecord> result;
}