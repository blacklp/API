package com.api.service;

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
        if (transaction.getTimestamp() >= System.currentTimeMillis() - 60000) {
            // TODO: Make 60000 a constant in Config class or so
            statistics.updateValue(transaction.getAmount());
        }
    }

    @Scheduled(fixedRate = 60000) // TODO: Make this every 60 sec a constant in a Config class or so
    public void run() {
        statistics.reset();
    }
}
