package com.mhc.exporter.client.collect;

import io.prometheus.client.Collector;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectMetrics extends Collector {
    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();
        String random = RandomStringUtils.random(8, true, true);
        MetricFamilySamples.Sample sample1 = new MetricFamilySamples.Sample("my_collect", Arrays.asList("lbale1", "lasle2"), Arrays.asList("value1", "value2"), 2.0);
        MetricFamilySamples.Sample sample2 = new MetricFamilySamples.Sample("my_collect", Arrays.asList("lbale1", "lable2", "lable3"), Arrays.asList("value1", "value2", random), 3.0);
        MetricFamilySamples metricFamilySamples = new MetricFamilySamples("my_collect", Type.COUNTER, "is help", Arrays.asList(sample1, sample2));
        mfs.add(metricFamilySamples);
        return mfs;
    }
}
