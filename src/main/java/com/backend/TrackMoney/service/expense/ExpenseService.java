package com.backend.TrackMoney.service.expense;

import com.backend.TrackMoney.dto.ExpenseDTO;
import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {

    ExpenseDTO postExpense(ExpenseDTO expenseDTO);

    List<ExpenseDTO> getAllExpenses();

    ExpenseDTO getExpenseById(Long id);  // admin-level

    List<ExpenseDTO> getMyExpenses();  // current user's expenses

    ExpenseDTO updateExpense(ExpenseDTO expenseDTO, Long id);

    void deleteExpenseById(Long id);
}
