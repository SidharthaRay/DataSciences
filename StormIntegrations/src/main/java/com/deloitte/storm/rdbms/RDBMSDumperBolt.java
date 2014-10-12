package com.deloitte.storm.rdbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
/*
 * Bolt for dumping stream data into RDBMS
 */
public class RDBMSDumperBolt  implements IBasicBolt {
    private static final long serialVersionUID = 1L;
    private static transient RDBMSCommunicator communicator = null;
    private transient RDBMSConnector connector = new RDBMSConnector();
    private transient Connection con = null;
    private String tableName = null;
    private ArrayList<Object> fieldValues = new ArrayList<Object>();
    private ArrayList<String> fieldNames = new ArrayList<String>();
    private Set<String> keySet = new HashSet<String>();
    private List<String> list = null;
    public RDBMSDumperBolt(String tableName, ArrayList<String> columnNames)           throws SQLException {
        super();
        this.tableName = tableName;
        this.fieldNames = columnNames;
        
     
    }
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
    	
    	
    	   try {
               //con = connector.getConnection();
           	 String dbClass = "org.apache.phoenix.jdbc.PhoenixDriver";
                Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
                con = DriverManager.getConnection ("jdbc:phoenix:localhost");
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           communicator = new RDBMSCommunicator(con);
           
           
           
           
        keySet = null;
        fieldValues = new ArrayList<Object>();
        System.out.println(input.toString());
        fieldValues = (ArrayList<Object>) input.getValues();
        System.out.println(fieldValues.toString());
        
        //keySet = input.();
       // list = new ArrayList<String>();
        //list = (List<String>) input.select(arg0)
        try {
            communicator.insertRow(this.tableName, fieldNames, fieldValues);
            
    			con.close();
    		
        } catch (SQLException e) {
            System.out.println("Exception occurred in adding a row ");
            e.printStackTrace();
        }
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {}
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
    @Override
    public void prepare(Map stormConf, TopologyContext context) {}
    @Override
    public void cleanup() {
    	
    	
    }
}
