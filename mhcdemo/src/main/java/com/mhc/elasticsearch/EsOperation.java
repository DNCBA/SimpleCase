package com.mhc.elasticsearch;


import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class EsOperation {

    public static void main(String[] args) {


        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElasticsearchTemplate elasticsearchTemplate = applicationContext.getBean(ElasticsearchTemplate.class);
        elasticsearchTemplate.createIndex("qwerqwe");
        elasticsearchTemplate.createIndex("qwerqwe","{}");


        //esClient();

    }

    private static void esClient() {
        try {
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).
                    addTransportAddress(
                            new TransportAddress(
                                    InetAddress.getByName("114.116.67.84"), 9300));

            //创建索引和添加数据（setSource()可以传递多种数据）
            client.prepareIndex("myindex3", "test").setSource("{\"name\":\"zs\",\"age\":19}", XContentType.JSON).get();
            //查询指定数据
            client.prepareGet("myindex","test","1").get();
            //删除指定数据
            client.prepareDelete().setIndex("myindex3").setType("test").setId("1").get();
            //查询
            client.prepareSearch("myindex").get();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
