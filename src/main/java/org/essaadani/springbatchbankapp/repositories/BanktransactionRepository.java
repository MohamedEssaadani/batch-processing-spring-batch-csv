package org.essaadani.springbatchbankapp.repositories;

import org.essaadani.springbatchbankapp.entities.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanktransactionRepository extends JpaRepository<BankTransaction, Long> {
}
