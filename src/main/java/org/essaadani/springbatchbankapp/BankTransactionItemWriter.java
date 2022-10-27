package org.essaadani.springbatchbankapp;

import org.essaadani.springbatchbankapp.entities.BankTransaction;
import org.essaadani.springbatchbankapp.repositories.BanktransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired
    BanktransactionRepository banktransactionRepository;

    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        System.out.println("Writing...");
        System.out.println(list);
        banktransactionRepository.saveAll(list);
    }
}
