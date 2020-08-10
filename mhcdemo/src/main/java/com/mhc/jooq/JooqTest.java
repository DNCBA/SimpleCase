package com.mhc.jooq;

import com.alibaba.fastjson.JSON;
import com.mhc.jooq.codegen.Tables;
import com.mhc.jooq.codegen.tables.S1User;
import com.mhc.jooq.codegen.tables.records.S1UserRecord;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-08 20:46
 */
public class JooqTest {


  public static void main(String[] args) throws SQLException {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setUsername("root");
    hikariDataSource.setPassword("123456");
    hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/learn-jooq?serverTimezone=GMT%2B8");

    Connection connection = hikariDataSource.getConnection();
    DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

    Result<Record> recordResult = dslContext.select().from(Tables.S1_USER).fetch();

//    System.out.println(JSON.toJSONString(recordResult));

    Result<S1UserRecord> s1UserRecords = recordResult.into(Tables.S1_USER);
    List<S1User> s1UserList = recordResult.into(S1User.class);
    for (S1UserRecord s1UserRecord : s1UserRecords) {
      String address = s1UserRecord.getAddress();
      System.out.println(address);
    }

    System.out.println(s1UserRecords.toString());
    System.out.println(s1UserList.toString());



  }

}
