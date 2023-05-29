package com.bank.card.controller.impl;

import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.service.card.ICardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class CardController implements com.bank.card.controller.ICardController {

    private final ICardService cardService;

    @Override
    @GetMapping("/card/{productId}/number")
    public ResponseEntity<CardDataDto> generateCardNumber(@PathVariable String productId) {
        var response = cardService.generateCardNumber(productId);
        log.info("Response of generate card number: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/card/enroll")
    public ResponseEntity<Void> activateCard(@RequestBody CardDataDto request) {
        cardService.activateCard(request.getCardId());

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<Void> blockCard(@PathVariable String cardId) {
        cardService.blockCard(cardId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/card/balance")
    public ResponseEntity<Void> rechargeBalance(@RequestBody CardDataDto request) {
        cardService.rechargeBalance(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/card/balance/{cardId}")
    public ResponseEntity<CardDataDto> getCardBalance(@PathVariable String cardId) {
        var response = cardService.getCardBalance(cardId);
        log.info("Response of get card balance: {}", response);

        return ResponseEntity.ok(response);
    }
}
