package com.mhc.jdbc;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

/**
 * JDBC连接的样例
 */
public class JDBC {

    @Test
    public void test() throws SQLException {

        //原生jdbc的链接
        insert();
        find();

        //使用c3p0的链接
        Connection connection = getConnection();

        //使用dbutils
        simpleInsert();
        simpleFind();

        //使用jdbctemplate
        testJdbcTemplate();


    }

    @Test
    public void testJdbcTemplate() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcTemplateConf.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        Integer integer = jdbcTemplate.queryForObject("select count(*) from alarm_definition  where tags like ? ", Integer.class, "\"cluster\":[\"*\"]");
//        Integer integer = jdbcTemplate.queryForObject("select count(*) from alarm_definition  where tags like 1 and updatexml(1,concat(0x7e,database(),0x7e,user(),0x7e,@@datadir),1)  ", Integer.class );
        System.out.println(integer);
    }


    /**
     * 使用dbutlis简化开发
     */
    private void simpleFind() throws SQLException {
        QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
        String sql = "insert into tb_user values (?,?,?)";
        int result = runner.update(sql, null, "zs", 18);
        System.out.println(result);
    }

    private void simpleInsert() throws SQLException {
        QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
        String sql = "select * from tb_user";
        List<User> list = runner.query(sql, new BeanListHandler<User>(User.class));
        System.out.println(list);
    }


    /**
     * 使用c3p0连接池
     */
    public Connection getConnection() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection connection = dataSource.getConnection();
        return connection;
    }

    /**
     * 原生jdbc的链接和查询
     */
    public void insert(){

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得链接
            String name = "root";
            String password = "354710644";
            String jdbcurl = "jdbc:mysql://localhost:3306/jdbcdemo?charcterEnoding=UTF-8";   //如果不加字符集可能会乱码
            connection = DriverManager.getConnection(jdbcurl, name, password);
            //3.创建stament
            String sql = "insert into tb_user values (?,?,?)";
            statement = connection.prepareStatement(sql);
            //4.添加参数stament
            statement.setObject(1,null);
            statement.setObject(2,"zs");
            statement.setObject(3,18);
            //5.执行stament
            statement.execute();
            //6.解析结果集

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //7.释放资源
            if (null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != statement ){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void find(){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得链接
            String name = "root";
            String password = "354710644";
            String jdbcurl = "jdbc:mysql://localhost:3306/jdbcdemo?charcterEnoding=UTF-8";   //如果不加字符集可能会乱码
            connection = DriverManager.getConnection(jdbcurl, name, password);
            //3.创建stament
            String sql = "select * from tb_user";
            statement = connection.prepareStatement(sql);
            //4.注入参数
            //5.执行stament
            resultSet = statement.executeQuery();
            //6.解析结果
            LinkedList<User> list = new LinkedList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId((Integer) resultSet.getObject(1));
                user.setName((String) resultSet.getObject(2));
                user.setAge((Integer) resultSet.getObject(3));
                list.add(user);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //7.释放资源
            if (null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != statement ){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != resultSet){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
