package com.api.service;

import com.api.model.Statistics;
import com.api.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StatisticsServiceTest {
    @Test
    public void addTransactionNow() {
        StatisticsService service = new StatisticsService();
        double amount = 123.23;

        Transaction transaction = new Transaction(amount, System.currentTimeMillis());
        // Method call
        service.addTransaction(transaction);

        // Checks
        Statistics statistics = service.getStatistics();
        Assertions.assertThat(statistics.getCount()).isEqualTo(1);
        Assertions.assertThat(statistics.getMax()).isEqualTo(amount);
        Assertions.assertThat(statistics.getMin()).isEqualTo(amount);
        Assertions.assertThat(statistics.getSum()).isEqualTo(amount);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(amount);
    }

    @Test
    public void addTwoTransactionsNow() {
        StatisticsService service = new StatisticsService();
        double amount1 = 100.50, amount2 = 200.50;

        Transaction transaction1 = new Transaction(amount1, System.currentTimeMillis());
        Transaction transaction2 = new Transaction(amount2, System.currentTimeMillis());
        // Method calls
        service.addTransaction(transaction1);
        service.addTransaction(transaction2);

        // Checks
        Statistics statistics = service.getStatistics();
        Assertions.assertThat(statistics.getCount()).isEqualTo(2);
        Assertions.assertThat(statistics.getMax()).isEqualTo(amount2);
        Assertions.assertThat(statistics.getMin()).isEqualTo(amount1);
        Assertions.assertThat(statistics.getSum()).isEqualTo(301.00);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(150.50);
    }

    @Test
    public void addTransactionBeforeTime() {
        StatisticsService service = new StatisticsService();
        double amount = 123.23;

        Transaction transaction = new Transaction(amount, System.currentTimeMillis() - 70000);
        // Method call
        service.addTransaction(transaction);

        // Checks
        Statistics statistics = service.getStatistics();
        // The transaction was not stored because it corresponds to a too early point in time
        Assertions.assertThat(statistics.getCount()).isEqualTo(0);
        Assertions.assertThat(statistics.getMax()).isEqualTo(Double.MIN_VALUE);
        Assertions.assertThat(statistics.getMin()).isEqualTo(Double.MAX_VALUE);
        Assertions.assertThat(statistics.getSum()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(0.0);
    }
}