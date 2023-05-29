package com.bank.card.controller.transaction;

import com.bank.card.controller.impl.TransactionController;
import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.dto.PurchaseResponse;
import com.bank.card.domain.dto.TransactionResponse;
import com.bank.card.service.transaction.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;
    @Mock
    private TransactionServiceImpl transactionService;

    @Test
    void doPurchase() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        var purchaseResponse = PurchaseResponse.builder()
                .transactionId("1000")
                .transactionDate(LocalDateTime.of(2023, 5, 28, 16, 20))
                .build();
        when(transactionService.purchase(any(CardDataDto.class))).thenReturn(purchaseResponse);
        var result = transactionController.purchase(purchaseRequest);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(purchaseResponse, result.getBody());
        verify(transactionService, atLeastOnce()).purchase(any(CardDataDto.class));
    }

    @Test
    void getTransaction() {

        var transactionResponse = TransactionResponse.builder()
                .transactionId(1L)
                .currency("USD")
                .isCancelled(false)
                .transactionDate(LocalDateTime.of(2023, 5, 28, 16, 20))
                .price(new BigDecimal(100))
                .cardNumber("5200828282828210")
                .build();
        when(transactionService.getTransaction(anyLong())).thenReturn(transactionResponse);
        var result = transactionController.getTransaction(1L);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(transactionResponse, result.getBody());
        verify(transactionService, atLeastOnce()).getTransaction(anyLong());
    }

    @Test
    void doAnnulation() {
        var annulationRequest = AnnulationRequest.builder()
                .cardId("5200828282828210")
                .transactionId("1000")
                .build();
        var result = transactionController.annul(annulationRequest);
        assertEquals(200, result.getStatusCode().value());
        verify(transactionService, atLeastOnce()).annul(any(AnnulationRequest.class));
    }
}
