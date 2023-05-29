package com.bank.card.repository.transaction.impl;

import com.bank.card.domain.entity.Transaction;
import com.bank.card.repository.transaction.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionRepository implements ITransactionRepositoryFacade {
    private final ITransactionRepository repository;

    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return repository.findById(transactionId);
    }

    @Override
    public void update(Transaction transaction) {
        repository.save(transaction);
    }
}
