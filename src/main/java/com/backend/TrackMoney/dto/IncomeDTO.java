package com.backend.TrackMoney.dto;

import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeDTO {
    private Long id;
    private String title;
    private String category;
    private LocalDate date;
    private Integer amount;
    private Long userId;

    public Income toIncomeEntity(User user) {
        return new Income(id, title, category, date, amount, user);
    }

    public static IncomeDTO fromIncomeEntity(Income income) {
        IncomeDTO dto = new IncomeDTO();
        dto.setId(income.getId());
        dto.setTitle(income.getTitle());
        dto.setCategory(income.getCategory());
        dto.setDate(income.getDate());
        dto.setAmount(income.getAmount());
        dto.setUserId(income.getUser().getId());  // attach user id in response
        return dto;
    }
}
