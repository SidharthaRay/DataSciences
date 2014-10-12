package com.recomender.extractorinterpreter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

//import com.recommender.extractorinterpreter.MyCounters;
import com.recommender.extractorinterpreter.utils.ExtractorInterpreterConstants;



public class ExtractorInterpreterMapper extends Mapper<LongWritable, Text, Text, Text> {
	static enum MyCounters {
		NUM_RECORDS
	}

	Text txtIPAddressNPath = new Text();
	private static PropertiesConfiguration mappingProperties = null;
	private static int TOTAL_FIELDS; 
	private static int DATETIME_FIELD;
	private static int IPADDRESS_FIELD; 	
	private String mapTaskId;
	private String inputFile;
	private int noRecords = 0;
	String outputTextDelimiter;
	private static FileSystem fs = null;
	String propertiesFilePath = null;
	
	public void setup(Context context) {
	
		
		String propertiesFilePath = null;
		try {
			Configuration conf = context.getConfiguration();
			fs = FileSystem.get(conf);
			propertiesFilePath = conf.get("propertiespath");
			//propertiesFilePath = conf.get("propertiesFilePath");
			Path propertiesPath = new Path(propertiesFilePath);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+propertiesFilePath+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			FSDataInputStream inFile = fs.open(propertiesPath);
			mappingProperties = new PropertiesConfiguration();
			mappingProperties.load(inFile);
			inFile.close();
			TOTAL_FIELDS = mappingProperties.getInt(ExtractorInterpreterConstants.TOTAL_FIELDS_EXTRACTOR);
			DATETIME_FIELD = mappingProperties.getInt(ExtractorInterpreterConstants.FIELDS_DATETIME);
			IPADDRESS_FIELD = mappingProperties.getInt(ExtractorInterpreterConstants.FIELDS_IPADDRESS_EXTRACTOR);
			outputTextDelimiter = mappingProperties.getString(ExtractorInterpreterConstants.OUTPUT_TEXT_FILE_DELIMITTER_EXTRACTOR);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {		

		String outputText = "";
		String strIPAddressNPath = "";
		
		String line = value.toString();
	

		int colCount = 1;

		for (String token : line.split(" ", TOTAL_FIELDS)) {

			token = token.replaceAll("\\[", "");
			token = token.replaceAll("\\]", "");
			token = token.replaceAll("\"", "");
			if (!token.equals("-")) {

				if (colCount == DATETIME_FIELD) {
					String[] arrDateTime = token.split(":", 2);
					
				
					SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
				 
					try {
				 
						Date date = formatter1.parse(arrDateTime[0]);
						SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
						String date_formated = formatter2.format(date);
						
						outputText = outputText + outputTextDelimiter
								+ date_formated + outputTextDelimiter
								+ arrDateTime[1];
						
						
				 
					} catch (ParseException e) {
						e.printStackTrace();
					}		
										
//					strIPAddressNPath = strIPAddressNPath + outputTextDelimiter
//							+ arrDateTime[0];
					
				} else if (colCount == IPADDRESS_FIELD) {
					strIPAddressNPath = strIPAddressNPath + token;
					outputText = outputText + token;
				} else {
					outputText = outputText + outputTextDelimiter + token;
				}
			} else {
				outputText = outputText + outputTextDelimiter;
			}
			colCount++;

		}
		txtIPAddressNPath.set(strIPAddressNPath);

		// Increment the no. of <key, value> pairs processed
//		++noRecords;

		// Increment counters
//		reporter.incrCounter(MyCounters.NUM_RECORDS, 1);

		// Every 100 records update application-level status
//		if ((noRecords % 100) == 0) {
//			reporter.setStatus(mapTaskId + " processed " + noRecords
//					+ " from input-file: " + inputFile);
//		}

		// Output the result
		// System.out.println(outputText);
		context.write(txtIPAddressNPath, new Text(outputText));

	
		
	}
	

}
