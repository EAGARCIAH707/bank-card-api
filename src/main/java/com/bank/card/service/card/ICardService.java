package com.bank.card.service.card;

import com.bank.card.domain.dto.CardDataDto;

import java.math.BigDecimal;

public interface ICardService {

    CardDataDto generateCardNumber(String productId);

    void activateCard(String cardId);

    void deduct(String cardNumber, BigDecimal price);

    void blockCard(String cardId);

    void rechargeBalance(CardDataDto request);

    CardDataDto getCardBalance(String cardId);
}

