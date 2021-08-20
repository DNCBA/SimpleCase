package com.mhc.jmx.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ManagedResource(objectName = "mhc:name=dncba")
public class Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

    public static volatile int count = 0;

    public void run() {
        while (true) {
            try {
                count++;
                LOGGER.info("service run count:{}", count);
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                LOGGER.error("exception when service run ", e);
            }
        }
    }

    @ManagedAttribute(description = "getCount")
    public int getCount() {
        LOGGER.info("jmx getCount :{}", count);
        return count;
    }

    @ManagedOperation
    @ManagedOperationParameter(name = "count", description = "count")
    public void resetCount(Integer count) {
        Service.count = count;
        LOGGER.info("service count :{}", Service.count);
    }
}
