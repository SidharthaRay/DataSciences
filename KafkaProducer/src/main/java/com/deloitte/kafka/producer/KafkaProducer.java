package com.deloitte.kafka.producer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.deloitte.kafka.util.ApplicationConstants;

public class KafkaProducer {

	public static void main(String[] args) {

		Properties properties = new Properties();
		try {
			if(args.length < 1) {
//				properties.load(new FileInputStream(ApplicationConstants.APP_RECOURCE));
				System.err.println("Provide application propertities file");
			} else {
				properties.load(new FileInputStream(args[0]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Process p;
		Properties props = new Properties();
		String line = "";
		// Random rnd = new Random();
		String msg = "";
		String num = "1";


		String command = "tail -f "
				+ properties.getProperty(ApplicationConstants.INPUT_FILE_NAME);
		String brokerList = properties
				.getProperty(ApplicationConstants.BROKER_LIST);
		String Serializer_type = properties
				.getProperty(ApplicationConstants.SERIALIZER_TYPE);
		String noOfAcks = properties
				.getProperty(ApplicationConstants.NUM_OF_ACK);
		String Topic_To_Write = properties
				.getProperty(ApplicationConstants.TOPIC_TO_WRITE);
		props.put(ApplicationConstants.METADATA_BRKR_LIST, brokerList);
		props.put(ApplicationConstants.SERLZR_CLASS, Serializer_type);
		// props.put("partitioner.class", "example.producer.SimplePartitioner");
		props.put(ApplicationConstants.REQ_RQRD_ACKS, noOfAcks);

		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);

		try {
			p = Runtime.getRuntime().exec(command);
			// p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((line = reader.readLine()) != null) {
				msg = line + "\n";
				KeyedMessage<String, String> data = new KeyedMessage<String, String>(
						Topic_To_Write, num, msg);
				producer.send(data);
			}
			producer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
