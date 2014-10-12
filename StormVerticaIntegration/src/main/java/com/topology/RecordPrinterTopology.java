package com.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

import com.bolt.RecordPrinterBolt;
import com.spout.RecordReaderSpout;

public class RecordPrinterTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new RecordReaderSpout());
		builder.setBolt("print", new RecordPrinterBolt(args[1])).shuffleGrouping("spout");
		Config conf = new Config();
		conf.setNumWorkers(1);
		try {
			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} catch (AlreadyAliveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
