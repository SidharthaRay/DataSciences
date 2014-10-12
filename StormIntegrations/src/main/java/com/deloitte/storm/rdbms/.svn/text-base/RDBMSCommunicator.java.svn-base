package com.deloitte.storm.rdbms;


import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/*
 * This class implements method for inserting data into RDBMS tables
 */
public class RDBMSCommunicator {
    private Connection con = null;
    private PreparedStatement prepstmt = null;
    private String queryStmt = null, queryValues = "";
    private int noOfColumns = 0, result = 0;
    private ResultSet rs = null;
    Map<String, String> tableDetails;
    public RDBMSCommunicator(Connection con) {
        super();
        this.con = con;
    }
    public int insertRow(String tableName, ArrayList<String> fieldNames, ArrayList<Object> fieldValues) throws SQLException {
        result = 0;
        try {       
            prepstmt = null;
            queryValues = "";
            noOfColumns = fieldNames.size();           
            queryStmt = "upsert into " + tableName + "(event_id, ";
            
            Long timestamp = new Date().getTime();
            queryValues = queryValues + timestamp + ", ";
            for (int i = 0; i <= noOfColumns - 1; i++) {
            	
                if (i != noOfColumns - 1) {
                    queryStmt = queryStmt + fieldNames.get(i) + ", ";
                    if(fieldNames.get(i).equals("eventdate")){
                		queryValues = queryValues + "to_date(?,'dd/MMM/yyyy'), ";
                	}else if(fieldNames.get(i).equals("eventtime")){
                		queryValues = queryValues + "to_date(?,'hh:mm:ss'), ";
                	}else{
                		queryValues = queryValues + "?, ";
                	}
                    
                } else {
                    queryStmt = queryStmt + fieldNames.get(i) + ") ";
                    queryValues = queryValues + "?";
                }
            }
            queryStmt = queryStmt + " values(" +  queryValues + ")";
            //System.out.println(queryStmt);
            prepstmt = con.prepareStatement(queryStmt);
            
            
            for (int j = 0; j <= noOfColumns - 1; j++) {
            	
                prepstmt.setObject(j + 1, fieldValues.get(j));
               
            }
            //System.out.println(prepstmt.toString());
            result = prepstmt.executeUpdate();
            if (result != 0) {
            	con.commit();
                System.out.println("Inserted data successfully ..");
            } else {
                System.out.println("Insertion failed ..");   
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public Map<String, String> getTableInformation(String tableName) {
        tableDetails = new HashMap<String, String>();
        try {
        String stmt = "select column_name, data_type, character_maximum_length from information_schema.columns where table_name = '" + tableName + "'";
        //System.out.println(stmt);
        PreparedStatement prepstmt = null;
        prepstmt = con.prepareStatement(stmt);
        rs = prepstmt.executeQuery();
        while(rs.next()) {
            tableDetails.put(rs.getString("column_name"), rs.getString("data_type"));
        }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tableDetails;
    }
}
