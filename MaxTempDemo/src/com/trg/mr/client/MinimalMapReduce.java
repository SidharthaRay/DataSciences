package com.trg.mr.client;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MinimalMapReduce extends Configured implements Tool{

	@Override
	public int run(String[] arg0) throws Exception {
		JobConf jobConf = new JobConf(getConf(), getClass());
		FileInputFormat.addInputPath(jobConf, new Path("resources/input/sample.txt"));
		FileOutputFormat.setOutputPath(jobConf, new Path("resources/output"));
		JobClient.runJob(jobConf);
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new MinimalMapReduce(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
