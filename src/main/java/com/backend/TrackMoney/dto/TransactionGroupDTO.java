package com.backend.TrackMoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionGroupDTO {

    private Double total;
    private List<TransactionDTO> transactions;
}
