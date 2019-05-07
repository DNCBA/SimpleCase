package com.mhc.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;

@Configuration
public class EsConfiguration {


    @Bean
    public Client getESClient(){
        Client client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        }catch (Exception e){
            e.printStackTrace();
            client = null;
        }
        return client;
    }



    @Bean
    public ElasticsearchTemplate getESTemplate(){
         return new ElasticsearchTemplate(getESClient());
    }

}
