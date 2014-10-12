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


public class PatentCitationCount extends Configured implements Tool {

	public static class PatentCitationMapper extends MapReduceBase implements Mapper<Text, Text, Text, Text> {

		@Override
		public void map(Text key, Text value, OutputCollector<Text, Text> collector,
				Reporter reporter) throws IOException {
			collector.collect(value, key);
		}
		
	}
	
	public static class PaternCitationReducer extends MapReduceBase implements
			Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int count = 0;
			while (values.hasNext()) {
				values.next();
				count++;
			}
			output.collect(key, new IntWritable(count));
		}
	}
	
	@Override
	public int run(String[] confArg) throws Exception {
		
		JobConf jobConf = new JobConf(getConf(), PatentCitationCount.class);
		
		FileInputFormat.addInputPath(jobConf, new Path(confArg[0]));
		FileOutputFormat.setOutputPath(jobConf, new Path(confArg[1]));
		
		jobConf.setMapperClass(PatentCitationMapper.class);
		jobConf.setReducerClass(PaternCitationReducer.class);
		
		jobConf.setInputFormat(KeyValueTextInputFormat.class);
		jobConf.setOutputFormat(TextOutputFormat.class);
		jobConf.setMapOutputKeyClass(Text.class);
		jobConf.setMapOutputValueClass(Text.class);
		jobConf.setOutputKeyClass(Text.class);
		jobConf.setOutputValueClass(IntWritable.class);
		jobConf.set("key.value.separator.in.input.line", ",");
		
		JobClient.runJob(jobConf);
		return 0;
	}
	
	public static void main(String[] args) {
		String[] paths = {"/tmp/SampleData4Hadoop/input/cite75_99.txt", "/tmp/SampleData4Hadoop/output"};
		try {
			int result = ToolRunner.run(new Configuration(), new PatentCitationCount(), paths);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
