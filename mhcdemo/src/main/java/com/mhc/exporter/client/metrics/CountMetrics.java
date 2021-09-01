package com.mhc.exporter.client.metrics;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountMetrics implements Process {


    private static final Logger LOGGER = LoggerFactory.getLogger(CountMetrics.class);
    private static final Counter counter = Counter.build().name("my_counter").labelNames("c_lable1", "c_lable2").help("count is help")
            .register();


    @Override
    public void process() {
        counter.labels("c_value1", "c_value2").inc();
        LOGGER.info("countMetrics inc");
    }


}
