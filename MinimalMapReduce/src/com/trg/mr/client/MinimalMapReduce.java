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
	public int run(String[] args) throws Exception {
		String inputPath = null, outputPath = null;
		if(args != null && args.length == 2) {
			inputPath = args[0];
			outputPath = args[1];
		} else {
			inputPath = "resources/input/sample.txt";
			outputPath = "resources/output";
		}
		JobConf jobConf = new JobConf(getConf(), getClass());
		FileInputFormat.addInputPath(jobConf, new Path(inputPath));
		FileOutputFormat.setOutputPath(jobConf, new Path(outputPath));
		JobClient.runJob(jobConf);
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new MinimalMapReduce(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
