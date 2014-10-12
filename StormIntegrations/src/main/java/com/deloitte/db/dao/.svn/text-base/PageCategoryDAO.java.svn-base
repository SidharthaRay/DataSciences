package com.deloitte.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.deloitte.storm.topology.DataSource;
import com.deloitte.util.ApplicationConstants;

public class PageCategoryDAO {
	private Connection connection;
	private Statement statement;

	public PageCategoryDAO(Map<String, String> dbConfig) {
		try {
			connection = DataSource.getInstance(dbConfig).getConnection();
			statement = connection.createStatement();
		} catch (Exception e) {
			throw new RuntimeException(
					"JDBC statement creation failed at PageCategoryDAO.PageCategoryDAO()", e);
		}
	}

	public Map<String, String> getAllCategories(String tableName) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			ResultSet resultSet = 
					statement.executeQuery("SELECT PAGE_URL, CATEGORY FROM " + tableName);
			while (resultSet.next()) {
				resultMap.put(
						resultSet.getString(ApplicationConstants.PAGE_URL), 
						resultSet.getString(ApplicationConstants.CATEGORY));
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"JDBC statement execution failed at PageCategoryDAO.getAllCategories()", e);
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				throw new RuntimeException(
						"JDBC statement/connection closing failed at PageCategoryDAO.getAllCategories()", e);
			}
		}
		return resultMap;
	}
}
