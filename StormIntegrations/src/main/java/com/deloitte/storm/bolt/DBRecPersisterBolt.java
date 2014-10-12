package com.deloitte.storm.bolt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.deloitte.storm.topology.DataSource;
import com.deloitte.util.ApplicationConstants;

public class DBRecPersisterBolt implements IRichBolt {
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private int recCounter = 0;
	private Map<String, String> dbConfig;
	private Map<String, String> categoryMap;
	private String interpreterTable;

	@SuppressWarnings("unchecked")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		try {
			interpreterTable = (String) stormConf.get(ApplicationConstants.INTRP_15MIN_TBL_NAME);
			connection = DataSource.getInstance(dbConfig).getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection
					.prepareStatement("insert into " + interpreterTable + "(IPADDRESS, USERNAME, VISITDATE, PAGEURL, CATEGORY) values(?, ?, TO_TIMESTAMP_TZ (?, 'DD/MON/YYYY:HH24:MI:SS TZH:TZM'), ?, ?)");
		} catch (Exception e) {
			throw new RuntimeException(
					"JDBC prepared statement creation failed at DBPersistBolt.prepare()", e);
		}
	}

	public void execute(Tuple input) {
		try {
			String pageUrl = null;
			preparedStatement.setString(1,
					input.getStringByField(ApplicationConstants.INTRP_15MIN_IPADDRESS));
			preparedStatement.setString(2,
					input.getStringByField(ApplicationConstants.INTRP_15MIN_USERNAME));
			preparedStatement.setString(3,
					input.getStringByField(ApplicationConstants.INTRP_15MIN_VISITDATE));
			pageUrl = input.getStringByField(ApplicationConstants.INTRP_15MIN_PAGEURL);
			preparedStatement.setString(4, pageUrl);
			preparedStatement.setString(5, categoryMap.get(pageUrl));
			preparedStatement.addBatch();
			if (++recCounter % 1000 == 0) {
				preparedStatement.executeBatch();
				connection.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"JDBC prepared statement execution failed at DBPersistBolt.prepare()",	e);
		}
		collector.ack(input);
	}

	public void cleanup() {
		try {
			preparedStatement.executeBatch(); // insert remaining records
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(
					"JDBC preparedStatement execution failed at DBPersistBolt.cleanup()", e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (Exception e) {
				throw new RuntimeException(
						"JDBC preparedstatement/connection closing failed at DBPersistBolt.cleanup()", e);
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// declarer.declare(new Fields("Interpreter15Min"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	public Map<String, String> getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(Map<String, String> dbConfig) {
		this.dbConfig = dbConfig;
	}

	public Map<String, String> getCategoryMap() {
		return categoryMap;
	}

	public void setCategoryMap(Map<String, String> categoryMap) {
		this.categoryMap = categoryMap;
	}

}