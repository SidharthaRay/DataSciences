package com.spout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

public class RecordReaderSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;
	private Connection conn;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public void nextTuple() {
		while(true) {
			try {
				while(resultSet.next()) {
					String val = resultSet.getString(1) + " <<=>> " + resultSet.getString(2);
					System.out.println("RecordReaderSpout>>>>>>>>>>>>>>>" + val);
					collector.emit(Arrays.asList(new Object[]{val}));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void open(Map map, TopologyContext con, SpoutOutputCollector collector) {
		this.collector = collector;
		try {
			Class.forName("com.vertica.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:vertica://10.25.36.162:5433/EDMP", "dbadmin", "Password1");
			preparedStatement = conn.prepareStatement("SELECT * FROM CNBO.PAGE_CATEGORY");
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.8.76.189:1521:XE", "cnbo", "Temp1234");
//			preparedStatement = conn.prepareStatement("SELECT * FROM PAGE_CATEGORY");
			resultSet = preparedStatement.executeQuery();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("record"));
	}

	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public void fail(Object arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
