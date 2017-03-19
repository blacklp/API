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
    private Statistics statistics = new Statistics();

    /**
     *
     * @param transaction a Transaction object to be added to the system
     * Adds the transaction into the system and only considers it if it has occurred during the last Config.TIME_SPAN
     * milliseconds. This is necessary in order to be able to deal with transactions received out of order.
     */
    public synchronized void addTransaction(Transaction transaction) {
        if (transaction.getTimestamp() >= System.currentTimeMillis() - Config.TIME_SPAN) {
            statistics.updateValue(transaction.getAmount());
        }
    }

    /**
     * Runs asynchronously every Config.TIME_SPAN milliseconds and resets the statistics object.
     * This behavior is needed since we are only interested in the statistics from the last Config.TIME_SPAN
     * milliseconds.
     */
    @Scheduled(fixedRate = Config.TIME_SPAN)
    public synchronized void run() {
        statistics.reset();
    }
}
