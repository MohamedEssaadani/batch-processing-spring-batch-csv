package org.essaadani.springbatchbankapp;

import lombok.Getter;
import org.essaadani.springbatchbankapp.entities.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

//@Component
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    @Getter
    private double totalDebit=0;
    @Getter
    private double totalCredit=0;

    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        if(bankTransaction.getTransactionType().equals("D"))
            totalDebit += bankTransaction.getAmount();
        else if(bankTransaction.getTransactionType().equals("C"))
            totalCredit += bankTransaction.getAmount();
        return bankTransaction;
    }
}
