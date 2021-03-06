package com.mhc.mybatis.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssistantMapper {

    @Select("select nick from assistant where id = #{id} ")
    String findById(@Param("id") String id);
}
