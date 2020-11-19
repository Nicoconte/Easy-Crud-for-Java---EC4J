package sorm4j.orm.builder;

import java.util.*;

public class QueryBuilder {
	
	private StringBuilder builder;
	
	public QueryBuilder() {
		
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
	public String select(List<String> fields, String table) {
		this.builder = new StringBuilder();
		
		int size = fields.size();
		
		this.builder.append(QUERY_BASE.SELECT + " ");
		
		for(String field : fields) {
			
			if (size == 1) 
				this.builder.append(field);
			else
				this.builder.append(field + ",");
			
			size--;
		}
	
		this.builder.append(" FROM " + table);
		
		return this.builder.toString();
	}
	
	/*
	 * @Simple select with the condition where
	 * @param List<String> fields name from database, String table name from database, the parameter o condition to check
	 * @return String select where query
	 * */
	public String select(List<String> fields, String table, String condition) {
		this.builder = new StringBuilder();
		
		int size = fields.size();
		
		this.builder.append(QUERY_BASE.SELECT + " ");
		
		for(String field : fields) {
			
			if (size == 1) 
				this.builder.append(field);
			else
				this.builder.append(field + ",");
			
			size--;
		}
	
		this.builder.append(" FROM " + table + " " + condition);
		
		return this.builder.toString();		
	}	
	
	
	/*
	 * @ Select Query with INNER JOIN. 
	 * @param: List of string with the fields to get, String table and the other table we want to join
	 * String of the attribute or field we want to join (Both attr) and finally we have the condition that define the connection
	 * @return String 
	 * */
	public String select(List<String> fields, String table, String anotherTable, String attribute, String anotherAttribute, String condition) {
		
		this.builder = new StringBuilder();
		
		this.builder.append(QUERY_BASE.SELECT + " ");
		
		int size = fields.size();
		
		for (String field : fields) {
			if (size == 1) 
				this.builder.append(field);
			else
				this.builder.append(field + ",");	
			size--;
		}
	
		this.builder.append(" FROM "+table + " " + "INNER JOIN " + anotherTable + " ON " + table+"."+attribute + "=" + anotherTable+"."+anotherAttribute + " " +condition);
		
		return this.builder.toString();
	}

		
	/*
	 * @Simple prepared statement insert query.
	 * @param int fields size, String database table
	 * @return String insert query
	 * */	
	public String insert(List<String> fields, String table) {
		this.builder = new StringBuilder();
		this.builder.append(QUERY_BASE.INSERT + " INTO " + table + " VALUES (");
		
		int fieldSize = fields.size();
		
		for(int i = 0; i < fieldSize; i++) {
			if (i == fieldSize - 1)
				this.builder.append("?)");
			else
				this.builder.append("?,");
		}
		
		return this.builder.toString();
	}
	
	/*
	 * @Simple prepared statement update Query
	 * @param List<String> fields of the database, String database table
	 * @return String update query
	 * */
	public String update(List<String> fields, String table, String parameter) {
		this.builder = new StringBuilder();
		this.builder.append(QUERY_BASE.UPDATE + " " + table + " SET ");
		
		int size = fields.size();
		
		for(String field : fields) {
			if (size == 1)
				this.builder.append(field + "=?");
			else
				this.builder.append(field + "=?,");
			
			size--;
		}
		
		this.builder.append(" WHERE " + parameter + "=?");
		
		return this.builder.toString();
	}
	
	
	/*
	 * @Delete a record from database based in a condition
	 * @param String table name from database
	 * @return String delete query
	 * */
	public String delete(String table) {
		return (this.builder = new StringBuilder(QUERY_BASE.DELETE + " FROM " + table + " WHERE id=?")).toString();
	}
	
	
	/*
	 * @Return a builder
	 * */
	public QueryBuilder build() {
		return new QueryBuilder();
	}
	
}
