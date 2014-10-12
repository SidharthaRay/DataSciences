package com.deloitte.storm.topology;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import com.deloitte.util.ApplicationConstants;

public class DataSource {

	private static DataSource datasource;
	private BasicDataSource dataSource;

	private DataSource(Map<String, String> dbConfig) throws IOException, SQLException,
			PropertyVetoException {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(dbConfig.get(ApplicationConstants.DB_DRIVER_CLASS));
		dataSource.setUsername(dbConfig.get(ApplicationConstants.DB_USER_NAME));
		dataSource.setPassword(dbConfig.get(ApplicationConstants.DB_PASSWORD));
		dataSource.setUrl(dbConfig.get(ApplicationConstants.DB_CONNECTION_URL));

		dataSource.setInitialSize(10);
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(20);
		dataSource.setMaxOpenPreparedStatements(180);

	}

	public static synchronized DataSource getInstance(Map<String, String> dbConfig)
			throws IOException, SQLException, PropertyVetoException {
		if (datasource == null) {
			datasource = new DataSource(dbConfig);
		}
		return datasource;
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

}