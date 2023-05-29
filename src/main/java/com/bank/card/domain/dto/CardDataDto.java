package com.bank.card.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class CardDataDto {

    @NotBlank
    @Length(min = 16, max = 16)
    @Schema(example = "5200828282828210")
    private String cardId;

    @NotBlank
    @Positive
    @Schema(example = "100")
    private BigDecimal price;

    @Positive
    @Schema(example = "100")
    private BigDecimal balance;

    private String cardNumber;
}

