package com.api.controller;

import com.api.Config;
import com.api.model.Statistics;
import com.api.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTest {
    @Autowired
    private TransactionsController transactionsController;

    @Autowired
    private StatisticsController statisticsController;

    @Before
    public void before() {
        statisticsController.getStatistics().reset();
    }

    @Test
    public void getStatisticsAfterOneTransaction() throws Exception {
        transactionsController.postTransaction(new Transaction(123.23, System.currentTimeMillis()));

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(123.23);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(123.23);
        Assertions.assertThat(statistics.getMax()).isEqualTo(123.23);
        Assertions.assertThat(statistics.getMin()).isEqualTo(123.23);
        Assertions.assertThat(statistics.getCount()).isEqualTo(1);
    }

    @Test
    public void getStatisticsAfterSeveralTransactions() throws Exception {
        transactionsController.postTransaction(new Transaction(123.23, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(34.89, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(457.09, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(876.91, System.currentTimeMillis()));

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(1492.12);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(373.03);
        Assertions.assertThat(statistics.getMax()).isEqualTo(876.91);
        Assertions.assertThat(statistics.getMin()).isEqualTo(34.89);
        Assertions.assertThat(statistics.getCount()).isEqualTo(4);
    }

    @Test
    public void getStatisticsAfterTimeSpan() throws Exception {
        transactionsController.postTransaction(new Transaction(123.23, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(34.89, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(457.09, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(876.91, System.currentTimeMillis()));

        Thread.sleep(Config.TIME_SPAN);
        // After this time 'statistics' should have been reset

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getMax()).isEqualTo(Double.MIN_VALUE);
        Assertions.assertThat(statistics.getMin()).isEqualTo(Double.MAX_VALUE);
        Assertions.assertThat(statistics.getCount()).isEqualTo(0);
    }

    @Test
    public void getStatisticsAfterTimeSpanAndOneTransaction() throws Exception {
        transactionsController.postTransaction(new Transaction(123.23, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(34.89, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(457.09, System.currentTimeMillis()));
        transactionsController.postTransaction(new Transaction(876.91, System.currentTimeMillis()));

        Thread.sleep(Config.TIME_SPAN);
        // After this time 'statistics' should have been reset

        Statistics statistics = statisticsController.getStatistics();

        transactionsController.postTransaction(new Transaction(45.78, System.currentTimeMillis()));
        Assertions.assertThat(statistics.getSum()).isEqualTo(45.78);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(45.78);
        Assertions.assertThat(statistics.getMax()).isEqualTo(45.78);
        Assertions.assertThat(statistics.getMin()).isEqualTo(45.78);
        Assertions.assertThat(statistics.getCount()).isEqualTo(1);
    }

    @Test
    public void getStatisticsWithUnorderedTransactions() throws Exception {
        transactionsController.postTransaction(new Transaction(123.23, System.currentTimeMillis() - 20000)); // included
        transactionsController.postTransaction(new Transaction(34.89, System.currentTimeMillis() - 80000)); // not inc.
        transactionsController.postTransaction(new Transaction(457.09, System.currentTimeMillis() - 60001)); // not inc.
        transactionsController.postTransaction(new Transaction(876.91, System.currentTimeMillis() - 59000)); // included

        Statistics statistics = statisticsController.getStatistics();

        Assertions.assertThat(statistics.getSum()).isEqualTo(1000.14);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(500.07);
        Assertions.assertThat(statistics.getMax()).isEqualTo(876.91);
        Assertions.assertThat(statistics.getMin()).isEqualTo(123.23);
        Assertions.assertThat(statistics.getCount()).isEqualTo(2);
    }
}