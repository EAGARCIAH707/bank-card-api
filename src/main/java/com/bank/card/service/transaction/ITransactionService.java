package com.bank.card.service.transaction;

import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.dto.PurchaseResponse;
import com.bank.card.domain.dto.TransactionResponse;


public interface ITransactionService {
    PurchaseResponse purchase(CardDataDto request);

    TransactionResponse getTransaction(Long transactionId);

    void annul(AnnulationRequest request);
}
