package com.mhc.exporter.client.metrics;

import io.prometheus.client.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaugeMetrics implements Process {
    private static final Logger LOGGER = LoggerFactory.getLogger(GaugeMetrics.class);
    private static final Gauge GAUGE = Gauge.build().name("my_gauge").labelNames("g_lable")
            .help("this is gauge help").register();


    @Override
    public void process() {
        GAUGE.labels("g_value").inc();
        LOGGER.info("gaugeMetrics inc");
    }
}
