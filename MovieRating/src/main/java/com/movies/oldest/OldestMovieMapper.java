package com.movies.oldest;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OldestMovieMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable offset, Text data, Context context)
			throws IOException, InterruptedException {
		context.write(new Text("Oldest Movie"), data);

	}
}