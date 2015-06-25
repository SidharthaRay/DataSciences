package com.movies.rating.n.highest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MovieRatingReducer extends Reducer<Text, Text, NullWritable, Text> {
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Map<Integer, List<String>> ratingRecordMap = new TreeMap<Integer, List<String>>();
		int rating = 0;
		List<String> temp = null;
		for(Text value : values) {
			temp = Arrays.asList(value.toString().split(","));
			rating = Integer.parseInt(temp.get(0));
			if(!ratingRecordMap.containsKey(rating)) {
				List<String> list = new ArrayList<String>();
				list.add(value.toString());
				ratingRecordMap.put(rating, list);
			} else {
				ratingRecordMap.get(rating).add(value.toString());
			}
		}
		
		int counter = 0;
		for(Map.Entry<Integer, List<String>> entry : ratingRecordMap.entrySet()) {
			for(String val : entry.getValue()) {
				context.write(NullWritable.get(), new Text(val));
				counter++;
				if(counter >= 3) {
					break;
				}
			}
			if(counter >= 3) {
				break;
			}
		}
	}
	
}
