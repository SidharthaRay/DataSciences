package com.student.better;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TotalMarkReducer extends Reducer<Text, IntWritable, NullWritable, Text> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		StringBuffer output = new StringBuffer(key.toString());
		int totalMarks = 0;
		for (IntWritable value : values) {
			totalMarks += value.get();
		}
		output.append(",");
		output.append(totalMarks);
		//<school,class,roll,total_mark>
		context.write(NullWritable.get(), new Text(output.toString()));
	}

}
