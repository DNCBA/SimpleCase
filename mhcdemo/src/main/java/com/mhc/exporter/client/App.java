package com.mhc.exporter.client;

import com.mhc.exporter.client.collect.CollectMetrics;
import com.mhc.exporter.client.metrics.CountMetrics;
import com.mhc.exporter.client.metrics.GaugeMetrics;
import com.mhc.exporter.client.metrics.HistogramMetrics;
import com.mhc.exporter.client.metrics.SummaryMetrics;
import io.prometheus.client.exporter.HTTPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 100, TimeUnit.SECONDS, new SynchronousQueue<>());

    public static void main(String[] args) {
        try {
            //register
            new CollectMetrics().register();

            executor.submit(()-> {
                CountMetrics countMetrics = new CountMetrics();
                LOGGER.info("countMetrics run");
                countMetrics.run();
            });
            executor.submit(()-> {
                GaugeMetrics gaugeMetrics = new GaugeMetrics();
                LOGGER.info("gaugeMetrics run");
                gaugeMetrics.run();
            });
            executor.submit(()-> {
                HistogramMetrics histogramMetrics = new HistogramMetrics();
                LOGGER.info("histogramMetrics run");
                histogramMetrics.run();
            });
            executor.submit(()-> {
                SummaryMetrics summaryMetrics = new SummaryMetrics();
                LOGGER.info("summaryMetrics run");
                summaryMetrics.run();

            });
            //server
            HTTPServer server = new HTTPServer(1234);
        } catch (Exception e) {
            LOGGER.error("exception when app main ", e);
        }
    }
}
