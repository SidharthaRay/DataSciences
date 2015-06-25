package com.student.marks.sort;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HighestMarkMapper extends
		Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable offset, Text data, Context context)
			throws IOException, InterruptedException {

		List<String> values = Arrays.asList(data.toString().split(","));
		context.write(new Text(values.get(0) + "," + values.get(1)),
				new Text(values.get(2) + "," + values.get(3)));

	}
}