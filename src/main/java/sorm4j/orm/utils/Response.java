package sorm4j.orm.utils;

import static sorm4j.orm.utils.Utils.*;

import java.util.HashMap;

public class Response {
	
	private HashMap<String, Object> map;
	
	
	public Response() {
		
	}
	
	public Response(HashMap<String, Object> map) {
		this.map = map;
	}
	
}
