package com.student.better;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.student.common.StudentConstants;

public class TotalMarkMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable offset, Text data, Context context)
			throws IOException, InterruptedException {
		// <school,class,roll> <-> <mark>
		List<String> values = Arrays.asList(data.toString().split("\\|"));
		context.write(
				new Text(values.get(StudentConstants.DATA_INDEX.GENDER
						.getIndex())),
				new IntWritable(Integer.parseInt(values.get(7))));

	}
}