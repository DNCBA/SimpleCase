package com.mhc.exporter.client.metrics;

import io.prometheus.client.Histogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HistogramMetrics implements Process {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistogramMetrics.class);
    private static final Histogram HISTOGRAM = Histogram.build().name("my_histogram").labelNames("h_lable").help("is help").register();
    private static final Random r = new Random();

    @Override
    public void process() {
        double v = r.nextDouble();
        Histogram.Timer timer = HISTOGRAM.labels("h_value").startTimer();
        LOGGER.info("Histogram observe {}", v);
        timer.observeDuration();
    }
}
