package com.backend.TrackMoney.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private LocalDate date;
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

//    public Expense(Long id, String title, String description, String category, LocalDate date, Integer amount, User user) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.category = category;
//        this.date = date;
//        this.amount = amount;
//        this.user = user;
    }


