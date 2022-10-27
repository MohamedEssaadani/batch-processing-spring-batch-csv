package org.essaadani.springbatchbankapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {
    @Id
    private  Long id;
    private Long accountId;
    private Date transactionDate;
    @Transient
    private String strTransactionDate;
    private String transactionType;
    private double amount;
}
