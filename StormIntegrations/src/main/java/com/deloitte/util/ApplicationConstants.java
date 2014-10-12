package com.deloitte.util;

public interface ApplicationConstants {
	
	//Generic constants
	public static final String APP_RECOURCE = "/home/cloudera/workspace/StormIntegrations/src/main/resources/config/ApplicationResources.properties";
	
	//Constants for Producer 
	public static final String INPUT_FILE_NAME = "producer.file.name";	
	public static final String BROKER_LIST = "producer.broker.list";	
	public static final String SERIALIZER_TYPE = "producer.serializer.type";
	public static final String NUM_OF_ACK = "producer.ack.request";
	public static final String TOPIC_TO_WRITE = "producer.topic.name";
	public static final String METADATA_BRKR_LIST = "metadata.broker.list";
	public static final String SERLZR_CLASS = "serializer.class";
	public static final String REQ_RQRD_ACKS = "request.required.acks";
	
	//Constants for Consumer
	public static final String ZK_HOST = "zookeeper.host";
	public static final String ZK_TOPIC = "zookeeper.topic";
	public static final String ZK_ROOT = "zookeeper.root";
	public static final String ZK_ID = "zookeeper.id";
	
	//Constants for Topology
	public static final String KAFKA_SPOUT = "kafka_spout";
	public static final String LOG_EXTRACTOR = "Log.Extractor";
	public static final String DB_REC_PERSISTER = "DB.Rec.Persisiter";
	public static final String HDFS_BOLT = "hdfs_bolt";
	public static final String TOPOLOGY_NAME = "LogExtractorTopology";
	public static final String TOPOLOGY_BOLTS = "topology.bolt";
	
	//Constants for DB
	public static final String DB_DRIVER_CLASS = "db.driver.classname";
	public static final String DB_USER_NAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_CONNECTION_URL = "db.connection.url";
	public static final String DB_CONFIG_MAP = "dbConfigMap";
	
	//Constants for HDFS
	public static final String REC_FMT_DELIM = "hdfs.record.format.delimeter";
	public static final String SYNC_POL_COUNT = "hdfs.sync.policy.count";
	public static final String FILE_SIZE_ROT_POL = "hdfs.file.size.rotation.policy";
	public static final String FILE_NAME_FORMAT = "hdfs.file.name.format";
	public static final String HDFS_URL = "hdfs.url";
	public static final String HDFS = "HDFS";
	
	//Constants for Application Config
	public static final String APP_MODE = "app.mode";
	public static final String DEBUG = "DEBUG";
	public static final String CATEGORY_MAP = "CATEGORY_MAP";
	public static final String LOG_FORMAT = "logFormat";
	public static final String WORKER_COUNT = "worker.count";
	
	//Constants for different fields of log file
	public static final String LOG = "log";
	public static final String LOG_IPADDRESS_INDEX = "log.cont.gr.IPADDRESS";
	public static final String LOG_USERNAME_INDEX = "log.cont.gr.USERNAME";
	public static final String LOG_VISITDATE_INDEX = "log.cont.gr.VISITDATE";
	public static final String LOG_PAGEURL_INDEX = "log.cont.gr.PAGEURL";
	
	//Constants for INTERPRETER_15MIN table
	public static final String INTRP_15MIN_TBL_NAME = "db.table.interpreter";
	public static final String INTRP_15MIN_IPADDRESS = "IPADDRESS";
	public static final String INTRP_15MIN_USERNAME = "USERNAME"; 
	public static final String INTRP_15MIN_VISITDATE = "VISITDATE"; 
	public static final String INTRP_15MIN_PAGEURL = "PAGEURL"; 
	public static final String INTRP_15MIN_CATEGORY = "CATEGORY";
	public static final String RDBMS = "RDBMS";
	
	//Constants for PAGE_CATEGORY table
	public static final String PAGE_CAT_TBL_NAME = "db.table.page.category";
	public static final String PAGE_URL = "PAGE_URL";
	public static final String CATEGORY = "CATEGORY";

}
