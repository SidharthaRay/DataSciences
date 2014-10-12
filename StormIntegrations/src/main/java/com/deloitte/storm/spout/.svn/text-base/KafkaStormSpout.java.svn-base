package com.deloitte.storm.spout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;

public class KafkaStormSpout extends BaseRichSpout {
	SpoutOutputCollector _collector;
	private static int STORM_KAFKA_FROM_READ_FROM_START = -2;
	private static int STORM_KAFKA_FROM_READ_FROM_CURRENT_OFFSET = -1;
	private static int readFromMode = STORM_KAFKA_FROM_READ_FROM_START;
	private static String TOPIC_NAME = "test";

	public void KafkaStormSpout() {
		List<String> hosts = new ArrayList<String>();
		// BrokerHosts brokerHosts = new ZkHosts("localhost");
		SpoutConfig kafkaConf = new SpoutConfig(new ZkHosts("localhost",
				"/brokers"), "mytopic", "/tmp", "discovery");
		// SpoutConfig kafkaConf = new SpoutConfig(brokerHosts, "test",
		// "/kafkastorm", "discovery");
		kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		// kafkaConf.forceStartOffsetTime(-2);

		kafkaConf.zkServers = new ArrayList<String>() {
			{
				add("localhost");
			}
		};
		kafkaConf.zkPort = 2181;
		KafkaSpout kafkaSpout = new KafkaSpout(kafkaConf);
		kafkaConf.forceFromStart = true;

	}
	
	
	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		// TODO Auto-generated method stub

		//
	}
	

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}
