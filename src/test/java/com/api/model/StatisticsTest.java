package com.api.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StatisticsTest {
    @Test
    public void reset() {
        Statistics statistics = new Statistics();
        statistics.setAvg(11.11);
        statistics.setCount(12);
        statistics.setMax(12.12);
        statistics.setMin(10.10);
        statistics.setSum(121.56);

        // Method call
        statistics.reset();

        // Checks
        Assertions.assertThat(statistics.getAvg()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getSum()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getMax()).isEqualTo(Double.MIN_VALUE);
        Assertions.assertThat(statistics.getMin()).isEqualTo(Double.MAX_VALUE);
        Assertions.assertThat(statistics.getCount()).isEqualTo(0);
    }

    @Test
    public void updateValueWithEmptyStatistics() {
        Statistics statistics = new Statistics();

        // Method call
        double value = 456.123;
        statistics.updateValue(value);

        Assertions.assertThat(statistics.getCount()).isEqualTo(1);
        Assertions.assertThat(statistics.getSum()).isEqualTo(value);
        Assertions.assertThat(statistics.getMax()).isEqualTo(value);
        Assertions.assertThat(statistics.getMin()).isEqualTo(value);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(value);
    }

    @Test
    public void updateValueWithExistingStatistics() {
        Statistics statistics = new Statistics();
        statistics.setCount(12);
        statistics.setSum(121.56);
        statistics.setMax(12.12);
        statistics.setMin(10.10);
        statistics.setAvg(11.11);


        // Method call
        double value = 456.123;
        statistics.updateValue(value);

        Assertions.assertThat(statistics.getCount()).isEqualTo(13);
        Assertions.assertThat(statistics.getSum()).isEqualTo(577.683);
        Assertions.assertThat(statistics.getMax()).isEqualTo(456.123);
        Assertions.assertThat(statistics.getMin()).isEqualTo(10.10);
        Assertions.assertThat(statistics.getAvg()).isEqualTo(44.43715384615385);
    }
}