package com.bank.card.service.card.transaction;

import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.error.model.ConflictException;
import com.bank.card.domain.error.model.NotFoundException;
import com.bank.card.repository.card.ICardRepository;
import com.bank.card.repository.transaction.impl.ITransactionRepositoryFacade;
import com.bank.card.service.card.ICardService;
import com.bank.card.service.transaction.impl.TransactionServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.bank.card.util.Factories.buildCard;
import static com.bank.card.util.Factories.buildTransaction;
import static com.bank.card.utils.Constants.ANNULATION_TIME_EXCEEDED;
import static com.bank.card.utils.Constants.CARD_NOT_FOUND;
import static com.bank.card.utils.Constants.EXPIRED_CARD;
import static com.bank.card.utils.Constants.INACTIVE_CARD;
import static com.bank.card.utils.Constants.INSUFFICIENT_BALANCE;
import static com.bank.card.utils.Constants.LOCKED_CARD;
import static com.bank.card.utils.Constants.TRANSACTION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private ITransactionRepositoryFacade transactionRepository;
    @Mock
    private ICardRepository cardRepository;

    @Mock
    private ICardService cardService;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    private static MockedStatic<LocalDateTime> mockedStatic;

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }

    @Test
    void purchase() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        var card = buildCard(true, false, new BigDecimal(200));
        var transaction = buildTransaction(card);
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        when(transactionRepository.save(any())).thenReturn(transaction);
        doNothing().when(cardService).deduct(any(), any());


        var result = transactionService.purchase(purchaseRequest);
        assertEquals(transaction.getTransactionId().toString(), result.getTransactionId());
    }

    @Test
    void purchaseNotFound() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        Exception ex = assertThrows(
                NotFoundException.class,
                () -> transactionService.purchase(purchaseRequest)
        );
        assertEquals(CARD_NOT_FOUND, ex.getMessage());
    }

    @Test
    void purchaseInsufficientBalance() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(300))
                .build();
        var card = buildCard(true, false, new BigDecimal(200));
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        Exception ex = assertThrows(
                ConflictException.class,
                () -> transactionService.purchase(purchaseRequest)
        );
        assertEquals(INSUFFICIENT_BALANCE, ex.getMessage());
    }

    @Test
    void purchaseExpired() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        var card = buildCard(true, false, new BigDecimal(200));
        card.setExpirationDate("04/2023");
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        Exception ex = assertThrows(
                ConflictException.class,
                () -> transactionService.purchase(purchaseRequest)
        );
        assertEquals(EXPIRED_CARD, ex.getMessage());
    }

    @Test
    void purchaseInactive() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        var card = buildCard(false, false, new BigDecimal(200));
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        Exception ex = assertThrows(
                ConflictException.class,
                () -> transactionService.purchase(purchaseRequest)
        );
        assertEquals(INACTIVE_CARD, ex.getMessage());
    }

    @Test
    void purchaseLocked() {
        var purchaseRequest = CardDataDto.builder()
                .cardId("5200828282828210")
                .price(new BigDecimal(100))
                .build();
        var card = buildCard(true, true, new BigDecimal(200));
        when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
        Exception ex = assertThrows(
                ConflictException.class,
                () -> transactionService.purchase(purchaseRequest)
        );
        assertEquals(LOCKED_CARD, ex.getMessage());
    }

    @Test
    void annulation() {
        var request = AnnulationRequest.builder()
                .transactionId("10000")
                .cardId("5200828282828210")
                .build();
        var transaction = Optional.of(buildTransaction());
        when(transactionRepository.findById(anyLong())).thenReturn(transaction);
        LocalDateTime date = LocalDateTime.of(2023, 5, 28, 16, 20);
        mockedStatic.when(LocalDateTime::now).thenReturn(date);

        assertDoesNotThrow(() -> {
            transactionService.annul(request);
        });

    }

    @Test
    void annulationNotFound() {
        var request = AnnulationRequest.builder()
                .transactionId("10000")
                .cardId("5200828282828210")
                .build();
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundException.class, () -> transactionService.annul(request));

        assertEquals(TRANSACTION_NOT_FOUND, ex.getMessage());
    }

    @Test
    void getTransaction() {
        var transaction = buildTransaction();
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        var result = transactionService.getTransaction(1L);
        assertNotNull(result);
    }

    @Test
    void getTransactionNotFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundException.class, () -> transactionService.getTransaction(1L));

        assertEquals(TRANSACTION_NOT_FOUND, ex.getMessage());
    }


    @Test
    void annulationExceeded() {
        var request = AnnulationRequest.builder()
                .transactionId("10000")
                .cardId("5200828282828210")
                .build();
        var transaction = Optional.of(buildTransaction());
        when(transactionRepository.findById(anyLong())).thenReturn(transaction);
        LocalDateTime date = LocalDateTime.of(2023, 5, 30, 15, 40);
        mockedStatic.when(LocalDateTime::now).thenReturn(date);
        Exception ex = assertThrows(ConflictException.class, () -> transactionService.annul(request));

        assertEquals(ANNULATION_TIME_EXCEEDED, ex.getMessage());
    }


}
