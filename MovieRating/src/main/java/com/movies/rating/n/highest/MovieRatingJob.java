package com.movies.rating.n.highest;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MovieRatingJob {

	public static void main(String[] args) throws Exception {

		args = new String[] {
				"/home/cloudera/workspace/MovieRating/src/test/resources/input/movie/movie.txt",
				"/home/cloudera/workspace/MovieRating/src/test/resources/input/rating/movierating.txt",
				"/home/cloudera/workspace/MovieRating/src/test/resources/output" };

		Job job = new Job();
		Configuration conf = job.getConfiguration();
		DistributedCache.addCacheFile(new URI(args[1]),conf);
		job.setJarByClass(MovieRatingJob.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		job.setMapperClass(MovieRatingMapper.class);
		job.setReducerClass(MovieRatingReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		System.out.println(job.waitForCompletion(true) ? 0 : 1);
	}

}
