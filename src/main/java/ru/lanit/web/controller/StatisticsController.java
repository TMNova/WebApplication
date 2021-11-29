package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.web.dto.StatisticsDTO;
import ru.lanit.web.services.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController { ;

    @Autowired
    StatisticsService statisticsService;

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }
}
