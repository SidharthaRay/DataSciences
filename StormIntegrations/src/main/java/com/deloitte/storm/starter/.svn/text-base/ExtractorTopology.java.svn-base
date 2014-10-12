package com.deloitte.storm.starter;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import storm.kafka.KafkaSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.deloitte.db.dao.PageCategoryDAO;
import com.deloitte.storm.bolt.DBRecPersisterBolt;
import com.deloitte.storm.bolt.HdfsBolt;
import com.deloitte.storm.bolt.LogExtractorBolt;
import com.deloitte.storm.bolt.format.DefaultFileNameFormat;
import com.deloitte.storm.bolt.format.DelimitedRecordFormat;
import com.deloitte.storm.bolt.format.FileNameFormat;
import com.deloitte.storm.bolt.format.RecordFormat;
import com.deloitte.storm.bolt.rotation.FileRotationPolicy;
import com.deloitte.storm.bolt.rotation.FileSizeRotationPolicy;
import com.deloitte.storm.bolt.rotation.FileSizeRotationPolicy.Units;
import com.deloitte.storm.bolt.sync.CountSyncPolicy;
import com.deloitte.storm.bolt.sync.SyncPolicy;
import com.deloitte.storm.topology.LogExtrTopolgyConfigurer;
import com.deloitte.util.ApplicationConstants;

/**
 * This topology demonstrates Storm's stream groupings and multilang
 * capabilities.
 */
public class ExtractorTopology {

	public static void main(String[] args) throws Exception {
		LogExtrTopolgyConfigurer topolgyConfigurer = new LogExtrTopolgyConfigurer();
		Properties appProperties = new Properties();
		String topologyName = "";
		if(args.length < 2) {
			appProperties.load(new FileInputStream(ApplicationConstants.APP_RECOURCE));
			topologyName = ApplicationConstants.TOPOLOGY_NAME;
//			System.out.println("Please provide TopologyName and Application.properties path");
//			System.exit(0);
		} else {	
			topologyName = args[0];
			appProperties.load(new FileInputStream(args[1]));
		}

		//Configuring and instantiating TopologyBuilder
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config conf = new Config();
		if(appProperties.getProperty(ApplicationConstants.APP_MODE)
				.equalsIgnoreCase(ApplicationConstants.DEBUG)) {
			conf.setDebug(true);
		}

		//Configuring KafkaSpout with a Kafka topic and instantiating it 
		topologyBuilder.setSpout(ApplicationConstants.KAFKA_SPOUT, new KafkaSpout(topolgyConfigurer.configureKafkaSpout(appProperties)));
		
		List<String> bolts = Arrays.asList(appProperties.getProperty(ApplicationConstants.TOPOLOGY_BOLTS).trim().split("\\s*,\\s*"));
		if(bolts.contains(ApplicationConstants.HDFS)) {		//Configuring HdfsBolt
			Fields hdfsFields = new Fields(ApplicationConstants.LOG);
			RecordFormat format = new DelimitedRecordFormat()
			        .withFieldDelimiter(appProperties.getProperty(ApplicationConstants.REC_FMT_DELIM));
//			        .withFields(hdfsFields);
			SyncPolicy syncPolicy = new CountSyncPolicy(
					Integer.parseInt(appProperties.getProperty(ApplicationConstants.SYNC_POL_COUNT)));
			FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(
					Float.parseFloat(appProperties.getProperty(ApplicationConstants.FILE_SIZE_ROT_POL)), 
					Units.MB);
			FileNameFormat fileNameFormat = new DefaultFileNameFormat()
			        .withPath(appProperties.getProperty(ApplicationConstants.FILE_NAME_FORMAT));
			HdfsBolt hdfsBolt = new HdfsBolt()
			        .withFsUrl(appProperties.getProperty(ApplicationConstants.HDFS_URL))
			        .withFileNameFormat(fileNameFormat)
			        .withRecordFormat(format)
			        .withRotationPolicy(rotationPolicy)
			        .withSyncPolicy(syncPolicy);
			topologyBuilder.setBolt(ApplicationConstants.HDFS_BOLT, hdfsBolt).shuffleGrouping(ApplicationConstants.KAFKA_SPOUT);
		}

		if(bolts.contains(ApplicationConstants.RDBMS)) {		//Configuring DBRecPersisterBolt
			//Making log format available to LogExtractorBolt
			LogExtractorBolt logExtractorBolt = new LogExtractorBolt();
			logExtractorBolt.setLogFormat(topolgyConfigurer.getLogFormat(appProperties));
			topologyBuilder.setBolt(ApplicationConstants.LOG_EXTRACTOR, logExtractorBolt).shuffleGrouping(ApplicationConstants.KAFKA_SPOUT);

			DBRecPersisterBolt dbPersistBolt = new DBRecPersisterBolt();
			Map<String, String> dbConfigMap = topolgyConfigurer.getDBConfig(appProperties);
			Map<String, String> categoryMap = new PageCategoryDAO(dbConfigMap).getAllCategories(appProperties.getProperty(ApplicationConstants.PAGE_CAT_TBL_NAME));
			dbPersistBolt.setDbConfig(dbConfigMap);
			dbPersistBolt.setCategoryMap(categoryMap);
			conf.put(ApplicationConstants.INTRP_15MIN_TBL_NAME, appProperties.get(ApplicationConstants.INTRP_15MIN_TBL_NAME));
			topologyBuilder.setBolt(ApplicationConstants.DB_REC_PERSISTER, dbPersistBolt).shuffleGrouping(ApplicationConstants.LOG_EXTRACTOR);
		}
		if(args != null && args.length > 0) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyName, conf,
					topologyBuilder.createTopology());

			// Thread.sleep(10000);
			// cluster.shutdown();
		} else if (args != null && args.length < 0) {
			conf.setNumWorkers(Integer.parseInt(appProperties.getProperty(ApplicationConstants.WORKER_COUNT)));
			StormSubmitter.submitTopology(topologyName, conf,
					topologyBuilder.createTopology());
		}
	}
}