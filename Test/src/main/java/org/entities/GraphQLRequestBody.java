package org.entities;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class GraphQLRequestBody {

	@Expose
	private String query;
	@Expose
	private String operationName;
	@Expose
	private Map<String, Object> varaibles = new HashMap<>();

	public GraphQLRequestBody() {
		// TODO Auto-generated constructor stub
	}

	public GraphQLRequestBody(String query, String operationName, Map<String, Object> varaibles) {
		super();
		this.query = query;
		this.operationName = operationName;
		this.varaibles = varaibles;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Map<String, Object> getVaraibles() {
		return varaibles;
	}

	public void setVaraibles(Map<String, Object> varaibles) {
		this.varaibles = varaibles;
	}

}
