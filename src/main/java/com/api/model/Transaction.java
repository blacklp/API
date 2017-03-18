package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Transaction {

    @NotNull(message = "Error: No Amount provided")
    private Double amount;

    @NotNull(message = "Error: No Timestamp provided")
    private Long timestamp;
}
