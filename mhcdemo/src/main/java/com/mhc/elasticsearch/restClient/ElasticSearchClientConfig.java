package com.mhc.elasticsearch.restClient;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClient.FailureListener;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-05-08 14:40
 */
@Configuration
public class ElasticSearchClientConfig {

  @Bean
  public RestClient getRestClient() {
    HttpHost httpHost = new HttpHost("localhost", 9200, "http");
    RestClient client = RestClient.builder(httpHost)
        .setFailureListener(new FailureListener() {
          @Override
          public void onFailure(Node node) {
            super.onFailure(node);
            System.out.println(node);
          }
        })
        .setRequestConfigCallback(builder ->
            builder.setAuthenticationEnabled(true) //认证
                .setConnectionRequestTimeout(10)  //超时
                .setConnectTimeout(10) //连接超时
                .setTargetPreferredAuthSchemes(new ArrayList<>()))
        .build();
    return client;
  }


  @Bean
  public RestHighLevelClient getRestHightLevelClient() {
    HttpHost httpHost = new HttpHost("localhost", 9200, "http");
    RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));
    return restHighLevelClient;
  }


}
