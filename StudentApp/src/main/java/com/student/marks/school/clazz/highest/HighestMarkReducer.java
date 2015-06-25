package com.student.marks.school.clazz.highest;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HighestMarkReducer extends Reducer<Text, Text, NullWritable, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		String rollNo = null;
		int maxMark = 0;
		int tempMark = 0;
		String[] tempVal = null;
		for (Text value : values) {
			tempVal = value.toString().split(",");
			tempMark = Integer.parseInt(tempVal[1]);
			if(tempMark > maxMark) {
				rollNo = tempVal[0];
				maxMark = tempMark;
			}
		}
		context.write(NullWritable.get(), new Text(key.toString() + "," + rollNo + "," + maxMark));
	}

}
