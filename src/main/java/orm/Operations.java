package orm;

import static utils.Utils.*;
import static orm.Database.*;

import java.util.*;

import java.sql.*;



public class Operations {
	
	private QueryBuilder queryBuilder;
	private List<Object> singleResponse;
	private List< HashMap<String, String> > multipleResponse;
	private int index;
	private boolean success;
	
	private Connection connector;
	private ResultSet rs;
	
	private String host;
	private String database;
	private String user;
	private String password;
	
	public Operations() {
		
	}
	
	public Operations(String host, String database, String user, String password) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		
		this.queryBuilder = new QueryBuilder();
		this.singleResponse = new ArrayList<Object>();
		this.multipleResponse = new ArrayList< HashMap<String, String> >();
		this.index = 1;
		this.success = false;
		this.connector = getConnection(this.host, this.database, this.user, this.password);
	}
	
	
	/*
	 * @Get a single record from database
	 * @param: List<String> fields name from database, String table name, String condition, String value for the condition
	 * @return: HashMap<String, String> with the fields as key and database values as value
	 * */
	public HashMap<String, String> get(List<String> fields, String table, String parameter, String values) throws SQLException {
		try {
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.selectQueryWhere(fields, table, parameter));
			sql.setObject(1, values);
			
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
	 * @Return all the records from the table given
	 * @param: List<String> fields name, String database table
	 * @return List< HashMap<String, String> > It´s similar to a json structure 
	 * */
	public List< HashMap<String, String> > getAll(List<String> fields, String table) throws SQLException {
		
		List<Object> aux = new ArrayList<Object>();
		
		try {
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.selectQuery(fields, table));
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
	 * @Save a record into database
	 * @param List<Strings> fields name, String table name, List<Object> values to save
	 * @return boolean
	 * */
	public boolean save(List<String> fields, String table, List<Object> values) throws SQLException{
		
		try {
			this.index = 0;
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.insertQuery(fields, table));
	
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
	public boolean update(List<String> fields, String table, List<Object> values, String parameter, Object parameterValue) throws SQLException {
		try {
			
			this.index = 0;
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.updateQuery(fields, table, parameter));
			
			for (int i = 1; i <= fields.size(); i++) {
				sql.setObject(i, values.get(this.index));
				this.index++;
			}			
			
			sql.setObject(fields.size() + 1, parameterValue); //TODO: Aca va el valor del WHERE 
			
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
			
			PreparedStatement sql = this.connector.prepareStatement(this.queryBuilder.deleteQueryWhere(table));
			
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
