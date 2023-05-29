package com.bank.card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseResponse {

    @Schema(example = "5200828282828210")
    private String transactionId;

    @NotBlank
    @Positive
    @Schema(example = "100")
    private LocalDateTime transactionDate;

}

