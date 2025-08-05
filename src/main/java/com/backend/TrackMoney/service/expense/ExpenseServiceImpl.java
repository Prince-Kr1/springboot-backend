package com.backend.TrackMoney.service.expense;

import com.backend.TrackMoney.dto.ExpenseDTO;
import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.repository.ExpenseRepository;
import com.backend.TrackMoney.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    // Helper method to get the current user from JWT
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Create new Expense for logged-in user
    public ExpenseDTO postExpense(ExpenseDTO expenseDTO) {
        User user = getCurrentUser();
        Expense savedExpense = saveOrUpdateExpense(new Expense(), expenseDTO, user);
        return ExpenseDTO.fromExpenseEntity(savedExpense);
    }

    //  Fetch all expenses (all users — use only if admin)
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .map(ExpenseDTO::fromExpenseEntity)
                .collect(Collectors.toList());
    }

    //  Fetch only expenses of the current logged-in user
    public List<ExpenseDTO> getMyExpenses() {
        User user = getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUser(user); // <- correct type

        return expenses.stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .map(ExpenseDTO::fromExpenseEntity)
                .collect(Collectors.toList());
    }


    // Fetch expense by ID
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id " + id));
        return ExpenseDTO.fromExpenseEntity(expense);
    }

    // Update existing Expense for logged-in user
    public ExpenseDTO updateExpense(ExpenseDTO expenseDTO, Long id) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id " + id));
        User user = getCurrentUser();

        Expense updated = saveOrUpdateExpense(existing, expenseDTO, user);
        return ExpenseDTO.fromExpenseEntity(updated);
    }

    // Delete expense
    public void deleteExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id " + id));
        expenseRepository.delete(expense);
    }

    // Internal reusable method to map DTO to entity
    private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO dto, User user) {
        expense.setTitle(dto.getTitle());
        expense.setDate(dto.getDate());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

}
