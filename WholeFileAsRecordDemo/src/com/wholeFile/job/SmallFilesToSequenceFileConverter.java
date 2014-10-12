package com.wholeFile.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.wholeFile.inputFormat.WholeFileInputFormat;

public class SmallFilesToSequenceFileConverter extends Configured implements Tool {

	static class SequenceFileMapper extends MapReduceBase implements Mapper<NullWritable, BytesWritable, Text, BytesWritable> {

		private JobConf jobConf;
		
		@Override
		public void configure(JobConf job) {
			this.jobConf = job;
		}
		
		@Override
		public void map(NullWritable key, BytesWritable value,
				OutputCollector<Text, BytesWritable> outputCollector, Reporter  reporter)
				throws IOException {
			String fileName = jobConf.get("map.input.file");
			outputCollector.collect(new Text(fileName), value);
		}
		
	}
	
	@Override
	public int run(String[] arg0) throws Exception {
		JobConf jobConf = new JobConf(getConf(), getClass());
		
		jobConf.setMapperClass(SequenceFileMapper.class);

		FileInputFormat.addInputPath(jobConf, new Path("resources/smallfiles"));
		FileOutputFormat.setOutputPath(jobConf, new Path("resources/output"));
		
		jobConf.setInputFormat(WholeFileInputFormat.class);
//		jobConf.setOutputFormat(SequenceFileOutputFormat.class);
		
		jobConf.setMapOutputKeyClass(Text.class);
		jobConf.setMapOutputValueClass(BytesWritable.class);
		
		jobConf.setReducerClass(IdentityReducer.class);
		jobConf.setOutputKeyClass(Text.class);
		jobConf.setOutputValueClass(BytesWritable.class);
		
		JobClient.runJob(jobConf);
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new Configuration(), new SmallFilesToSequenceFileConverter(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
