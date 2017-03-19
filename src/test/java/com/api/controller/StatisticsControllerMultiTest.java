package com.api.controller;

import com.api.Config;
import com.api.model.Statistics;
import com.api.model.Transaction;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Multi-threaded test for StatisticsController class. In this tests concurrent transactions are sent and getting
 * statistics should return the correct result at each point in time.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerMultiTest {
    @Autowired
    private TransactionsController transactionsController;

    @Autowired
    private StatisticsController statisticsController;

    @Before
    public void before() {
        statisticsController.getStatistics().reset();
    }

    @Test
    public void getStatistics() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> future1 = executorService.submit(() -> postTransaction(123.24));
        Future<?> future2 = executorService.submit(() -> postTransaction(38.35));
        Future<?> future3 = executorService.submit(() -> postTransaction(90.567));
        Future<?> future4 = executorService.submit(() -> postTransaction(140.89));
        Future<?> future5 = executorService.submit(() -> postTransaction(998.01));

        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(1391.057, Offset.offset(0.001));
        Assertions.assertThat(statistics.getAvg()).isEqualTo(278.2114, Offset.offset(0.001));
        Assertions.assertThat(statistics.getMax()).isEqualTo(998.01);
        Assertions.assertThat(statistics.getMin()).isEqualTo(38.35);
        Assertions.assertThat(statistics.getCount()).isEqualTo(5);
    }

    @Test
    public void getStatisticsAfterTimeSpan() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> future1 = executorService.submit(() -> postTransaction(123.24));
        Future<?> future2 = executorService.submit(() -> postTransaction(38.35));
        Future<?> future3 = executorService.submit(() -> postTransaction(90.567));
        Future<?> future4 = executorService.submit(() -> postTransaction(140.89));
        Future<?> future5 = executorService.submit(() -> postTransaction(998.01));

        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();

        Thread.sleep(Config.TIME_SPAN);

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getMax()).isEqualTo(Double.MIN_VALUE);
        Assertions.assertThat(statistics.getMin()).isEqualTo(Double.MAX_VALUE);
        Assertions.assertThat(statistics.getCount()).isEqualTo(0);
    }

    @Test
    public void getStatisticsWithUnorderedTransactions() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> future1 = executorService.submit(() -> postTransaction(123.24, System.currentTimeMillis() - 60500));
        Future<?> future2 = executorService.submit(() -> postTransaction(38.35, System.currentTimeMillis() - 27000));
        Future<?> future3 = executorService.submit(() -> postTransaction(90.567, System.currentTimeMillis() - 59500));
        Future<?> future4 = executorService.submit(() -> postTransaction(140.89, System.currentTimeMillis() - 10000));
        Future<?> future5 = executorService.submit(() -> postTransaction(998.01, System.currentTimeMillis() - 5000));
        Future<?> future6 = executorService.submit(() -> postTransaction(8.43, System.currentTimeMillis() - 75000));
        Future<?> future7 = executorService.submit(() -> postTransaction(28.67, System.currentTimeMillis() - 190000));

        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();
        future6.get();
        future7.get();

        Statistics statistics = statisticsController.getStatistics();
        Assertions.assertThat(statistics.getSum()).isEqualTo(1267.817, Offset.offset(0.001));
        Assertions.assertThat(statistics.getAvg()).isEqualTo(316.95425, Offset.offset(0.001));
        Assertions.assertThat(statistics.getMax()).isEqualTo(998.01);
        Assertions.assertThat(statistics.getMin()).isEqualTo(38.35);
        Assertions.assertThat(statistics.getCount()).isEqualTo(4);
    }

    private void postTransaction(double amount) {
        postTransaction(amount, System.currentTimeMillis());
    }

    private void postTransaction(double amount, long timestamp) {
        transactionsController.postTransaction(new Transaction(amount, timestamp));
    }
}
