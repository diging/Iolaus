package edu.asu.lerna.iolaus.domain.mbl.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Location extends Node {
	
	private String type = "Location";
	private String uri;
	private String location;
	private String dataset = "mblcourses";
	private String iolausMappingId;
	
	public Location(String uri, String location) {
		this.uri = uri;
		this.location = location;
		this.iolausMappingId = location;
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

	public String getLocation() {
		return location;
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
		propertyJson.add(createJson(nodeId, "location", location));
		propertyJson.add(createJson(nodeId, "dataset", dataset));
		propertyJson.add(createJson(nodeId, "iolausMappingId", iolausMappingId));
		return propertyJson;
	}
}
