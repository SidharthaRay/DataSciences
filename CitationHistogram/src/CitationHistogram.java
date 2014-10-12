import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class CitationHistogram extends Configured implements Tool {

	public static class HistogramMapper extends MapReduceBase implements Mapper<Text, Text, IntWritable, IntWritable> {
		
		private IntWritable one = new IntWritable(1);
		private IntWritable citationCount = new IntWritable();
		
		@Override
		public void map(Text key, Text value,
				OutputCollector<IntWritable, IntWritable> collector, Reporter reporter)
				throws IOException {
			citationCount.set(Integer.parseInt(value.toString()));
			collector.collect(citationCount, one);
		}
		
	}
	
	public static class HistogramReducer extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

		@Override
		public void reduce(IntWritable key, Iterator<IntWritable> values,
				OutputCollector<IntWritable, IntWritable> collector, Reporter reporter)
				throws IOException {
			int count = 0;
			while(values.hasNext()) {
				count += values.next().get();
			}
			collector.collect(key, new IntWritable(count));
		}
		
	}
	
	@Override
	public int run(String[] jobArg) throws Exception {
		
		JobConf job = new JobConf(getConf(), CitationHistogram.class);
		
		FileInputFormat.setInputPaths(job, new Path(jobArg[0]));
		FileOutputFormat.setOutputPath(job, new Path(jobArg[1]));
		
		job.setInputFormat(KeyValueTextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		job.set("key.value.separator.in.input.line", ",");
		
		job.setMapperClass(HistogramMapper.class);
		job.setReducerClass(HistogramReducer.class);
		
		JobClient.runJob(job);
		
		return 0;
	}
	
	public static void main(String[] args) {
		String[] paths = {"/tmp/SampleData4Hadoop/input/cite75_99.txt", "/tmp/SampleData4Hadoop/output"};
		try {
			ToolRunner.run(new Configuration(), new CitationHistogram(), paths);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
