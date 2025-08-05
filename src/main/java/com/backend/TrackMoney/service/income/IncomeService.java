package com.backend.TrackMoney.service.income;

import com.backend.TrackMoney.dto.IncomeDTO;
import com.backend.TrackMoney.entity.Income;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IncomeService {

    IncomeDTO postIncome(IncomeDTO incomeDTO);

    List<IncomeDTO> getAllIncomes();

    List<IncomeDTO> getMyIncomes();

    IncomeDTO getIncomeById(Long id);

    IncomeDTO updateIncome(IncomeDTO incomeDTO, Long id);

    void deleteIncomeById(Long id);
}
