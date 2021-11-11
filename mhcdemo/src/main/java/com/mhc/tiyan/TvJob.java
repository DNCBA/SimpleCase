package com.mhc.tiyan;

import com.mhc.jd.utils.JdHttpUtils;
import com.mhc.tiyan.framewark.Task;
import com.mhc.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TvJob extends Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(TvJob.class);

    public TvJob() {
        count = new AtomicLong(2);
        time = System.currentTimeMillis();
        name = RandomStringUtils.random(8, true, true);
    }

    @Override
    protected void run() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Length", "166");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Host", "https://order.jd.com/center/list.action");
        header.put("Origin", "https://order.jd.com/center/list.action");
        header.put("Referer", "https://order.jd.com/center/list.action");
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36");
        header.put("X-Requested-With", "XMLHttpRequest");
        JdHttpUtils httpUtils = new JdHttpUtils("");
        JdHttpUtils.HttpUtilsResponse response = httpUtils.post("marathon.jd.com", "{}", header);
        LOGGER.info("running ... {}", response.getStringBody());
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {

        }

    }
}
