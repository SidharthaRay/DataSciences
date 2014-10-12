package com.recomender.extractorinterpreter;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;

import com.recommender.extractorinterpreter.utils.*;

public class ExtractorInterpreterDriver extends Configured implements Tool {
	private static PropertiesConfiguration properties = null;
//	private static FileSystem fs = null;

	
	
    public static void main(String[] args){
    	try{
    	
    	int res = ToolRunner.run(new Configuration(), new ExtractorInterpreterDriver(), args);
    	System.exit(res);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public int run(String[] args) throws Exception {
    	
    	FileSystem fs = null;
   	 
        // When implementing tool
        Configuration conf = this.getConf();
 
        // Create job
        Job job = new Job(conf, ExtractorInterpreterConstants.JOB_NAME_EXTRACTOR);
        job.setJarByClass(ExtractorInterpreterDriver.class);
 
        // Setup MapReduce job
        job.setMapperClass(ExtractorInterpreterMapper.class);
        job.setReducerClass(ExtractorInterpreterReducer.class);
 

 
        //Setting property File
        fs = FileSystem.get(conf);
		//String propertiesPath = args[0];
        String propertiesPath = conf.get("propertiespath");
		Path propertiesFilePath = new Path(propertiesPath);
		FSDataInputStream inFile = fs.open(propertiesFilePath);
		properties = new PropertiesConfiguration();
		properties.load(inFile);
		inFile.close();
		//conf.set("propertiesFilePath", propertiesPath);
		//conf.addResource(propertiesFilePath);

		RemoteIterator<LocatedFileStatus> childFiles = fs.listFiles(new Path(
				properties.getString(ExtractorInterpreterConstants.INPUT_DIRECTORY_EXTRACTOR)),
				false);
		LocatedFileStatus childFile = null;
		String paths = ExtractorInterpreterConstants.EMPTY_STRING;
		String absoluteFilePath = ExtractorInterpreterConstants.EMPTY_STRING;
		while (childFiles.hasNext()) {
			childFile = childFiles.next();
			if(!childFile.getPath().getName().endsWith("tmp"))
			{
			absoluteFilePath =  properties
					.getProperty(ExtractorInterpreterConstants.INPUT_DIRECTORY_EXTRACTOR)
			+ childFile.getPath().getName();
			System.out.println(absoluteFilePath);
			if (paths.length() > 0) {
				paths += ExtractorInterpreterConstants.COMMA_STRING
						+ absoluteFilePath;
			} else {
				paths = absoluteFilePath;
			}
			}
		
		}
		// Input
		FileInputFormat.setInputPaths(job, paths);
		job.setInputFormatClass(TextInputFormat.class);
		
		
		// Output
		fs.delete(
				new Path(properties
						.getString(ExtractorInterpreterConstants.OUTPUT_DIRECTORY_EXTRACTOR)), true);
	
		
		FileOutputFormat.setOutputPath(
				job,
				new Path(properties
						.getString(ExtractorInterpreterConstants.OUTPUT_DIRECTORY_EXTRACTOR)));
		
        // Specify key / value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		MultipleOutputs. addNamedOutput(job, "ReducerOutput", TextOutputFormat.class, Text.class, Text.class);

 
        // Execute job and return status
        return job.waitForCompletion(true) ? 0 : 1;
    }
	 

    }

