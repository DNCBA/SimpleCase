package com.mhc.exporter.client.metrics;

import io.prometheus.client.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SummaryMetrics implements Process {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryMetrics.class);
    private static final Summary SUMMARY = Summary.build().name("my_summary").labelNames("s_lable").help("is help").register();
    private static final Random r = new Random();

    @Override
    public void process() {
        double v = r.nextDouble();
        Summary.Timer timer = SUMMARY.labels("h_value").startTimer();
        LOGGER.info("Summary observe {}", v);
        timer.observeDuration();
    }
}
