package com.trg.mr.client;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import com.trg.mr.mapper.MaxTemperatureMapper;
import com.trg.mr.reducer.MaxTemperatureReducer;

public class MaxTemperature {
	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(MaxTemperature.class);
		conf.setJobName("Max temperature");
		//We can mention any number of files, folders or both like this...
		FileInputFormat.addInputPath(conf, new Path("resources/input/sample.txt"));
		//Make sure there is no such folder already exists with name "resources/output" 
		FileOutputFormat.setOutputPath(conf, new Path("resources/output"));
		conf.setMapperClass(MaxTemperatureMapper.class);
		conf.setReducerClass(MaxTemperatureReducer.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		JobClient.runJob(conf);
	}
}