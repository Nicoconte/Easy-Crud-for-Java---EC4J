

# -- Easy Crud for java - EC4J :coffee: --

## EC4J es una libreria escrito en java y para java.

### Manipula facilmente pequeñas / medianas base de datos sin la necesidad de perder tiempo creando las consultas desde 0 u ocurrir a librerias complejas.


### *v.1*:

**Consultas disponibles** :white_check_mark:

  - SELECT FROM 
  - SELECT FROM WHERE 
  - SELECT INNER JOIN
  - UPDATE SET WHERE  
  - DELETE FROM WHERE  

**Base de datos | Motores** :notebook:
  - MySQL
  - SQLServer
  - MariaDB


### Ejemplos :pencil2:

```java
import sorm4j.orm.*; 

//Guarda el resultado del metodo "getAll"
List< HashMap<String, Object> > allResponse;
		
//Guarda el resultado del metodo "get"
HashMap<String, Object> singleResponse;
		
//Guarda el resultado del metodo "save / update / delete"
boolean responseValue;
	
//Conexion a la base de datos
Query op = new Query(new Database("localhost:3306", "blogsystem", "root", "", "mysql"));
		
		
//Guardar registro 
responseValue = op.save(Arrays.asList("id", "name", "password"), 		// INSERT (campos)
			"user",                          			// INTO (tabla)
			Arrays.asList("1", "usuario generico 123", "lol"));     // VALUES (valores)   
				
print("Usuario guardado => " + responseValue);
		
// ---------------------------------------------------------------------------------------

//Obtener un registro
singleResponse = op.get(Arrays.asList("id", "name", "password"), // SELECT (campos)
			"user", 				// FROM (tabla)
			"WHERE id=?", 				// (Condicion)
			Arrays.asList("1"));			// (Valores)
		
print("Resultado obtenido => " + singleResponse);
print("Fetch de resultado obtenido anteriormente => " + singleResponse.get("name") );

// ---------------------------------------------------------------------------------------
		
//Actualizar registro
responseValue = op.update("user",                               // UPDATE (tabla)
			Arrays.asList("name", "password"), 	// SET (campos)
			Arrays.asList("el pepe", "lolazo"), 	// =(valores)
			"id", 					// WHERE (campo)
			"1");					// =(valor)
		
print("Usuario actualizado => " + responseValue);
		
// ---------------------------------------------------------------------------------------
		
//Obtener registro ligado a otra tabla
allResponse = op.get(Arrays.asList("blog.author", "blog.title"), // SELECT (campos)
		    "blog", 					// FROM (tabla)
		    "user", 					// INNER JOIN (otra tabla)
		    "blog.author=user.name", 			// ON (relaciones)
		    "WHERE blog.author=?", 			// (condicion)
		    Arrays.asList("el pepe"));			// =(valor)
		
print("Todas las respuestas => " + "\n" + allResponse);
		
// ---------------------------------------------------------------------------------------
		
		
//Obtener todos los registros de una tabla
allResponse = op.getAll(Arrays.asList("id", "name", "password"), // SELECT (campos)
			"user");				 // FROM (tabla)		
		
print("Todas las respuestas => " + allResponse);
		
// ---------------------------------------------------------------------------------------
		
//Verificar si un registro existe en la base de datos 
responseValue = op.exist("user", 				// SELECT * FROM (tabla)
			"WHERE name=? AND password=?", 		// (condicion)
			Arrays.asList("el pepe", "lolazo"));	// = (valores)
		
print("El registro existe => " + responseValue);
		
// ---------------------------------------------------------------------------------------

//Eliminar registro
responseValue = op.delete("user", 			// DELETE FROM (tabla)
			"1");				// WHERE id=(valor)
				
print("Registro eliminado => " + responseValue);

		
```

*:file_folder: Enlace de descarga: https://drive.google.com/file/d/1LLkARy5B2Pt7n55g6cKdzGU9FWqB1Ry4/view?usp=sharing *

*Desarrollado por Conte Nicolas* :copyright:
