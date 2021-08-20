package com.mhc.jmx.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class JmxConnection {


  private static final Logger LOGGER = LoggerFactory.getLogger(JmxConnection.class);

  public static void main(String[] args) throws Exception {
    JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9102/jmxrmi");
    JMXConnector connect = JMXConnectorFactory.connect(jmxServiceURL);
    MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();
    ObjectName objectName = new ObjectName("mhc:name=dncba");
    MBeanInfo mhc = mBeanServerConnection.getMBeanInfo(objectName);
    MBeanAttributeInfo[] attributes = mhc.getAttributes();
    MBeanOperationInfo[] operations = mhc.getOperations();
    MBeanNotificationInfo[] notifications = mhc.getNotifications();

    int size = 0;
    while (true) {
      Object attribute = mBeanServerConnection.getAttribute(objectName, attributes[0].getName());
      LOGGER.info("get count :{}", attribute);
      size++;
      TimeUnit.SECONDS.sleep(10);
      if (size >= 20) {
        String[] signature = new String[operations[0].getSignature().length];
        for (int i = 0; i < operations[0].getSignature().length; i++) {
          String name = operations[0].getSignature()[i].getType();
          signature[i] = name;
        }
        Integer[] params = new Integer[]{1};
        Object invoke = mBeanServerConnection.invoke(objectName, operations[0].getName(), params, signature);
        LOGGER.info("invoke resetCount : {}", 0);
      }
    }
  }


}
