package com.trg.mr.client;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.trg.mr.mapper.NewMaxTemperatureMapper;
import com.trg.mr.reducer.NewMaxTemperatureReducer;

public class MaxTemeratureJobClient {
	public static void main(String[] args) {
		try {
			Job job = new Job();
			job.setJarByClass(MaxTemeratureJobClient.class);
			
			FileInputFormat.addInputPath(job, new Path("resources/input"));
			FileOutputFormat.setOutputPath(job, new Path("resources/output"));
			
			job.setMapperClass(NewMaxTemperatureMapper.class);
			job.setReducerClass(NewMaxTemperatureReducer.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			System.out.println(job.waitForCompletion(true) ? 0 : 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
