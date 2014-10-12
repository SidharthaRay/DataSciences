package com.hadoop.trg.invert;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.util.Tool;

public class InvertJob extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Configuration configuration = getConf();
		JobConf jobConf = new JobConf(configuration, getClass());
		
		Path inputPath = new Path("/tmp/SampleData4Hadoop/input");
		Path outputPath = new Path("/tmp/SampleData4Hadoop/output");
		FileInputFormat.setInputPaths(jobConf, inputPath);
		FileOutputFormat.setOutputPath(jobConf, outputPath);
		
		jobConf.setJobName("InvertCitation");
		jobConf.setMapperClass(PatentCitationMapper.class);
		jobConf.setReducerClass(PatentCitationReducer.class);
		
		jobConf.setInputFormat(KeyValueTextInputFormat.class);
		jobConf.set("key.value.separator.in.input.line", ",");//set(“key.value.separator.in.input.line”, “,”);
//		jobConf.setm
		return 0;
	}

}
