package com.deloitte.storm.bolt;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.deloitte.util.ApplicationConstants;

public class LogExtractorBolt implements IRichBolt {
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private Pattern logPattern;
	private Map<String, String> logFormat;
	
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		StringBuilder logFormat = new StringBuilder();
		logFormat.append("(\\s*\\d+\\.\\d+.\\d+.\\d+\\s*)");
		logFormat.append("(\\s*-\\s+-\\s*)");
		logFormat.append("(\\s*\\[\\d+/\\w+/\\d+:\\d+:\\d+:\\d+\\s+-\\d+\\]\\s*)");
		logFormat.append("(\\s*\"\\w+\\s*)");
		logFormat.append("(\\s*[\\w\\d/\\.]+\\s*)");
		logFormat.append("(\\s*[\\w\\d\\./]+\")");
		logFormat.append("(\\s*\\d+\\s+)");
		logFormat.append("(\\s*\\d+\\s+)");
		logFormat.append("(\\s*\".*\"\\s*)");
		logFormat.append("(\\s*\"[.\\w\\W\\d\\D]*\"\\s*)");
		logPattern = Pattern.compile(logFormat.toString());
	}

	public void execute(Tuple input) {
		String log = input.getString(0);
		
		Matcher matcher = logPattern.matcher(log);
		String ipAddress = null;
		String username = null;
		String visitDate = null;
		String pageUrl = null;
		int index;
		while (matcher.find()) {
			index = Integer.parseInt(logFormat.get(ApplicationConstants.LOG_IPADDRESS_INDEX));
			ipAddress = matcher.group(index);
			index = Integer.parseInt(logFormat.get(ApplicationConstants.LOG_USERNAME_INDEX));
			username = matcher.group(index);
			index = Integer.parseInt(logFormat.get(ApplicationConstants.LOG_VISITDATE_INDEX));
			visitDate = matcher.group(index).replace("[", "").replace("]", "");
			index = Integer.parseInt(logFormat.get(ApplicationConstants.LOG_PAGEURL_INDEX));
			pageUrl = matcher.group(index);
		}
		collector.emit(input, 
				new Values(ipAddress, username, visitDate, pageUrl));
		collector.ack(input);
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(
				ApplicationConstants.INTRP_15MIN_IPADDRESS,
				ApplicationConstants.INTRP_15MIN_USERNAME,
				ApplicationConstants.INTRP_15MIN_VISITDATE,
				ApplicationConstants.INTRP_15MIN_PAGEURL));
	}

	public void cleanup() {
	}

	private void fail() {

	}
	
	private void ack() {

	}
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	public Map<String, String> getLogFormat() {
		return logFormat;
	}

	public void setLogFormat(Map<String, String> logFormat) {
		this.logFormat = logFormat;
	}
}