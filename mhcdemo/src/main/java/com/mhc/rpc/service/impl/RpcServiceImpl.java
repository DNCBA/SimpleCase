package com.mhc.rpc.service.impl;

import com.mhc.rpc.api.IRpcService;
import org.springframework.stereotype.Service;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 13:58
 */
@Service
public class RpcServiceImpl implements IRpcService {
    @Override
    public String sayHello(String name) {
        String result = " hello " + name;
        System.out.println(result);
        return result;
    }
}
