package com.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class NLineInputFormatDriver extends Configured implements Tool {
	
	public static void main(String[] args) {
		try {
			int result = ToolRunner.run(new Configuration(), new NLineInputFormatDriver(), args);
			System.exit(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job(getConf());
		job.setJobName("NLineInputFormat Example");
		job.setJarByClass(getClass());
		
		job.setInputFormatClass(NLineInputFormat.class);
		NLineInputFormat.setInputPaths(job, new Path("resources/input"));
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 10);
		LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path("resources/output"));
		
		job.setMapperClass(NLineInputFormatMapper.class);
		job.setNumReduceTasks(0);
		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}
}
