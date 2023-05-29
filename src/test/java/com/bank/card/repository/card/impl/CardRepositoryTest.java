package com.bank.card.repository.card.impl;

import com.bank.card.domain.entity.Card;
import com.bank.card.repository.card.ICardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.bank.card.util.Factories.buildCard;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardRepositoryTest {

    @InjectMocks
    CardRepository cardRepository;

    @Mock
    private ICardRepository repository;

    @Test
    void findByNumber() {
        when(repository.findByNumber(any())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> {
            cardRepository.findByNumber("5200828282828210");
        });
    }

    @Test
    void update() {
        when(repository.save(any())).thenReturn(new Card());
        assertDoesNotThrow(() -> {
            cardRepository.save(buildCard(true, false, new BigDecimal(200)));
        });
    }

    @Test
    void save() {
        when(repository.save(any())).thenReturn(new Card());
        assertDoesNotThrow(() -> {
            cardRepository.update(buildCard(true, false, new BigDecimal(200)));
        });
    }
}
