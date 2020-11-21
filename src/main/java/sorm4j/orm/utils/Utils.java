package sorm4j.orm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Constructor;
import java.sql.*;

public class Utils {
	
	/**
	 * @This is a method for lazy developers. Print things XD 
	 * @param any Object 
	 * @void
	 * */
	public static void print(Object object) {
		System.out.println(object);
	}
	
	/**
	 * @Isolate class attributes. 
	 * @param Arrays of Object
	 * @return a list of String with the attributes
	 * */
	public static List<String> getClassAttributes(Object arr[] ) {
		List<String> attributes = new ArrayList<String>();
		List<String[]> aux = new ArrayList<String[]> ();
		
		for(int i = 0; i < arr.length; i++) {
			aux.add(arr[i].toString().split(" "));
		}
		
		for(String[] s : aux) {
			attributes.add( s[ s.length - 1 ].toString().replace(".", " ").split(" ")[ s.length - 1 ].toString() );
		}
		
		return attributes;
	}
	
	
	/**
	 * @Get class Attributes. We can use this method if class attributes match with table fields
	 * @return a list of string with the fields 
	 * */
	public static List<String> getAllFields(Object obj) {
		return getClassAttributes(obj.getClass().getDeclaredFields());
	}
	
	/**
	 * @Generate a hash map with the fields from table and values from database response
	 * @param list string with fields and list of object with the returned values
	 * @return a string, Object hash map
	 * */
	public static HashMap<String, Object> generateSingleHashMap(List<String> fields, List<Object> values) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		for (int i = 0; i < fields.size(); i++) {
			response.put(fields.get(i), values.get(i).toString());
		}
		
		return response;
 	}
	
	public static int getColumnSize(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		return size;
	}
	
	
	public static boolean validateCondition(String condition) {
		return (condition.indexOf("?") > -1);
	}
	
}
