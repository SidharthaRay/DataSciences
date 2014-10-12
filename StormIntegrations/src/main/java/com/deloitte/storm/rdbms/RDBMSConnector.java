package com.deloitte.storm.rdbms;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * Class that establishes a connection with rdbms and returns an Connection object
 */
public class RDBMSConnector {
    String dbUrl = null;
    String dbClass = null;
    Connection con = null;
    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        dbUrl =  sqlDBUrl + "?user="+ sqlUser +"&password=" + sqlPassword;
        dbClass = "org.apache.phoenix.jdbc.PhoenixDriver";
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        con = DriverManager.getConnection ("jdbc:phoenix:localhost");
        return con;
    }
}
