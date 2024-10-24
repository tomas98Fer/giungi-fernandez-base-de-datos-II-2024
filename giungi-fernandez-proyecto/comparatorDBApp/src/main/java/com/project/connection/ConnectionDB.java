package com.project.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private Connection connection = null;
	private String driver;
	private String url;
	private String engine;
	private String username;
	private String password;
	private String schema;
	private String dbname;
	private String host;
	
	/**
	 * 
	 * @param engineName
	 * @param host
	 * @param dbname
	 * @param username
	 * @param password
	 * @param schema
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ConnectionDB(String engineName, String host, String dbname, String username, String password, String schema) 
			throws ClassNotFoundException, SQLException {
		if(anyParameterIsorrect( engineName, host, dbname, username, password))
			throw new IllegalArgumentException("Parmeter are Incorrect");
		if(!isCorrectEngineName(engineName))
			throw new IllegalArgumentException("Engine name is incorrect.");
		
		this.engine = engineName;
		this.host = host;
		this.dbname = dbname;
		this.username = username;
		this.password = password;	
		this.schema = schema;
		this.driver = specificDriver(this.engine);
		this.url      = buildURL();
		Class.forName(driver);
		connection = DriverManager.getConnection(url, username, password);
	}

	
	
	

	
	
	private String specificDriver(String engine) {
		switch(engine) {
			case "postgres" :
				return "org.postgresql.Driver";
			case "mysql" :
				return "com.mysql.cj.jdbc.Driver";
			case "oracle11xe" :
				return "oracle.jdbc.OracleDriver";
		}
		return "";
		
	}






//consultar por las url de oracle
	private String buildURL() {
		switch(engine) {
		case "postgres" :
			return "jdbc:postgresql://" + this.host +"/" + this.dbname;
		case "mysql" :
			return "jdbc:mysql://" + this.host +"/" + this.dbname;
		case "oracle11xe" :
			return "";
	}
	return "";

	}







	public Connection getConnection() {
		return connection;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}


	public String getSchema() {
		return this.schema;
	}
	
	public String getEngine() {
		return this.engine;
	}
	
	private boolean isCorrectEngineName(String engineName) {
		return engineName.equals("postgres") || engineName.equals("mysql") || engineName.equals("oracle11xe");
	}
	
	private boolean anyParameterIsorrect(String engineName, String host, String dbname, String username, String password) {
		if(engineName == null || host == null || dbname == null || username == null || password == null)
			return true;
		if(engineName.isEmpty() || host.isEmpty() || dbname.isEmpty() || username.isEmpty()) 
			return true;
		return false;
		
	}







	public String getDatabaseName() {
		return this.dbname;
	}








}
