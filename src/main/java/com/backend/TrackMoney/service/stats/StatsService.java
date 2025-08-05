package com.backend.TrackMoney.service.stats;

import com.backend.TrackMoney.dto.GraphDTO;
import com.backend.TrackMoney.dto.StatsDTO;
import org.springframework.stereotype.Service;

@Service
public interface StatsService {

    GraphDTO getChartData();

    StatsDTO getStatsDataForCurrentUser();
}
