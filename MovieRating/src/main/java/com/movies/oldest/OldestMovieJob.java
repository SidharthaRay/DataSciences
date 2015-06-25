package com.movies.oldest;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class OldestMovieJob {

	public static void main(String[] args) throws Exception {

		args = new String[] {
				"/home/cloudera/workspace/MovieRating/src/test/resources/input/movie/movie.txt",
				"/home/cloudera/workspace/MovieRating/src/test/resources/output" };

		Job job = new Job();
		job.setJarByClass(OldestMovieJob.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(OldestMovieMapper.class);
		job.setReducerClass(OldestMovieReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		System.out.println(job.waitForCompletion(true) ? 0 : 1);
	}

}
