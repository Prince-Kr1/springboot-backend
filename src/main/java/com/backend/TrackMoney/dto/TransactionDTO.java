package com.backend.TrackMoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private String title;
    private String category;
    private LocalDate date;
    private double amount;
    private String type;
    private Long userId;// "income" or "expense"
}
