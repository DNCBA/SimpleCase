package com.mhc.rpc.client;

import com.mhc.rpc.api.IRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 13:59
 */
public class RpcServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceClient.class);

    public static void main(String[] args) {
        ProxyClient proxyClient = new ProxyClient("localHost",8090);
        IRpcService service = proxyClient.getClient(IRpcService.class);
        String result = service.sayHello("tome");
        System.out.println(result);
    }
}
