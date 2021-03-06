package edu.asu.lerna.iolaus.domain.mbl.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Institution extends Node {
	
	private String type = "Institution";
	private String uri;
	private String name;
	private String dataset = "mblcourses";
	private String iolausMappingId;
	
	public Institution(String uri, String name) {
		this.uri = uri;
		this.name = name;
		this.iolausMappingId = name;
	}
	
	public String getIolausMappingId() {
		return iolausMappingId;
	}


	public String getType() {
		return type;
	}
	
	public String getDataset() {
		return dataset;
	}
	
	public String getUri() {
		return uri;
	}

	public String getName() {
		return name;
	}

	public String toJson() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(this);
		return json;
	}

	@Override
	public List<String> getPropertyJson(String nodeId) {
		List<String> propertyJson = new ArrayList<String>();
		propertyJson.add(createJson(nodeId, "type", type));
		propertyJson.add(createJson(nodeId, "uri", uri));
		propertyJson.add(createJson(nodeId, "name", name));
		propertyJson.add(createJson(nodeId, "dataset", dataset));
		propertyJson.add(createJson(nodeId, "iolausMappingId", iolausMappingId));
		return propertyJson;
	}
}
