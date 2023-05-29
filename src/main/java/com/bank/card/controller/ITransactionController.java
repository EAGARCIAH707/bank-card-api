package com.bank.card.controller;

import com.bank.card.domain.dto.AnnulationRequest;
import com.bank.card.domain.dto.CardDataDto;
import com.bank.card.domain.dto.PurchaseResponse;
import com.bank.card.domain.dto.TransactionResponse;
import com.bank.card.domain.error.dto.GeneralErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ITransactionController {
    @Tag(name = "Transactions", description = "service related to transactions")
    @Operation(summary = "Purchase", description = "Service used to purchase by card id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Successful query",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PurchaseResponse.class))),
                    @ApiResponse(
                            description = "Internal controlled error",
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class)))
            })
    ResponseEntity<PurchaseResponse> purchase(@RequestBody CardDataDto request);

    @Tag(name = "Transactions")
    @Operation(summary = "Get Transaction By Id", description = "Service used to find transaction by id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Successful query",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(
                            description = "Internal controlled error",
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class)))
            })
    ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long transactionId);


    @Tag(name = "Transactions")
    @Operation(summary = "Annul Transaction By Id", description = "Annul transaction by id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Successful query",
                            responseCode = "204",
                            content = @Content(schema = @Schema())),
                    @ApiResponse(
                            description = "Internal controlled error",
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class)))
            })
    ResponseEntity<Void> annul(@RequestBody AnnulationRequest request);

}
