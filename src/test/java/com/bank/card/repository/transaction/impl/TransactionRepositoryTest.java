package com.bank.card.repository.transaction.impl;

import com.bank.card.domain.entity.Transaction;
import com.bank.card.repository.transaction.ITransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bank.card.util.Factories.buildTransaction;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    @InjectMocks
    TransactionRepository transactionRepository;

    @Mock
    private ITransactionRepository repository;

    @Test
    void save() {
        when(repository.save(any())).thenReturn(new Transaction());
        assertDoesNotThrow(() -> {
            transactionRepository.save(buildTransaction());
        });
    }

    @Test
    void findById() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> {
            transactionRepository.findById(1L);
        });
    }

    @Test
    void update() {
        when(repository.save(any())).thenReturn(new Transaction());
        assertDoesNotThrow(() -> transactionRepository.update(buildTransaction()));
    }
}
