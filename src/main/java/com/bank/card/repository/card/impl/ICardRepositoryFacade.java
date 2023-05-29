package com.bank.card.repository.card.impl;

import com.bank.card.domain.entity.Card;

import java.util.Optional;

public interface ICardRepositoryFacade {

    Optional<Card> findByNumber(String cardNumber);

    void update(Card card);

    void save(Card card);
}
