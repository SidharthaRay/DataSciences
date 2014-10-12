package com.deloitte.storm.hbase;



import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

/*
 * Class that establishes a connection with hbase and returns an HBaseConfiguration object                 
 */
public class HBaseConnector {
    private HConnection hcon;
    public final HConnection getHBaseConf(final String pathToHBaseXMLFile) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path(pathToHBaseXMLFile));
        hcon = HConnectionManager.createConnection(conf);
        return hcon;
    }
}
