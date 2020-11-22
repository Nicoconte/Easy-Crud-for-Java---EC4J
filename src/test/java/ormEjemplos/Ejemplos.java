package ormEjemplos;

import static sorm4j.orm.utils.Utils.*;

import sorm4j.orm.query.Query;
import sorm4j.orm.builder.QueryBuilder;
import sorm4j.orm.utils.Database;
import sorm4j.orm.utils.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.sql.*;

public class Ejemplos {
	
	
	public static void main (String [] args) throws SQLException, InstantiationException, IllegalAccessException {

		Query q = new Query(new Database("localhost:3306", "blogsystem", "root", "", "mysql"));
		Response response = new Response();
		HashMap<String, Object> res = new HashMap<String, Object>();		
		Object responseValue = null;
		QueryBuilder builder = new QueryBuilder();
		List <HashMap<String, Object> > listResponse;
		
		
		/*//Guardar registro
		responseValue = q.save(Arrays.asList("id", "name", "password"), 
				"user", 
				Arrays.asList(UUID.randomUUID().toString(), "Limp bizkit", "312"));
		
		//Actualizar registro
		responseValue = q.update("user", //Update <tabla> set 
				Arrays.asList("name"), // set <campo>
				Arrays.asList("Admin modificado"), // <campo>=<valores>
				"id", "9ce25b94-c7f3-446a-b374-365705cfa781"); //Where <condicion> = <valor>
		
		//Eliminar registro
		responseValue = q.delete("user", //Delete from <tabla>
				"7f141d8c-983a-4855-b254-328773150891"); // Where id=<valor>
		
		//-----------------------------------------------------------------------------

		//Verificar si un registro existe
		responseValue = q.exist("user", //Select * from <tabla> 
					"WHERE name=? AND password=?", 
					Arrays.asList("lolazo", "1323")); //Where <condicion> = <valor>*/ 				
		
		responseValue = q.save(Arrays.asList("id", "name", "password"), 
				"user", 
				Arrays.asList(UUID.randomUUID().toString(), "Linkin park", "500"));
		
		//Obtener valores ligados con otras tablas
		listResponse = q.get(Arrays.asList("blog.author", "blog.title", "blog.content"), 
					"blog", 
					"user", 
					"blog.author=user.name",
					"WHERE blog.author=?", 
					Arrays.asList("lolazo"));
					
		//Obtener un solo registro
		res = q.get(Arrays.asList("id", "name", "password"), 
					"user", 
					"WHERE name=?", 
					Arrays.asList("Linkin park")); 			
		
		responseValue = q.exist("user", 
				"WHERE name=? AND password=?", 
				Arrays.asList("Linkin park", "500"));
	
		print("respuesta => " + listResponse);
		
		print(res);
		
		print("Existe => " + responseValue );
		
	}
}