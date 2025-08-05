package com.backend.TrackMoney.service.stats;


import com.backend.TrackMoney.dto.GraphDTO;
import com.backend.TrackMoney.dto.StatsDTO;
import com.backend.TrackMoney.dto.TransactionDTO;
import com.backend.TrackMoney.dto.TransactionGroupDTO;
import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.repository.ExpenseRepository;
import com.backend.TrackMoney.repository.IncomeRepository;
import com.backend.TrackMoney.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    @Override
    public GraphDTO getChartData() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(100);

        GraphDTO graphDTO = new GraphDTO();
        graphDTO.setExpenseList(expenseRepository.findByDateBetween(startDate, endDate));
        graphDTO.setIncomeList(incomeRepository.findByDateBetween(startDate, endDate));

        return graphDTO;
    }

    @Override
    public StatsDTO getStatsDataForCurrentUser() {
        User user = userService.getCurrentUser();

        List<Income> incomeList = incomeRepository.findByUser(user);
        List<Expense> expenseList = expenseRepository.findByUser(user);

        // Total income, expense, balance
        Double totalIncome = incomeList.stream().mapToDouble(Income::getAmount).sum();
        Double totalExpense = expenseList.stream().mapToDouble(Expense::getAmount).sum();
        Double totalBalance = totalIncome - totalExpense;

        // Recent Transactions (5 latest from both income & expense)
        List<TransactionDTO> recentTransactions = Stream.concat(
                incomeList.stream().map(i -> new TransactionDTO(
                        i.getTitle(),
                        i.getCategory(),
                        i.getDate(),
                        i.getAmount(),
                        "income",
                        i.getUser().getId()
                )),
                expenseList.stream().map(e -> new TransactionDTO(
                        e.getTitle(),
                        e.getCategory(),
                        e.getDate(),
                        e.getAmount(),
                        "expense",
                        e.getUser().getId()
                ))
        )
        .sorted(Comparator.comparing(TransactionDTO::getDate).reversed())
        .limit(5)
        .collect(Collectors.toList());

        // Last 30 days Income and Expenses
        LocalDate fromDate = LocalDate.now().minusDays(300);

        List<TransactionDTO> last30DaysIncomes = incomeList.stream()
                .filter(i -> !i.getDate().isBefore(fromDate))
                .map(i -> new TransactionDTO(
                        i.getTitle(),
                        i.getCategory(),
                        i.getDate(),
                        i.getAmount(),
                        "income",
                        i.getUser().getId()
                ))
                .collect(Collectors.toList());

        List<TransactionDTO> last30DaysExpenses = expenseList.stream()
                .filter(e -> !e.getDate().isBefore(fromDate))
                .map(e -> new TransactionDTO(
                        e.getTitle(),
                        e.getCategory(),
                        e.getDate(),
                        e.getAmount(),
                        "expense",
                        e.getUser().getId()
                ))
                .collect(Collectors.toList());

        double incomeTotal30 = last30DaysIncomes.stream().mapToDouble(TransactionDTO::getAmount).sum();
        double expenseTotal30 = last30DaysExpenses.stream().mapToDouble(TransactionDTO::getAmount).sum();

        TransactionGroupDTO incomeGroup = new TransactionGroupDTO(incomeTotal30, last30DaysIncomes);
        TransactionGroupDTO expenseGroup = new TransactionGroupDTO(expenseTotal30, last30DaysExpenses);

        // Then return it in the new StatsDTO
        return new StatsDTO(
                totalIncome,
                totalExpense,
                totalBalance,
                recentTransactions,
                incomeGroup,
                expenseGroup
        );

    }
}
