package com.student.marks.sort;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HighestMarkReducer extends Reducer<Text, Text, NullWritable, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		TreeMap<Integer, String> sortedMap = new TreeMap<Integer, String>();
		int tempMark = 0;
		String[] tempVal = null;
		for (Text value : values) {
			tempVal = value.toString().split(",");
			tempMark = Integer.parseInt(tempVal[1]);
			sortedMap.put(tempMark, key.toString() + "," + tempVal[0] + "," + tempMark);
		}
		for(Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
			context.write(NullWritable.get(), new Text(entry.getValue()));
		}
	}

}
