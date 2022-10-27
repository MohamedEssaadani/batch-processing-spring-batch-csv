package org.essaadani.springbatchbankapp;

import org.essaadani.springbatchbankapp.entities.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return bankTransaction;
    }
}
