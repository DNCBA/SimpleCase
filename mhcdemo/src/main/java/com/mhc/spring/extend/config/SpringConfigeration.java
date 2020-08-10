package com.mhc.spring.extend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:34
 */
@Configuration
@ComponentScan("com.mhc.spring.extend.*")
public class SpringConfigeration {

  @Bean("map")
  public Map getMap() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("map",1);
    return hashMap;
  }

  @Bean("list")
  public List getList() {
    ArrayList<Object> arrayList = new ArrayList<>();
    arrayList.add("list");
    return arrayList;
  }


  @Bean("set")
  public Set getSet() {
    HashSet<Object> hashSet = new HashSet<>();
    hashSet.add("set");
    return hashSet;
  }


  @Bean("string")
  public String getString() {
    return "abc";
  }

}
