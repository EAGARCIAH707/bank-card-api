package com.bank.card.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private Long transactionId;
    private String cardNumber;
    private BigDecimal price;
    private LocalDateTime transactionDate;
    private boolean isCancelled;
    private String currency;
}
