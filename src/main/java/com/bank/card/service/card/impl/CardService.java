package com.bank.card.service.card.impl;

import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.entity.Card;
import com.bank.card.domain.error.model.NotFoundException;
import com.bank.card.repository.card.impl.ICardRepositoryFacade;
import com.bank.card.service.card.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bank.card.utils.Constants.CARD_NOT_FOUND;
import static com.bank.card.utils.Constants.CARD_VALIDITY_YEARS;
import static com.bank.card.utils.Constants.DATE_FORMAT;
import static com.bank.card.utils.Constants.RAND_NUM_LENGTH;

@Service
@RequiredArgsConstructor
public class CardService implements ICardService {

    private final ICardRepositoryFacade cardRepository;


    @Override
    public CardDataDto getCardBalance(String cardId) {
        var card = getIfExists(cardId);
        return CardDataDto.builder()
                .cardId(cardId)
                .balance(card.getBalance())
                .build();
    }


    @Override
    @Transactional
    public void activateCard(String cardId) {
        var card = getIfExists(cardId);
        card.setActive(true);

        cardRepository.update(card);
    }

    @Override
    public void deduct(String cardNumber, BigDecimal price) {
        var card = getIfExists(cardNumber);
        var balance = card.getBalance().subtract(price);
        card.setBalance(balance);

        cardRepository.update(card);
    }

    @Override
    @Transactional
    public void blockCard(String cardId) {
        var card = getIfExists(cardId);
        card.setBlocked(true);

        cardRepository.update(card);
    }

    @Override
    @Transactional
    public void rechargeBalance(CardDataDto request) {
        var cardId = request.getCardId();
        var card = getIfExists(cardId);
        var balance = card.getBalance().add(request.getBalance());
        card.setBalance(balance);

        cardRepository.update(card);
    }

    @Override
    public CardDataDto generateCardNumber(String productId) {
        var cardNumber = generateNumber(productId);
        var card = Card.builder()
                .productId(productId)
                .number(cardNumber)
                .expirationDate(generateExpirationDate())
                .balance(new BigDecimal(0))
                .build();
        cardRepository.save(card);

        return CardDataDto.builder()
                .cardNumber(cardNumber)
                .build();
    }


    private String generateNumber(String productId) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder number = new StringBuilder(RAND_NUM_LENGTH);

        for (int i = 0; i < RAND_NUM_LENGTH; i++) {
            int randomDigit = secureRandom.nextInt(RAND_NUM_LENGTH);
            number.append(randomDigit);
        }

        return productId.concat(number.toString());
    }

    private String generateExpirationDate() {
        LocalDate cardValidity = LocalDate.now().plusYears(CARD_VALIDITY_YEARS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return cardValidity.format(formatter);
    }

    private Card getIfExists(String cardId) {
        var card = cardRepository.findByNumber(cardId);
        if (card.isPresent()) {
            return card.get();
        }
        throw new NotFoundException(CARD_NOT_FOUND);
    }
}

