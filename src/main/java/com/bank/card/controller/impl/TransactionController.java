package com.bank.card.controller.impl;

import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.dto.PurchaseResponse;
import com.bank.card.domain.dto.TransactionResponse;
import com.bank.card.service.transaction.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/v0")
@RequiredArgsConstructor
public class TransactionController implements com.bank.card.controller.ITransactionController {

    private final ITransactionService transactionService;

    @Override
    @PostMapping("/transaction/purchase")
    public ResponseEntity<PurchaseResponse> purchase(@RequestBody CardDataDto request) {
        var response = transactionService.purchase(request);
        log.info("Response of purchase: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long transactionId) {
        var response = transactionService.getTransaction(transactionId);
        log.info("Response of get transaction {}", response);

        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @Override
    @PostMapping("/transaction/annulation")
    public ResponseEntity<Void> annul(AnnulationRequest request) {
        transactionService.annul(request);

        return ResponseEntity.ok().build();
    }
}
