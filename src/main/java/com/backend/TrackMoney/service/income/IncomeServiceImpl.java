package com.backend.TrackMoney.service.income;

import com.backend.TrackMoney.dto.IncomeDTO;
import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.repository.IncomeRepository;
import com.backend.TrackMoney.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public IncomeDTO postIncome(IncomeDTO incomeDTO) {
        User user = getCurrentUser();
        Income income = incomeDTO.toIncomeEntity(user);
        Income saved = saveOrUpdateIncome(new Income(), incomeDTO, user);
        return IncomeDTO.fromIncomeEntity(saved);
    }

    @Override
    public List<IncomeDTO> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .sorted(Comparator.comparing(Income::getDate).reversed())
                .map(IncomeDTO::fromIncomeEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomeDTO> getMyIncomes() {
        User user = getCurrentUser();
        return incomeRepository.findByUser(user).stream()
                .sorted(Comparator.comparing(Income::getDate).reversed())
                .map(IncomeDTO::fromIncomeEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeDTO getIncomeById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found"));
        return IncomeDTO.fromIncomeEntity(income);
    }

    @Override
    public IncomeDTO updateIncome(IncomeDTO incomeDTO, Long id) {
        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id " + id));
        User user = getCurrentUser();
        Income updated = saveOrUpdateIncome(existing, incomeDTO, user);
        return IncomeDTO.fromIncomeEntity(updated);
    }

    @Override
    public void deleteIncomeById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found"));
        incomeRepository.delete(income);
    }

    private Income saveOrUpdateIncome(Income income, IncomeDTO dto, User user) {
        income.setTitle(dto.getTitle());
        income.setDate(dto.getDate());
        income.setAmount(dto.getAmount());
        income.setCategory(dto.getCategory());
        income.setUser(user);
        return incomeRepository.save(income);
    }
}
