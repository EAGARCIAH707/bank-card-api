package com.bank.card.util;

import com.bank.card.domain.entity.Card;
import com.bank.card.domain.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Factories {

    public static Card buildCard(boolean isActive, boolean isBlocked, BigDecimal balance) {
        return Card.builder()
                .productId("102030")
                .number("5200828282828210")
                .holderName("John Doe")
                .expirationDate("05/2026")
                .isActive(isActive)
                .isBlocked(isBlocked)
                .balance(balance)
                .build();
    }

    public static Transaction buildTransaction(Card card) {
        return Transaction.builder()
                .transactionId(10000L)
                .card(card)
                .price(new BigDecimal(100))
                .transactionDate(LocalDateTime.of(2023, 5, 29, 14, 30))
                .build();
    }

    public static Transaction buildTransaction() {
        return Transaction.builder()
                .transactionId(10000L)
                .card(buildCard(true, false, new BigDecimal(200)))
                .price(new BigDecimal(100))
                .transactionDate(LocalDateTime.of(2023, 5, 29, 10, 10))
                .build();
    }
}
