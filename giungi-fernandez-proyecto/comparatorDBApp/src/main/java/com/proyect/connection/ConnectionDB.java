package com.proyect.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private Connection connection = null;
	private String driver;
	private String url;
	private String username;
	private String password;
	private String schema;
	
	public ConnectionDB(String driver, String url, String username, String password, String schema) 
			throws ClassNotFoundException, SQLException {
		this.url      = url;
		this.username = username;
		this.password = password;	
		this.schema = schema;
		Class.forName(driver);
		connection = DriverManager.getConnection(url, username, password);
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
	
	
	
}
