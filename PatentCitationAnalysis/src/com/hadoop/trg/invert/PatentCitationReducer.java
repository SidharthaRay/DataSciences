package com.hadoop.trg.invert;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class PatentCitationReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text>{

	@Override
	public void reduce(Text key, Iterator<Text> valueIterator,
			OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		String csv = "";
		while (valueIterator.hasNext()) {
			if(csv.length() != 0)
				csv += ", ";
			csv += valueIterator.next().toString();
		}
		output.collect(key, new Text(csv));
	}
	
}
