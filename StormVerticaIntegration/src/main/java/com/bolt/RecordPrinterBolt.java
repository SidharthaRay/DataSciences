package com.bolt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class RecordPrinterBolt implements IRichBolt {

	private List<String> records = new ArrayList<String>();
	private FileWriter fileWriter;
	String fileName;

	public RecordPrinterBolt(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void prepare(Map map, TopologyContext context,
			OutputCollector collector) {
		try {
			fileWriter = new FileWriter(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
		String val = tuple.getValue(0).toString();
		System.out.println("RecordPrinterBolt>>>>>>>>>>>>>>>>>>>>>>" + val);
		records.add(val);
	}

	@Override
	public void cleanup() {
		try {
			for (String record : records) {
				fileWriter.write(record + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
