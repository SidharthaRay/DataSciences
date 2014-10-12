package com.deloitte.kafka.util;

public interface ApplicationConstants {
	//Generic constants
	public static final String APP_RECOURCE = "/home/cloudera/workspace/KafkaProducer/src/main/resources/ApplicationResources.properties";
	
	//Constants for Producer 
	public static final String INPUT_FILE_NAME = "producer.file.name";	
	public static final String BROKER_LIST = "producer.broker.list";	
	public static final String SERIALIZER_TYPE = "producer.serializer.type";
	public static final String NUM_OF_ACK = "producer.ack.request";
	public static final String TOPIC_TO_WRITE = "producer.topic.name";
	public static final String METADATA_BRKR_LIST = "metadata.broker.list";
	public static final String SERLZR_CLASS = "serializer.class";
	public static final String REQ_RQRD_ACKS = "request.required.acks";
}
