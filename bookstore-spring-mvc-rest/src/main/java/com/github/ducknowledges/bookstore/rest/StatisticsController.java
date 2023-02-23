package com.github.ducknowledges.bookstore.rest;

import com.github.ducknowledges.bookstore.dto.CounterStatisticsDto;
import com.github.ducknowledges.bookstore.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/api/statistics")
    public CounterStatisticsDto getStatistics() {
        return statisticsService.getCounterStatistics();
    }
}
