package test;

import static utils.Utils.*;
import static orm.Database.*;
import orm.*;

import java.util.Arrays;
import java.util.UUID;
import java.sql.*;

public class Testing {
	 
	public static void main (String [] args) throws SQLException {

		Operations op = new Operations("localhost:3306", "blogsystem", "root", "");
		
		op.save(Arrays.asList("id", "name", "password"), // ==> Insert <campos>
				"user", // ==> into <tabla>
				Arrays.asList(UUID.randomUUID().toString(), "lolazo", "1323")); // ==> values <valores>

	}

}
