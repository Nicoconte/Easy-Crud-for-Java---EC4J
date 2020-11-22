package ec4j.builder;

import java.util.*;

public class QueryBuilder {
	
	private StringBuilder builder;
	
	//The beginning of the query will never changes, so we use an enum to avoid changes
	private enum QUERY_BASE {
		SELECT,
		INSERT,
		UPDATE,
		DELETE,
	}

	
	public QueryBuilder() {
		
	}
	
	
	/**
	 * @Task Build select Query
	 * @param List<String>, String table from database
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
	
	/**
	 * @Task Build select query with conditions
	 * @param List<String> fields from database, String table from database, String conditions
	 * @return String select query with conditions
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
	
	
	/**
	 * @Task Build select query with inner join on and conditions 
	 * @param: List of string with the fields we want to get, String table´s relation, String conditions
	 * @return String select query with inner join statement
	 * */
	public String select(List<String> fields, String table, String anotherTable, String relations, String condition) {
		
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
	
		this.builder.append(" FROM "+table + " " + "INNER JOIN " + anotherTable + " ON " + relations + " " +condition);
		
		return this.builder.toString();
	}

		
	/**
	 * @Task Build insert query
	 * @param List of string with the fields we want to get, String table from database
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
	
	/**
	 * @Task Build update Query
	 * @param List of string with the fields we want to update, String table from database
	 * @return String update query
	 * */
	public String update(List<String> fields, String table, String condition) {
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
		
		this.builder.append(" WHERE " + condition + "=?");
		
		return this.builder.toString();
	}
	
	
	/**
	 * @Task Build delete query
	 * @param String table from database
	 * @return String delete query
	 * */
	public String delete(String table) {
		return (this.builder = new StringBuilder(QUERY_BASE.DELETE + " FROM " + table + " WHERE id=?")).toString();
	}
	
}
