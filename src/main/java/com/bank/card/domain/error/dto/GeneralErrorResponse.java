package com.bank.card.domain.error.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralErrorResponse {
    private String message;
    private Object error;
    private String path;
    private Integer status;
}
