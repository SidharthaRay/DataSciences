package com.movies.oldest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.movies.constant.MoviesConstants;

public class OldestMovieReducer extends Reducer<Text, Text, NullWritable, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> data, Context context) throws IOException, InterruptedException {
		List<String> values = null;
		String record = null;
		int year = 0;
		int oldestYear = 9999;
		for (Text value : data) {
			values = Arrays.asList(value.toString().split("\\|"));
			year = Integer.parseInt(values.get(MoviesConstants.MOVIE_YEAR_INDEX));
			if(year != 0 && year < oldestYear) {
				oldestYear = year;
				record = value.toString();
			}
		}
		context.write(NullWritable.get(), new Text(record));
	}

}
