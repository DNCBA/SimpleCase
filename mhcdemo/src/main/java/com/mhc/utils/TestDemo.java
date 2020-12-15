package com.mhc.utils;

import com.mhc.utils.HttpUtils.HttpUtilsResponse;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-12-15 14:47
 */
public class TestDemo {

  public static void main(String[] args) {
    MDC.put("traceId", UUID.randomUUID().toString().substring(0,8));
    MDC.put("mainId", UUID.randomUUID().toString().substring(0,8));
    MDC.put("subId", UUID.randomUUID().toString().substring(0,8));

    for (int i = 0; i < 10; i++) {
      Optional<HttpUtilsResponse> httpUtilsResponse = HttpUtils
          .get("http://stg-api.leyanbot.com/marketing-ops/v1/import-task?pageNum=1&pageSize=10");

      httpUtilsResponse.ifPresent(resp -> {
        System.out.println(resp.code);
        System.out.println(resp.StringBody);
      });
    }


  }

}
