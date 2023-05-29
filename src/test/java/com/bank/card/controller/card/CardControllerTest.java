package com.bank.card.controller.card;

import com.bank.card.controller.impl.CardController;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.service.card.impl.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @InjectMocks
    private CardController cardController;
    @Mock
    private CardService cardService;

    @Test
    void generateCardNumber() {
        var cardNumber = "102030123456789";
        when(cardService.generateCardNumber(anyString()))
                .thenReturn(CardDataDto.builder()
                        .cardNumber(cardNumber)
                        .build()
                );
        var result = cardController.generateCardNumber("102030");
        assertEquals("102030123456789", Objects.requireNonNull(result.getBody()).getCardNumber());
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void activateCard() {
        var result = cardController.activateCard(CardDataDto.builder().cardId("102030123456789").build());
        assertEquals(204, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).activateCard(anyString());
    }

    @Test
    void blockCard() {
        var result = cardController.blockCard("102030123456789");
        assertEquals(204, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).blockCard(anyString());
    }

    @Test
    void rechargeBalance() {
        var result = cardController.rechargeBalance(CardDataDto.builder()
                .cardId("102030123456789")
                .balance(new BigDecimal(100))
                .build()
        );
        assertEquals(204, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).rechargeBalance(any(CardDataDto.class));
    }

    @Test
    void getCardBalance() {
        var cardDataDto = CardDataDto.builder()
                .cardId("102030123456789")
                .balance(BigDecimal.valueOf(100))
                .build();
        when(cardService.getCardBalance(anyString())).thenReturn(cardDataDto);
        var result = cardController.getCardBalance("102030123456789");
        assertEquals(200, result.getStatusCode().value());
        assertEquals(cardDataDto, result.getBody());
    }
}
