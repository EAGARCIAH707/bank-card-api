package com.bank.card.repository.card;

import com.bank.card.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(String cardNumber);

}

