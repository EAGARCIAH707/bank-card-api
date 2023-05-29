package com.bank.card.repository.transaction.impl;

import com.bank.card.domain.entity.Transaction;

import java.util.Optional;

public interface ITransactionRepositoryFacade {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long transactionId);


    void update(Transaction transaction);
}
