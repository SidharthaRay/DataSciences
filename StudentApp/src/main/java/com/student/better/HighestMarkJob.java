package com.student.better;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HighestMarkJob {

	public static void main(String[] args) throws Exception {

		args = new String[] {
				"/home/cloudera/workspace/StudentApp/src/main/resources/input/students-db.txt",
				"/home/cloudera/workspace/StudentApp/src/main/resources/output" };

		Job job = new Job();
		job.setJarByClass(HighestMarkJob.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(TotalMarkMapper.class);
		job.setReducerClass(TotalMarkReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		System.out.println(job.waitForCompletion(true) ? 0 : 1);
	}

}
