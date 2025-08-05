package com.backend.TrackMoney.dto;

import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double totalBalance;
    private List<TransactionDTO> recentTransactions;

    private TransactionGroupDTO last30DaysIncomes;
    private TransactionGroupDTO last30DaysExpenses;



}
