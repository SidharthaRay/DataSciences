package com.trg.mr.client;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapRunner;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.HashPartitioner;
import org.apache.hadoop.mapred.lib.IdentityMapper;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MinimalMapReduceWithDefaults extends Configured implements Tool{

	@Override
	public int run(String[] arg0) throws Exception {
		JobConf jobConf = new JobConf(getConf(), getClass());
		FileInputFormat.addInputPath(jobConf, new Path("resources/input/sample.txt"));
		FileOutputFormat.setOutputPath(jobConf, new Path("resources/output1"));
		
		jobConf.setInputFormat(TextInputFormat.class);
		
		jobConf.setNumMapTasks(1);
		jobConf.setMapperClass(IdentityMapper.class);
		jobConf.setMapRunnerClass(MapRunner.class);
		
		jobConf.setMapOutputKeyClass(LongWritable.class);
		jobConf.setMapOutputValueClass(Text.class);
		
		jobConf.setPartitionerClass(HashPartitioner.class);
		
		jobConf.setNumReduceTasks(1);
		jobConf.setReducerClass(IdentityReducer.class);
		
		jobConf.setOutputKeyClass(LongWritable.class);
		jobConf.setOutputValueClass(Text.class);
		
		jobConf.setOutputFormat(TextOutputFormat.class);
		
		JobClient.runJob(jobConf);
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new MinimalMapReduceWithDefaults(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
