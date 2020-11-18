package ormTesting;

import static sorm4j.orm.utils.Utils.*;

import sorm4j.orm.query.Query;
import sorm4j.orm.utils.Database;
import sorm4j.orm.utils.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.sql.*;

public class Testing {
	 
	public static void main (String [] args) throws SQLException, InstantiationException, IllegalAccessException {

		Query q = new Query(new Database("localhost:3306", "blogsystem", "root", "", "mysql").databaseManager());
		Response response = new Response();
		HashMap<String, Object> res = new HashMap<String, Object>();
		
		
		res = q.get(Arrays.asList("name"), "user", "id", "53ff7164-81ee-4704-bd74-fedd5669480e");
		
		User u = new User(res.get("name").toString());

	}

}