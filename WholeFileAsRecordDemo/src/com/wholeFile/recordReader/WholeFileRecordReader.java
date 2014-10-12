package com.wholeFile.recordReader;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;

public class WholeFileRecordReader implements RecordReader<NullWritable, BytesWritable>{

	private FileSplit fileSpilt;
	private Configuration conf;
	private boolean processed = false;
	
	public WholeFileRecordReader(FileSplit fileSplit, Configuration conf) {
		this.fileSpilt = fileSplit;
		this.conf = conf;
	}
	
	@Override
	public NullWritable createKey() {
		return NullWritable.get();
	}

	@Override
	public BytesWritable createValue() {
		return new BytesWritable();
	}

	@Override
	public long getPos() throws IOException {
		return processed ? fileSpilt.getLength() : 0;
	}

	@Override
	public float getProgress() throws IOException {
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public boolean next(NullWritable key, BytesWritable value)
			throws IOException {
		if(!processed) {
			byte[] content = new byte[(int) fileSpilt.getLength()];
			Path file = fileSpilt.getPath();
			FileSystem fs = file.getFileSystem(conf);
			FSDataInputStream in = null;
			try {
				in = fs.open(file);
				IOUtils.readFully(in, content, 0, content.length);
				value.set(content, 0, content.length);
			} finally {
				IOUtils.closeStream(in);
			}
			processed = true;
			return true;
		}
		return false;
	}

	@Override
	public void close() throws IOException {
		//do nothing
	}

}
