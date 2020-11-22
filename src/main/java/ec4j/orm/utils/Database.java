package ec4j.orm.utils;

import java.sql.*;

public class Database {
	
	private String host;
	private String database;
	private String user;
	private String password;
	
	private String databaseType;
	
	private static Connection connector;

	public Database() {
		
	}
	
	/**
	 * @Constructor for a specific database
	 * */
	public Database(String host, String database, String user, String password, String type) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		this.databaseType = type;
	}
	
	
	/**
	 * @Constructor for MySQL database. It´s the database by default
	 * */
	public Database(String host, String database, String user, String password) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		this.databaseType = "";
	}
			
	public Connection databaseManager() {
		switch(this.databaseType.toLowerCase()) {
			case "mysql":
				this.makeMysqlConnection();
				break;
				
			case "sqlserver":
				this.makeSqlServerConnection();
				break;
			
			case "mariadb":
				this.makeMariaDbConnection();
				break;
				
			default:
				System.out.println("Unknown Database");
				return null;
		}
		
		return connector;
	}
	
	//TODO: Mix all the method in one 
	private Connection makeMysqlConnection() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connector = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database, this.user, this.password);
			
			if(connector != null)
				System.out.println("Success : MySQL");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connector;
	}
	
	
	private Connection makeSqlServerConnection() {
		try {
			
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			connector = DriverManager.getConnection("jdbc:sqlserver://" + this.host + "\\" + this.database + ";user=" + this.user + ";password=" + this.password);
			
			if (connector != null) 
				System.out.println("Success : SQL Server");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connector;
	}	
	
	private Connection makeMariaDbConnection() {
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			connector = DriverManager.getConnection("jdbc:mariadb://" + this.host + "/" + this.database, this.user, this.password);
			
			if (connector != null)
				System.out.println("Success : MariaDB");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connector;
	}
}
