package com.backend.TrackMoney.controller;

import com.backend.TrackMoney.dto.ExpenseDTO;
import com.backend.TrackMoney.dto.IncomeDTO;
import com.backend.TrackMoney.entity.Expense;
import com.backend.TrackMoney.entity.Income;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.service.income.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;


    @PostMapping
    public ResponseEntity<?> postIncome(@RequestBody IncomeDTO incomeDTO) throws TrackMoneyException {
        try {
            IncomeDTO createdIncome = incomeService.postIncome(incomeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add income: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyIncomes() {
        return ResponseEntity.ok(incomeService.getMyIncomes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getIncomeById(@PathVariable Long id) {
        try {
            IncomeDTO income = incomeService.getIncomeById(id);
            return ResponseEntity.ok(income);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income not found: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@RequestBody IncomeDTO incomeDTO, @PathVariable Long id) {
        try{
            IncomeDTO updated = incomeService.updateIncome(incomeDTO, id);
            return ResponseEntity.ok(updated);
        }  catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income not found: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncomeById(@PathVariable Long id) {
        try{
            incomeService.deleteIncomeById(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
