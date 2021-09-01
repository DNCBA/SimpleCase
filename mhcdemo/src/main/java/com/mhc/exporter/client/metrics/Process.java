package com.mhc.exporter.client.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public interface Process {

    static final Logger LOGGER = LoggerFactory.getLogger(Process.class);

    default void run(){
        try {
            while (true) {
                process();
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            LOGGER.error("exception when run ", e);
        }
    }

    void process();
}
