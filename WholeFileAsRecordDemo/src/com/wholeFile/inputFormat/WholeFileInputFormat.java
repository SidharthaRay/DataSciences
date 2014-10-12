package com.wholeFile.inputFormat;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

import com.wholeFile.recordReader.WholeFileRecordReader;

public class WholeFileInputFormat extends FileInputFormat<NullWritable, BytesWritable> {

	
	@Override
	protected boolean isSplitable(FileSystem fs, Path filename) {
		return false;
	}
	
	@Override
	public RecordReader<NullWritable, BytesWritable> getRecordReader(
			InputSplit inputSplit, JobConf jobConf,
			Reporter reporter) throws IOException {
		return new WholeFileRecordReader((FileSplit) inputSplit, jobConf);
	}

}
