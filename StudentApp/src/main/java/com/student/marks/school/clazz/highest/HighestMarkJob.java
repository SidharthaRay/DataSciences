package com.student.marks.school.clazz.highest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HighestMarkJob {

	public static void main(String[] args) throws Exception {

		args = new String[] {
				"/home/cloudera/workspace/StudentApp/src/main/resources/input/students-db.txt",
				"/home/cloudera/workspace/StudentApp/src/main/resources/temp",
				"/home/cloudera/workspace/StudentApp/src/main/resources/output" };

		// Sum total marks calculation job
		Configuration totalMarkJobConf = new Configuration();
		Job totalMarkJob = new Job(totalMarkJobConf,
				"Sum total marks calulation job");
		totalMarkJob.setJarByClass(HighestMarkJob.class);
		totalMarkJob.setJobID(new JobID("TotalMarkJobConf", 1));

		totalMarkJob.setMapperClass(TotalMarkMapper.class);
		totalMarkJob.setReducerClass(TotalMarkReducer.class);

		totalMarkJob.setMapOutputKeyClass(Text.class);
		totalMarkJob.setMapOutputValueClass(IntWritable.class);
		totalMarkJob.setOutputKeyClass(NullWritable.class);
		totalMarkJob.setOutputValueClass(Text.class);

		// Sum total marks calculation job
		Configuration maxMarkJobConf = new Configuration();
		Job maxMarkJob = new Job(maxMarkJobConf, "Max mark calulation job");
		maxMarkJob.setJarByClass(HighestMarkJob.class);
		maxMarkJob.setJobID(new JobID("MaxMarkJobConf", 1));

		maxMarkJob.setMapperClass(HighestMarkMapper.class);
		maxMarkJob.setReducerClass(HighestMarkReducer.class);

		maxMarkJob.setMapOutputKeyClass(Text.class);
		maxMarkJob.setMapOutputValueClass(Text.class);
		maxMarkJob.setOutputKeyClass(NullWritable.class);
		maxMarkJob.setOutputValueClass(Text.class);

		// Chaining both the jobs
//		FileSystem fileSystem = null;
		JobControl jobControl = new JobControl("Highest mark Calculator");
		ControlledJob ctrldTotalMarkJob = new ControlledJob(totalMarkJobConf);
		ctrldTotalMarkJob.setJob(totalMarkJob);
		FileInputFormat.addInputPath(totalMarkJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(totalMarkJob, new Path(args[1]));
		jobControl.addJob(ctrldTotalMarkJob);
		ControlledJob ctrldMaxMarkJob = new ControlledJob(maxMarkJobConf);
		ctrldMaxMarkJob.setJob(maxMarkJob);
		// Remove output dir if present before.
		// fileSystem = FileSystem.get(rfmAttrExtrctrJobConf);
		// fileSystem.delete(new Path(args[2]), true);
		FileInputFormat.addInputPath(maxMarkJob, new Path(args[1] + "/part*"));
		FileOutputFormat.setOutputPath(maxMarkJob, new Path(args[2]));
		jobControl.addJob(ctrldMaxMarkJob);
		ctrldMaxMarkJob.addDependingJob(ctrldTotalMarkJob);

		Thread thread = new Thread(jobControl);
		thread.start();
		while (!jobControl.allFinished()) {
			try {
				Thread.sleep(10000);
				// Remove temp dir.
				// fileSystem = FileSystem.get(rfmAttrExtrctrJobConf);
				// fileSystem.delete(new Path(args[1]), true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jobControl.stop();
	}

}
