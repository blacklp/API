package com.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Statistics {
    private double sum;

    private double avg;

    private double max;

    private double min;

    private long count;

    public Statistics() {
        init();
    }

    private void init() {
        this.sum = 0.0;
        this.avg = 0.0;
        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;
        this.count  = 0;
    }

    public void reset() {
        init();
    }

    public void updateValue(double value) {
        this.sum += value;
        this.count++;
        this.avg = sum / count;

        if (value > this.max) {
            this.max = value;
        }
        if (value < this.min) {
            this.min = value;
        }
    }
}
