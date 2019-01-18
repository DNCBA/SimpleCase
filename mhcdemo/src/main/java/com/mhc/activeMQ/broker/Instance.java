package com.mhc.activeMQ.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class Instance {
    public static void main(String[] args) {
        try {
            BrokerService brokerService = new BrokerService();
            brokerService.setUseJmx(true);
            brokerService.setBrokerName("ActiveMQ");
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
