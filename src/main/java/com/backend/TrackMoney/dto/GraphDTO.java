package com.backend.TrackMoney.dto;

import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.Income;
import lombok.Data;

import java.util.List;

@Data
public class GraphDTO {

    private List<Expense> expenseList;

    private List<Income> incomeList;
}
