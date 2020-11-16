package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;

public class Utils {
	
	public static void print(Object object) {
		System.out.println(object);
	}
	
	//TODO: OPTIMIZAR EL METODO
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
	
	public static List<String> getAllFields(Object obj) {
		return getClassAttributes(obj.getClass().getDeclaredFields());
	}

	public static HashMap<String, String> generateSingleHashMap(List<String> fields, List<Object> values) {
		HashMap<String, String> response = new HashMap<String, String>();
		
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
	
}
