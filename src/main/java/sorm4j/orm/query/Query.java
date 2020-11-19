package sorm4j.orm.query;


import static sorm4j.orm.utils.Utils.*;

import java.util.*;
import sorm4j.orm.builder.QueryBuilder;

import java.sql.*;



public class Query {
	
	private QueryBuilder queryBuilder;
	private List<Object> singleResponse;
	private List< HashMap<String, Object> > multipleResponse;
	private int index;
	private boolean success;
	
	private Connection connector;
	private ResultSet rs;

	
	public Query() {
		
	}
	
	public Query(Connection connection) {
	
		this.queryBuilder = new QueryBuilder();
		this.singleResponse = new ArrayList<Object>();
		this.multipleResponse = new ArrayList< HashMap<String, Object> >();
		this.index = 1;
		this.success = false;
		this.connector = connection;
	}
	
	//TODO: Optimizar las queries select. Atomizar clase 
	/*
	 * @Get a single record from database
	 * @param: List<String> fields name from database, String table name, String condition, String value for the condition
	 * @return: HashMap<String, String> with the fields as key and database values as value
	 * */
	public HashMap<String, Object> get(List<String> fields, String table, String condition, List<Object> values) throws SQLException {
		
		if (!validateCondition(condition)) return null;
		
		try {
						
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fields, table, condition));

			for(int i = 1; i <= values.size(); i++) {
				sql.setObject(i, values.get(i - 1));
			}

			rs = sql.executeQuery();

			if(rs.next()) {
				while (index <= getColumnSize(rs)) {
					singleResponse.add(rs.getObject(index));
					index++;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if (this.connector != null && !this.connector.isClosed())
				this.connector.close();
		}
		
		return generateSingleHashMap(fields, singleResponse);
	}

	/*
	 * @Select with Inner join statement. The value from a table related to other. Example. Get blogs from a specific user
	 * 
	 * */
	public List< HashMap<String, Object> > get(List<String> fieldsToGet, String table, String tableToJoinWith,
			String attribute, String attributeToJoinWith, String condition, List<Object> conditionValues) throws SQLException{

		if (!validateCondition(condition)) return null;
		
		List<Object> aux = new ArrayList<Object>();
		
		try {
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fieldsToGet, table, tableToJoinWith, attribute, attributeToJoinWith, condition));
			
			for (int i = 1; i <= conditionValues.size(); i ++) {
				sql.setObject(i, conditionValues.get(i - 1));
			}
			
			this.rs = sql.executeQuery();			

			while (rs.next()) {
				aux.clear();

				while(index <= getColumnSize(rs)) {
					aux.add(rs.getObject(index));
					index++;
				}
				
				this.multipleResponse.add(generateSingleHashMap(fieldsToGet, aux));
				index = 1;
			}				
		
		} catch(Exception e) {
			e.printStackTrace();
		
		} finally {
			if (this.connector != null || !this.connector.isClosed() )
				this.connector.close();
		}
		
		return multipleResponse;
	}
	
	/*
	 * @Return all the records from the table given
	 * @param: List<String> fields name, String database table
	 * @return List< HashMap<String, String> > It´s similar to a json structure 
	 * */
	public List< HashMap<String, Object> > getAll(List<String> fields, String table) throws SQLException {
		
		List<Object> aux = new ArrayList<Object>();
		
		try {
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fields, table));
			this.rs = sql.executeQuery();
			
				while (rs.next()) {
					aux.clear();
				
					while(index <= getColumnSize(rs)) {
						aux.add(rs.getObject(index));
						index++;
					}
					
				this.multipleResponse.add(generateSingleHashMap(fields, aux));
				index = 1;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.connector != null && !this.connector.isClosed() )
				this.connector.close();
		}
		
		return multipleResponse;
	}

	/*
	 * @Verify if a record already exist in the database
	 * @param: List<String> fields name from database, String table name, String condition, String value for the condition
	 * @return: HashMap<String, String> with the fields as key and database values as value
	 * */
	public boolean exist(String table, String condition, List<Object> values) throws SQLException {
		
		if (!validateCondition(condition)) return false;
		
		try {
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(Arrays.asList("*"), table, condition));
		
			for(int i = 1; i <= values.size(); i++) {
				sql.setObject(i, values.get(i - 1));
			}
			
			rs = sql.executeQuery();

			if(rs.next()) {
				this.success = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if (this.connector != null && !this.connector.isClosed())
				this.connector.close();
		}
		
		return this.success;
	}
	
	// ------------------------------- END SELECT QUERIES ------------------------------------------------------
	
	/*
	 * @Save a record into database
	 * @param List<Strings> fields name, String table name, List<Object> values to save
	 * @return boolean
	 * */
	public boolean save(List<String> fields, String table, List<Object> values) throws SQLException{
		
		try {
			this.index = 0;
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.insert(fields, table));
	
			for (int i = 1; i <= fields.size(); i++) {
				sql.setObject(i, values.get(this.index));
				this.index++;
			}			

			if ( sql.executeUpdate() >= 1)
				success = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
		
			if (this.connector != null && !this.connector.isClosed())
				this.connector.close();
		}
		
		return success;
	}
	
	/*
	 * @Update a specific record from database
	 * @param List<String> fields name, String table name, List<Object> incoming values to update, String condition, String condition value
	 * @return boolean
	 * */
	public boolean update(String table, List<String> fields, List<Object> values, String parameter, Object parameterValue) throws SQLException {
		try {
			
			this.index = 0;
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.update(fields, table, parameter));
			
			for (int i = 1; i <= fields.size(); i++) {
				sql.setObject(i, values.get(this.index));
				this.index++;
			}			
			
			sql.setObject(fields.size() + 1, parameterValue); //Aca va el valor del WHERE 
			
			if ( sql.executeUpdate() >= 1)
				success = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
		
			if (this.connector != null && !this.connector.isClosed())
				this.connector.close();
		}
		
		return success;
	}
	
	
	/*
	 * @Delete a specific record from database. It´ll only delete by ID
	 * @param String table name, Object value
	 * @return boolean
	 * */
	public boolean delete(String table, Object parameterValue) throws SQLException{
		try {
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.delete(table));
			
			if (parameterValue != null) {
				sql.setObject(1, parameterValue);
				
				if (sql.executeUpdate() >= 1) 
					success = true;
			}
			
		} catch(Exception e) {
			
		} finally {
			if (this.connector != null && !this.connector.isClosed())
				this.connector.close();
		}
		
		return success;
	}
	
}
