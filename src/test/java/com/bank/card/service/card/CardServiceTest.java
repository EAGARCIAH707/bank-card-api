package com.bank.card.service.card;

import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.entity.Card;
import com.bank.card.domain.error.model.NotFoundException;
import com.bank.card.repository.card.impl.ICardRepositoryFacade;
import com.bank.card.service.card.impl.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.bank.card.utils.Constants.CARD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private ICardRepositoryFacade cardRepository;
    @InjectMocks
    private CardService cardService;

    @Test
    void generateCardNumber() {
        var productId = "102030";
        var result = cardService.generateCardNumber(productId);
        assertEquals(16, result.getCardNumber().length());
        verify(cardRepository, atLeastOnce()).save(any(Card.class));
    }

    @Test
    void activateCard() {
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(new Card()));
        cardService.activateCard("5200828282828210");
    }

    @Test
    void activateCardThrowErrorWhenCardNotExist() {
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        Exception ex = assertThrows(
                NotFoundException.class,
                () -> cardService.activateCard("5200828282828210")
        );
        assertEquals(CARD_NOT_FOUND, ex.getMessage());
    }

    @Test
    void blockCard() {
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(new Card()));
        cardService.blockCard("5200828282828210");
        verify(cardRepository, atLeastOnce()).update(any());
    }

    @Test
    void blockCardThrowErrorWhenCardNotExist() {
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        Exception ex = assertThrows(
                NotFoundException.class,
                () -> cardService.blockCard("5200828282828210")
        );
        assertEquals(CARD_NOT_FOUND, ex.getMessage());
    }

    @Test
    void rechargeBalance() {
        var card = Card.builder().balance(BigDecimal.ONE).build();
        var request = CardDataDto.builder()
                .cardId("5200828282828210")
                .balance(new BigDecimal(100))
                .build();
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        assertDoesNotThrow(() -> cardService.rechargeBalance(request));
    }

    @Test
    void rechargeBalanceThrowErrorWhenCardNotExist() {
        var request = CardDataDto.builder()
                .cardId("5200828282828210")
                .balance(new BigDecimal(100))
                .build();
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        Exception ex = assertThrows(
                NotFoundException.class,
                () -> cardService.rechargeBalance(request)
        );
        assertEquals(CARD_NOT_FOUND, ex.getMessage());
    }

    @Test
    void getCardBalance() {
        var card = Card.builder().balance(BigDecimal.ONE).build();
        var cardId = "5200828282828210";
        var expectValue = CardDataDto.builder()
                .cardId(cardId)
                .balance(BigDecimal.ONE)
                .build();
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        var balance = cardService.getCardBalance(cardId);
        assertEquals(expectValue, balance);
    }

    @Test
    void getCardBalanceThrowErrorWhenCardNotExist() {
        var cardId = "5200828282828210";
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        Exception ex = assertThrows(
                NotFoundException.class,
                () -> cardService.getCardBalance(cardId)
        );
        assertEquals(CARD_NOT_FOUND, ex.getMessage());
    }
}
