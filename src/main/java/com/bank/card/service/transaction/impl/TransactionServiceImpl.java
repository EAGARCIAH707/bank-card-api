package com.bank.card.service.transaction.impl;

import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.dto.PurchaseResponse;
import com.bank.card.domain.dto.TransactionResponse;
import com.bank.card.domain.entity.Card;
import com.bank.card.domain.entity.Transaction;
import com.bank.card.domain.error.model.ConflictException;
import com.bank.card.domain.error.model.NotFoundException;
import com.bank.card.repository.card.ICardRepository;
import com.bank.card.repository.transaction.impl.ITransactionRepositoryFacade;
import com.bank.card.service.card.ICardService;
import com.bank.card.service.transaction.ITransactionService;
import com.bank.card.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.bank.card.utils.Constants.ANNULATION_TIME_EXCEEDED;
import static com.bank.card.utils.Constants.CARD_NOT_FOUND;
import static com.bank.card.utils.Constants.CURRENCY;
import static com.bank.card.utils.Constants.DATE_FORMAT;
import static com.bank.card.utils.Constants.EXPIRED_CARD;
import static com.bank.card.utils.Constants.INACTIVE_CARD;
import static com.bank.card.utils.Constants.INSUFFICIENT_BALANCE;
import static com.bank.card.utils.Constants.LOCKED_CARD;
import static com.bank.card.utils.Constants.TRANSACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ICardRepository cardRepository;
    private final ITransactionRepositoryFacade transactionRepository;
    private final ICardService cardService;

    @Override
    @Transactional
    public PurchaseResponse purchase(CardDataDto request) {
        var cardId = request.getCardId();
        var price = request.getPrice();
        var card = cardRepository.findByNumber(cardId)
                .orElseThrow(() -> new NotFoundException(CARD_NOT_FOUND));

        validateBalanceAvailable(card.getBalance(), price);
        validateValidity(card.getExpirationDate());
        validateCardStatus(card);

        cardService.deduct(card.getNumber(), price);

        var transaction = Transaction.builder()
                .card(card)
                .price(price)
                .transactionDate(LocalDateTime.now())
                .currency(CURRENCY)
                .build();

        var transactionSaved = transactionRepository.save(transaction);

        return PurchaseResponse.builder()
                .transactionId(transactionSaved.getTransactionId().toString())
                .transactionDate(transactionSaved.getTransactionDate())
                .build();
    }

    @Override
    public TransactionResponse getTransaction(Long transactionId) {
        var transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new NotFoundException(TRANSACTION_NOT_FOUND));

        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .cardNumber(transaction.getCard().getNumber())
                .price(transaction.getPrice())
                .transactionDate(transaction.getTransactionDate())
                .isCancelled(transaction.isCancelled())
                .currency(transaction.getCurrency())
                .build();
    }

    @Override
    @Transactional
    public void annul(AnnulationRequest request) {
        var transactionId = Long.valueOf(request.getTransactionId());
        var transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new NotFoundException(TRANSACTION_NOT_FOUND));

        validateAnnulationTime(transaction.getTransactionDate());
        transaction.setCancelled(true);
        transactionRepository.update(transaction);
        cardService.rechargeBalance(CardDataDto.builder()
                .cardId(transaction.getCard().getNumber())
                .balance(transaction.getPrice())
                .build());
    }

    private void validateBalanceAvailable(BigDecimal balance, BigDecimal price) {
        if (balance.doubleValue() < price.doubleValue()) {
            throw new ConflictException(INSUFFICIENT_BALANCE);
        }
    }

    private void validateValidity(String expirationDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate expirationDate = YearMonth.parse(expirationDateStr, formatter).atEndOfMonth();
        if (LocalDate.now().isAfter(expirationDate)) throw new ConflictException(EXPIRED_CARD);
    }

    private void validateAnnulationTime(LocalDateTime transactionDate) {
        long diffHours = ChronoUnit.HOURS.between(transactionDate, LocalDateTime.now());
        if (diffHours > Constants.ANN_HOUR_LIMIT)
            throw new ConflictException(ANNULATION_TIME_EXCEEDED);
    }

    private void validateCardStatus(Card card) {
        if (!card.isActive()) throw new ConflictException(INACTIVE_CARD);
        if (card.isBlocked()) throw new ConflictException(LOCKED_CARD);
    }
}
