package com.bank.card.repository.card.impl;

import com.bank.card.domain.entity.Card;
import com.bank.card.repository.card.ICardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardRepository implements ICardRepositoryFacade {

    private final ICardRepository repository;

    @Override
    public Optional<Card> findByNumber(String cardNumber) {
        return repository.findByNumber(cardNumber);
    }

    @Override
    public void update(Card card) {
        repository.save(card);
    }

    @Override
    public void save(Card card) {
        repository.save(card);
    }
}
