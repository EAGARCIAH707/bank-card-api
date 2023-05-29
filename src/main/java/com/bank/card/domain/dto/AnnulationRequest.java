package com.bank.card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnulationRequest {

    @NotBlank
    @Length(min = 16, max = 16)
    @Schema(example = "5200828282828210")
    private String cardId;

    @NotBlank
    @Schema(example = "1")
    private String transactionId;
}

