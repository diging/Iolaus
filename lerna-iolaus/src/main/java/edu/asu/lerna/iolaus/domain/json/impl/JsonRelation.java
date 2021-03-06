package edu.asu.lerna.iolaus.domain.json.impl;

import java.io.Serializable;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.lerna.iolaus.domain.json.Element;
import edu.asu.lerna.iolaus.domain.json.IJsonRelation;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonRelation extends Element implements IJsonRelation,
		Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private String startNode;
	private String endNode;
	private HashMap<String, String> data;

	public JsonRelation() {
		data = new HashMap<String, String>();
	}

	@Override
	public String getEndNode() {
		return endNode;
	}

	@Override
	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	@Override
	public String getStartNode() {
		return startNode;
	}

	@Override
	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public HashMap<String, String> getData() {
		return data;
	}

	@Override
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	@Override
	public void addData(String key, String value) {
		if (this.data == null)
			this.data = new HashMap<String, String>();
		this.data.put(key, value);
	}
	
	public static class Adapter extends XmlAdapter<JsonRelation, IJsonRelation>
	{

		@Override
		public IJsonRelation unmarshal(JsonRelation v) throws Exception {
			return v;
		}

		@Override
		public JsonRelation marshal(IJsonRelation v) throws Exception {
			return (JsonRelation)v;
		}
		
	}

}
