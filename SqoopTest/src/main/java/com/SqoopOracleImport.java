package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MConnection;
import org.apache.sqoop.model.MConnectionForms;
import org.apache.sqoop.validation.Status;

public class SqoopOracleImport {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.8.54.39:1521:XE", "BENDEX", "Temp1234");
			System.out.println("Connection.." + conn.isValid(10));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void mainn(String[] args) {

		String url = "http://localhost:12000/sqoop/";
		SqoopClient client = new SqoopClient(url);
		
		// Dummy connection object
		MConnection newCon = client.newConnection(1);

		// Get connection and framework forms. Set name for connection
		MConnectionForms conForms = newCon.getConnectorPart();
		MConnectionForms frameworkForms = newCon.getFrameworkPart();
		newCon.setName("MyConnection");

		// Set connection forms values
		conForms.getStringInput("connection.connectionString").setValue(
				"jdbc:oracle:thin:@10.8.54.39:1521:XE");
		conForms.getStringInput("connection.jdbcDriver").setValue(
				"oracle.jdbc.driver.OracleDriver");
		conForms.getStringInput("connection.username").setValue("BENDEX");
		conForms.getStringInput("connection.password").setValue("Temp1234");

		frameworkForms.getIntegerInput("security.maxConnections").setValue(0);

		Status status = client.createConnection(newCon);
		if (status.canProceed()) {
			System.out.println("Created. New Connection ID : "
					+ newCon.getPersistenceId());
		} else {
			System.out.println("Check for status and forms error ");
		}
		
		/*
		 * 
		 * // Creating dummy job object MJob newjob =
		 * client.newJob(newCon.getPersistenceId(),
		 * org.apache.sqoop.model.MJob.Type.IMPORT); MJobForms connectorForm =
		 * newjob.getConnectorPart(); MJobForms frameworkForm =
		 * newjob.getFrameworkPart();
		 * 
		 * newjob.setName("ImportJob"); // Database configuration
		 * connectorForm.getStringInput
		 * ("table.schemaName").setValue(schemaName); // Input either table name
		 * or sql
		 * connectorForm.getStringInput("table.tableName").setValue(tableName);
		 * // connectorForm.getStringInput("table.sql").setValue(
		 * "select id,name from table where ${CONDITIONS}");
		 * 
		 * connectorForm.getStringInput("table.columns").setValue(columns);
		 * connectorForm.getStringInput("table.partitionColumn").setValue(
		 * partitionColumn);
		 * 
		 * // Set boundary value only if required //
		 * connectorForm.getStringInput("table.boundaryQuery").setValue("");
		 * 
		 * // Output configurations
		 * frameworkForm.getEnumInput("output.storageType").setValue("HDFS");
		 * frameworkForm
		 * .getEnumInput("output.outputFormat").setValue("TEXT_FILE");// Other
		 * // option: // SEQUENCE_FILE // / // TEXT_FILE
		 * frameworkForm.getStringInput("output.outputDirectory").setValue(
		 * outputDirectory); // Job resources
		 * frameworkForm.getIntegerInput("throttling.extractors").setValue(1);
		 * frameworkForm.getIntegerInput("throttling.loaders").setValue(1);
		 * 
		 * status = client.createJob(newjob); if (status.canProceed()) {
		 * System.out.println("New Job ID: " + newjob.getPersistenceId()); }
		 * else { System.out.println("Check for status and forms error "); } //
		 * Now Submit the Job MSubmission submission =
		 * client.startSubmission(newjob .getPersistenceId());
		 * System.out.println("Status : " + submission.getStatus());
		 */
	}

}
