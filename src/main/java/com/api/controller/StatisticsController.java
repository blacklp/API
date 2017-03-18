package com.api.controller;

import com.api.model.Statistics;
import com.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     *
     * @return statistics for the last 60 seconds
     */
    @RequestMapping(method = RequestMethod.GET)
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}
