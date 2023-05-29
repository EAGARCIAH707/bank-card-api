package com.bank.card.repository.transaction;

import com.bank.card.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
