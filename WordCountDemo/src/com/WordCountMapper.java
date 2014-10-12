package com;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WordCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

	private IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> outputCollector, Reporter reporter)
			throws IOException {
		String line = value.toString();
		StringTokenizer tokens = new StringTokenizer(line);
		while(tokens.hasMoreElements()) {
			word.set(tokens.nextToken());
			outputCollector.collect(word, one);
		}
	}

}
