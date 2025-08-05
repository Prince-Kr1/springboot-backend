package com.backend.TrackMoney.dto;

import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseDTO {

        private Long id;
        private String title;
        private String category;
        private LocalDate date;
        private Integer amount;
        private Long userId;

//        // Converting DTO to Entity
//        public Expense toExpenseEntity(User user) {
//                return new Expense(id, title, category, date, amount, user);
//        }

        // Converting Entity to DTO
        public static ExpenseDTO fromExpenseEntity(Expense expense) {
                ExpenseDTO dto = new ExpenseDTO();
                dto.setId(expense.getId());
                dto.setTitle(expense.getTitle());
                dto.setCategory(expense.getCategory());
                dto.setDate(expense.getDate());
                dto.setAmount(expense.getAmount());
                dto.setUserId(expense.getUser().getId()); // Set user ID
                return dto;
        }
}
