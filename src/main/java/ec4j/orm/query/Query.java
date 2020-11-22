package ec4j.orm.query;


import java.util.*;

import ec4j.orm.builder.QueryBuilder;
import ec4j.orm.utils.Database;

import static ec4j.orm.utils.Utils.*;

import java.sql.*;


public class Query {
	
	private QueryBuilder queryBuilder;
	private List<Object> singleResponse;
	private List< HashMap<String, Object> > multipleResponse;
	private boolean success;
	
	private Database database;
	private Connection connector;
	private ResultSet rs;

	
	public Query() {
		
	}
	
	public Query(Database database) {
	
		this.queryBuilder = new QueryBuilder();
		this.singleResponse = new ArrayList<Object>();
		this.multipleResponse = new ArrayList< HashMap<String, Object> >();
		this.success = false;
		this.database = database;
		
	}

	/**
	 * @Task: Get a single record from database
	 * @param: List<String> fields name from database, String table name, String condition, String value for the condition
	 * @return: HashMap<String, String> with the fields as key and values as hashmap value
	 * */
	public HashMap<String, Object> get(List<String> fields, String table, String condition, List<Object> values) throws SQLException {
	
		if (!validateCondition(condition)) return null;
		
		this.connector = this.database.databaseManager();
		
		try {
						
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fields, table, condition));

			for(int i = 1; i <= values.size(); i++) {
				sql.setObject(i, values.get(i - 1));
			}

			rs = sql.executeQuery();

			if(rs.next()) {
				for (int i = 1; i <= getColumnSize(rs); i++) {
					singleResponse.add(rs.getObject(i));
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

	/**
	 * @Task Select with Inner join statement. The value from a table related to other. Example. Get blogs from a specific user
	 * */
	public List< HashMap<String, Object> > get(List<String> fields, String table, String tableToJoinWith,
			String relations, String condition, List<Object> conditionValues) throws SQLException{
	
		if (!validateCondition(condition)) return null;
		
		this.connector = this.database.databaseManager();
		
		List<Object> aux = new ArrayList<Object>();
		
		try {
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fields, table, tableToJoinWith, relations, condition));
			
			for (int i = 1; i <= conditionValues.size(); i ++) {
				sql.setObject(i, conditionValues.get(i - 1));
			}
			
			this.rs = sql.executeQuery();			

			while (rs.next()) {
				aux.clear();

				for(int i = 1; i <= getColumnSize(rs); i++) {
					aux.add(rs.getObject(i));
				}

				this.multipleResponse.add(generateSingleHashMap(fields, aux));
			}				
		
		} catch(Exception e) {
			e.printStackTrace();
		
		} finally {
			if (this.connector != null || !this.connector.isClosed() )
				this.connector.close();
		}
		
		return multipleResponse;
	}
	
	/**
	 * @Task: Get all the records from a table
	 * @param: List<String> fields name, String table
	 * @return List< HashMap<String, String> > 
	 * */
	public List< HashMap<String, Object> > getAll(List<String> fields, String table) throws SQLException {
		
		List<Object> aux = new ArrayList<Object>();
		
		this.connector = this.database.databaseManager();
		
		try {
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.select(fields, table));
			this.rs = sql.executeQuery();
			
				while (rs.next()) {
					aux.clear();
				
					for(int i = 1; i <= getColumnSize(rs); i++) {
						aux.add(rs.getObject(i));
					}
					
				this.multipleResponse.add(generateSingleHashMap(fields, aux));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.connector != null && !this.connector.isClosed() )
				this.connector.close();
		}
		
		return multipleResponse;
	}

	/**
	 * @Task Verify if a record already exist in the database
	 * @param: List<String> fields name from database, String table name, String condition, String value for the condition
	 * @return: HashMap<String, String> with the fields as key and values as hashmap value
	 * */
	public boolean exist(String table, String condition, List<Object> values) throws SQLException {
		
		if (!validateCondition(condition)) return false;
		
		this.connector = this.database.databaseManager();
		
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
	
	/**
	 * @Task: Save a record into database
	 * @param List<Strings> fields name, String table name, List<Object> values to save
	 * @return boolean
	 * */
	public boolean save(List<String> fields, String table, List<Object> values) throws SQLException{
		
		this.connector = this.database.databaseManager();
		
		try {
	
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.insert(fields, table));
	
			for (int i = 1; i <= fields.size(); i++) {
				sql.setObject(i, values.get(i - 1));
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
	
	/**
	 * @Task: Update a record from database
	 * @param List<String> fields name, String table name, List<Object> incoming values to update, String condition, String condition value
	 * @return boolean
	 * */
	public boolean update(String table, List<String> fields, List<Object> values, String condition, Object value) throws SQLException {
				
		this.connector = this.database.databaseManager();
		
		try {
					
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.update(fields, table, condition));
		
			for (int i = 1; i <= fields.size(); i++) {
				sql.setObject(i, values.get(i - 1));
			}			
			
			sql.setObject(fields.size() + 1, value);
			
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
	
	
	/**
	 * @Task: Delete a specific record from database. It will only delete by ID
	 * @param String table name, Object value
	 * @return boolean
	 * */
	public boolean delete(String table, Object parameterValue) throws SQLException{
		
		this.connector = this.database.databaseManager();
		
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
