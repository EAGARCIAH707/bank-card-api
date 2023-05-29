package com.bank.card.controller;

import com.bank.card.domain.dto.CardDataDto;
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


public interface ICardController {

    @Tag(name = "Card", description = "service related to cards of bank")
    @Operation(summary = "Generate Card Number", description = "Service used to generate a card number")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Successful query",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CardDataDto.class))),
                    @ApiResponse(
                            description = "Internal controlled error",
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class)))
            })
    ResponseEntity<CardDataDto> generateCardNumber(@PathVariable String productId);

    @Tag(name = "Card")
    @Operation(summary = "Activate Card", description = "Service used to activate card")
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
    ResponseEntity<Void> activateCard(@RequestBody CardDataDto request);

    @Tag(name = "Card")
    @Operation(summary = "Block Card", description = "Service used to block card by card id")
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
    ResponseEntity<Void> blockCard(@PathVariable String cardId);

    @Tag(name = "Card")
    @Operation(summary = "Cash In", description = "Service used to set balance")
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
    ResponseEntity<Void> rechargeBalance(@RequestBody CardDataDto request);

    @Tag(name = "Card")
    @Operation(summary = "Find Card Balance", description = "Service used to get card balance")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Successful query",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CardDataDto.class))),
                    @ApiResponse(
                            description = "Internal controlled error",
                            responseCode = "409",
                            content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class)))
            })
    ResponseEntity<CardDataDto> getCardBalance(@PathVariable String cardId);
}
