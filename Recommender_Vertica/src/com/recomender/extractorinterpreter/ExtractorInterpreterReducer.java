package com.recomender.extractorinterpreter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.Reducer;

import com.recommender.extractorinterpreter.utils.ExtractorInterpreterConstants;

public class ExtractorInterpreterReducer extends Reducer<Text, Text, Text, Text> {
	private String reduceTaskId;
	private int noKeys = 0;
	private MultipleOutputs mos;
	private static PropertiesConfiguration mappingProperties = null;
	private static int DATE_FIELD;// =
	// Integer.parseInt(mappingProperties.getProperty(InterpreterConstants.FIELDS_DATETIME));
	private static int TIME_FIELD;
	private static int IPADDRESS_FIELD; // =
	// Integer.parseInt(mappingProperties.getProperty(InterpreterConstants.FIELDS_IPADDRESS));
	private static int USERNAME_FIELD; // =
	// Integer.parseInt(mappingProperties.getProperty(InterpreterConstants.FIELDS_USERNAME));
	private static int URL_FIELD; // =
	// Integer.parseInt(mappingProperties.getProperty(InterpreterConstants.FIELDS_URL));
	private static int BROWSERINFO_FIELD;// =
	private static int REFERRER_FIELD;
	// Integer.parseInt(mappingProperties.getProperty(InterpreterConstants.FIELDS_BROWSERINFO));
	private static FileSystem fs = null;
	String outputTextDelimiter;
	String inputTextDelimiter;
	java.util.Date date= new java.util.Date();
	String baseOutputPath = "/batch/weblogs/ReducerOutput/"+new Timestamp(date.getTime());
	 public void setup(Context context) {
		 mos = new MultipleOutputs(context);
			String propertiesFilePath = null;
			try {
				Configuration conf = context.getConfiguration();
				fs = FileSystem.get(conf);
				propertiesFilePath = conf.get("propertiespath");
				//propertiesFilePath = conf.get("propertiesFilePath");
				System.err.println(propertiesFilePath);
				Path propertiesPath = new Path(propertiesFilePath);
				FSDataInputStream inFile = fs.open(propertiesPath);
				mappingProperties = new PropertiesConfiguration();
				mappingProperties.load(inFile);
				inFile.close();
				DATE_FIELD = mappingProperties
						.getInt(ExtractorInterpreterConstants.FIELDS_DATE);
				IPADDRESS_FIELD = mappingProperties
						.getInt(ExtractorInterpreterConstants.FIELDS_IPADDRESS_INTERPRETER);
				TIME_FIELD = mappingProperties
						.getInt(ExtractorInterpreterConstants.FIELDS_TIME);
				URL_FIELD = mappingProperties
						.getInt(ExtractorInterpreterConstants.FIELDS_URL_INTERPRETER);
				REFERRER_FIELD = mappingProperties
						.getInt(ExtractorInterpreterConstants.FIELDS_REFERRER);
				outputTextDelimiter = mappingProperties
						.getString(ExtractorInterpreterConstants.OUTPUT_TEXT_FILE_DELIMITTER_INTERPRETER);
				inputTextDelimiter = mappingProperties
						.getString(ExtractorInterpreterConstants.INPUT_TEXT_FILE_DELIMITTER_INTERPRETER);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 
		public void reduce(Text txtIPAddressNPath, Iterator<Text> values,
				Context context) throws IOException {
			
		
			try{
			// Process
			int noValues = 0;
			int noOfVisits = 1;
			long timeOnPageSec = 0;
			int noOfPagesPerSession = 0;
			long totalTimeOnSite = 0;
			String currDate = "";
			String currTime = "";
			String currURL = "";
			String currIPAddress = "";
			String currReferrer = "";
			String outputValue = "";
			String outputKey = "";
			Text currentValue = new Text();
			String[] currFields;
			Vector<String> recordVector = new Vector<String>();

		
			while (values.hasNext()) {

				currentValue = values.next();
				recordVector.add(currentValue.toString());
				
//				mos.getCollector("interpretermapper", reporter).collect(
//						txtIPAddressNPath, currentValue);
			}
			Collections.sort(recordVector);
			 
			for (int count = 0; count < recordVector.size(); count++) {

				String currData = (String) recordVector.get(count);
				currFields = currData.split(inputTextDelimiter);
				currDate = currFields[(DATE_FIELD - 1)];
				currTime = currFields[(TIME_FIELD - 1)];
				currURL = currFields[(URL_FIELD - 1)];
//				currReferrer = currFields[(REFERRER_FIELD - 1)];
				currIPAddress = currFields[(IPADDRESS_FIELD - 1)];
				outputKey = "";
				++noValues;
				++noOfPagesPerSession;
				
				if ((count + 1) < recordVector.size()) {
					String nextData = (String) recordVector.get(count + 1);
					String[] nxtFields = nextData.split(inputTextDelimiter);
					Date currParsedDate;
					Date nextParsedDate;
					long timeDiff;				
					    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					    currParsedDate = dateFormat.parse(currDate+" "+currTime);
					    nextParsedDate = dateFormat.parse(nxtFields[(DATE_FIELD - 1)]+" "+nxtFields[(TIME_FIELD - 1)]);
					    timeDiff = Math.abs(((nextParsedDate.getTime() - currParsedDate.getTime()) / 1000));
					    System.out.println("Current"+currIPAddress+" "+currParsedDate.getTime());
					    System.out.println("Next"+nxtFields[(IPADDRESS_FIELD - 1)]+" "+nextParsedDate.getTime());
					    if (timeDiff > 600 || timeDiff == 0) {
							if (noOfPagesPerSession > 1) {
								timeOnPageSec = timeOnPageSec + totalTimeOnSite
										/ noOfPagesPerSession;
							}
							else
							{
								if(timeDiff > 600){
									timeOnPageSec = timeOnPageSec + 600;
								}
							}
							totalTimeOnSite = 0;
							noOfPagesPerSession = 0;
						} else {
							timeOnPageSec = timeOnPageSec + timeDiff;
							totalTimeOnSite = totalTimeOnSite + timeDiff;
						}
						outputKey = outputKey + currIPAddress + inputTextDelimiter
								+ currURL;
						txtIPAddressNPath = new Text(outputKey);
//						outputValue = currIPAddress + outputTextDelimiter + currURL
//								+ outputTextDelimiter + currDate + outputTextDelimiter
//								+ currTime + outputTextDelimiter + currReferrer
//								+ outputTextDelimiter + noOfVisits
//								+ outputTextDelimiter + timeOnPageSec;
						outputValue = currIPAddress + outputTextDelimiter + currURL
								+ outputTextDelimiter + currDate + " "
								+ currTime + outputTextDelimiter + currReferrer
								+ outputTextDelimiter + noOfVisits
								+ outputTextDelimiter + timeOnPageSec;

//						mos.getCollector("interpretercombiner", reporter).collect(null,
//								outputValue);
						txtIPAddressNPath = new Text(currIPAddress
								+ outputTextDelimiter + currURL);
												
						mos.write("ReducerOutput", txtIPAddressNPath, new Text(outputValue), baseOutputPath);
						
						context.write(txtIPAddressNPath, new Text(outputValue));

						// Resetting Time & Visit
						timeOnPageSec = 0;
						noOfVisits = 1;
						
					}
					} 
			// IPAddress, Page, Date, TotalNoOfVisits, TotalTimeSpent(sec)
			if (noOfPagesPerSession > 1) {
				timeOnPageSec = timeOnPageSec + totalTimeOnSite
						/ noOfPagesPerSession;
			}
		
			outputKey = outputKey + currIPAddress + inputTextDelimiter + currURL;
			txtIPAddressNPath = new Text(outputKey);
			outputValue = currIPAddress + outputTextDelimiter + currURL
					+ outputTextDelimiter + currDate + " "
					+ currTime + outputTextDelimiter + currReferrer
					+ outputTextDelimiter + noOfVisits
					+ outputTextDelimiter + timeOnPageSec;
			txtIPAddressNPath = new Text(currIPAddress + outputTextDelimiter
					+ currURL);
			
			mos.write("ReducerOutput", txtIPAddressNPath, new Text(outputValue), baseOutputPath);
			
			context.write(txtIPAddressNPath, new Text(outputValue));

//			if ((noValues % 10) == 0) {
//				reporter.progress();
//			}

			// Increment the no. of <key, list of values> pairs processed
//			++noKeys;

			// Increment counters
			// reporter.incrCounter(NUM_RECORDS, 1);

			// Every 100 keys update application-level status
//			if ((noKeys % 100) == 0) {
//				reporter.setStatus(reduceTaskId + " processed " + noKeys);
//			}
			}catch(Exception e){
				e.printStackTrace();
		}

		}
		 public void cleanup(Context  context) throws IOException, InterruptedException {
			 mos.close();
			 }
	

}
			
