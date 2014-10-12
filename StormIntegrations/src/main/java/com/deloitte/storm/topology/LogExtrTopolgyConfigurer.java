package com.deloitte.storm.topology;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

import backtype.storm.spout.SchemeAsMultiScheme;

import com.deloitte.util.ApplicationConstants;

public class LogExtrTopolgyConfigurer {
	
	/**
	 * This method configures Kafka SpoutConfig
	 * @param spoutProps
	 * @return SpoutConfig
	 */
	public SpoutConfig configureKafkaSpout(Properties spoutProps) {
		ZkHosts zkHosts = new ZkHosts(spoutProps.getProperty(ApplicationConstants.ZK_HOST));
		SpoutConfig spoutConfig = new SpoutConfig(
				zkHosts, 
				spoutProps.getProperty(ApplicationConstants.ZK_TOPIC), 
				spoutProps.getProperty(ApplicationConstants.ZK_ROOT), 
				spoutProps.getProperty(ApplicationConstants.ZK_ID));
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConfig.forceFromStart = true;	//Forcefully starting from the beginning of the kafka topic 
		return spoutConfig;
	}
	
	/**
	 * This method configures log message format
	 * @param logProps
	 * @return Map
	 */
	public Map<String, String> getLogFormat(Properties logProps) {
		Map<String, String> logFormatMap = new HashMap<String, String>();
		logFormatMap.put(ApplicationConstants.LOG_IPADDRESS_INDEX, logProps.getProperty(ApplicationConstants.LOG_IPADDRESS_INDEX));
		logFormatMap.put(ApplicationConstants.LOG_USERNAME_INDEX, logProps.getProperty(ApplicationConstants.LOG_USERNAME_INDEX));
		logFormatMap.put(ApplicationConstants.LOG_VISITDATE_INDEX, logProps.getProperty(ApplicationConstants.LOG_VISITDATE_INDEX));
		logFormatMap.put(ApplicationConstants.LOG_PAGEURL_INDEX, logProps.getProperty(ApplicationConstants.LOG_PAGEURL_INDEX));
		return logFormatMap;
	}
	
	/**
	 * This method configures the database connection
	 * @param dbProps
	 * @return Map
	 */
	public Map<String, String> getDBConfig(Properties dbProps) {
		Map<String, String> dbConfigMap = new HashMap<String, String>();
		dbConfigMap.put(ApplicationConstants.DB_DRIVER_CLASS, dbProps.getProperty(ApplicationConstants.DB_DRIVER_CLASS));
		dbConfigMap.put(ApplicationConstants.DB_USER_NAME, dbProps.getProperty(ApplicationConstants.DB_USER_NAME));
		dbConfigMap.put(ApplicationConstants.DB_PASSWORD, dbProps.getProperty(ApplicationConstants.DB_PASSWORD));
		dbConfigMap.put(ApplicationConstants.DB_CONNECTION_URL, dbProps.getProperty(ApplicationConstants.DB_CONNECTION_URL));
		return dbConfigMap;
	}
	
}
