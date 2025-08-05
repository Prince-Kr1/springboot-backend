package com.backend.TrackMoney.dto;

import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "{user.name.absent}")
    private String username;

    @NotBlank(message = "{user.email.absent}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.absent}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,15}$", message = "{user.password.invalid}")
    private String password;

//    private List<Income> incomes;
//    private List<Expense> expenses;

//    public UserDTO(Long id, String username, String email, String password) {
//    }

    public User toEntity() {
        return new User(this.id, this.username, this.email, this.password);
    }
}
