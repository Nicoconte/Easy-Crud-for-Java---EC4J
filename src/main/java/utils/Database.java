package utils;

import java.sql.*;

public class Database {
	
	private static Connection connector;
	
	public static Connection getConnection(String host, String database, String user, String password) {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connector = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
			
			if(connector != null)
				System.out.println("Conectado");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connector;
	}
	
}
