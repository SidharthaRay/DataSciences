package com;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TextInputFormatDemo {
	public static class TextInputFormatMapper extends
			Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}

	public static class TextInputFormatReducer extends
			Reducer<Text, Text, Text, Text> {
		private Text result = new Text();

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String comps = "";
			for (Text val : values) {
				comps += "|" + val.toString();
			}
			comps = comps.substring(1, comps.length());
			result.set(comps);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "TextInputFormatDemoJob");
		job.setJarByClass(TextInputFormatDemo.class);
		job.setMapperClass(TextInputFormatMapper.class);
		job.setReducerClass(TextInputFormatReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		FileInputFormat.addInputPath(job, new Path(
				"resources/input"));
		FileOutputFormat.setOutputPath(job, new Path("resources/output"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}