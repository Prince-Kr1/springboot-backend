package com.backend.TrackMoney.repository;

import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.amount) FROM Income i")
    Double totalIncome();

    Optional<Income> findFirstByOrderByDateDesc();

    List<Income> findByUser(User user);
}
