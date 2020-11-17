package ormTesting;

import static utils.Utils.*;
import orm.*;

import java.util.Arrays;
import java.util.UUID;
import java.sql.*;

public class Testing {
	 
	public static void main (String [] args) throws SQLException {

		Operations op = new Operations("localhost:3306", "blogsystem", "root", "");
		
		op.save(Arrays.asList("id", "name", "password"), "user", Arrays.asList(UUID.randomUUID().toString(), "lolazo", "1323")); 
	
	}

}