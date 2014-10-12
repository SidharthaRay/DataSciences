package com;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class WordCountReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	public void reduce(Text key, Iterator<IntWritable> valuesIterator,
			OutputCollector<Text, IntWritable> outputCollector, Reporter reporter)
			throws IOException {
		int sum = 0;
		while(valuesIterator.hasNext()) {
			sum += valuesIterator.next().get();
		}
		outputCollector.collect(key, new IntWritable(sum));
	}

}
