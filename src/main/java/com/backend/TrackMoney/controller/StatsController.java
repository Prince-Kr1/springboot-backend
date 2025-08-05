package com.backend.TrackMoney.controller;

import com.backend.TrackMoney.dto.GraphDTO;
import com.backend.TrackMoney.dto.StatsDTO;
import com.backend.TrackMoney.service.stats.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stats")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/chart")
    public ResponseEntity<GraphDTO> getChartDetails() {
        return ResponseEntity.ok(statsService.getChartData());
    }

    @GetMapping
    public ResponseEntity<StatsDTO> getStatsData() {
        return ResponseEntity.ok(statsService.getStatsDataForCurrentUser());
    }
}
