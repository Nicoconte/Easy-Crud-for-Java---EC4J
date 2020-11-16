package orm;

import java.util.*;

public class Queries {
	
	private StringBuilder query;
	
	public Queries() {
		
	}
	
	private enum QUERY_BASE {
		SELECT,
		INSERT,
		UPDATE,
		DELETE,
	}

	/*
	 * @Single and Basic select Query. 
	 * @param List<String>, String databaseTable
	 * @return String select query
	 * */	
	public String selectQuery(List<String> fields, String table) {
		this.query = new StringBuilder();
		
		int size = fields.size();
		
		this.query.append(QUERY_BASE.SELECT + " ");
		
		for(String field : fields) {
			
			if (size == 1) 
				this.query.append(field);
			else
				this.query.append(field + ",");
			
			size--;
		}
	
		this.query.append(" FROM " + table);
		
		return this.query.toString();
	}
	
	
	/*
	 * @Simple prepared statement insert query.
	 * @param int fields size, String database table
	 * @return String insert query
	 * */	
	public String insertQuery(List<String> fields, String table) {
		this.query = new StringBuilder();
		this.query.append(QUERY_BASE.INSERT + " INTO " + table + " VALUES (");
		
		int fieldSize = fields.size();
	
		for(int i = 0; i < fieldSize; i++) {
			if (i == fieldSize - 1)
				this.query.append("?)");
			else
				this.query.append("?,");
		}
		
		return this.query.toString();
	}
	
	/*
	 * @Simple prepared statement update Query
	 * @param List<String> fields of the database, String database table
	 * @return String update query
	 * */
	public String updateQuery(List<String> fields, String table, String parameter) {
		this.query = new StringBuilder();
		this.query.append(QUERY_BASE.UPDATE + " " + table + " SET ");
		
		int size = fields.size();
		
		for(String field : fields) {
			if (size == 1)
				this.query.append(field + "=?");
			else
				this.query.append(field + "=?,");
			
			size--;
		}
		
		this.query.append(" WHERE " + parameter + "=?");
		
		return this.query.toString();
	}
	
	
	/*
	 * @Simple select with the condition where
	 * @param List<String> fields name from database, String table name from database, the parameter o condition to check
	 * @return String select where query
	 * */
	public String selectQueryWhere(List<String> fields, String table, String parameter) {
		this.query = new StringBuilder();
		
		int size = fields.size();
		
		this.query.append(QUERY_BASE.SELECT + " ");
		
		for(String field : fields) {
			
			if (size == 1) 
				this.query.append(field);
			else
				this.query.append(field + ",");
			
			size--;
		}
	
		this.query.append(" FROM " + table + " WHERE " + parameter + "=? LIMIT 1");
		
		return this.query.toString();		
	}
	
	
	/*
	 * @Delete a record from database based in a condition
	 * @param String table name from database
	 * @return String delete query
	 * */
	public String deleteQueryWhere(String table) {
		return (this.query = new StringBuilder(QUERY_BASE.DELETE + " FROM " + table + " WHERE id=?")).toString();
	}
	
	
}
