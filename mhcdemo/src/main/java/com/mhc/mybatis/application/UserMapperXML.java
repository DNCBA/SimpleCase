package com.mhc.mybatis.application;

import java.util.HashMap;
import java.util.Map;

public class UserMapperXML {

    public static String NAMESPEACES = "";

    public static Map<String,String> METHODMAPPING = new HashMap<String,String>();

    static {
        METHODMAPPING.put("save","");
        METHODMAPPING.put("deleteById","");
        METHODMAPPING.put("selectById","");
        METHODMAPPING.put("update","");
    }
}
