package com.api.service;

import com.api.Config;
import com.api.model.Statistics;
import com.api.model.Transaction;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    @Getter
    private Statistics statistics = new Statistics(); // TODO: synchronized if needed

    public void addTransaction(Transaction transaction) {
        if (transaction.getTimestamp() >= System.currentTimeMillis() - Config.TIME_SPAN) {
            statistics.updateValue(transaction.getAmount());
        }
    }

    @Scheduled(fixedRate = Config.TIME_SPAN)
    public void run() {
        statistics.reset();
    }
}
