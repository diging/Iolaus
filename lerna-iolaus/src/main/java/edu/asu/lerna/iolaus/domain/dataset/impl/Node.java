//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.06 at 01:16:07 PM MST 
//


package edu.asu.lerna.iolaus.domain.dataset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.asu.lerna.iolaus.domain.dataset.INode;
import edu.asu.lerna.iolaus.domain.dataset.IProperty;



/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="propertyList" type="{http://digitalhps.org/lerna-Dataset-model}PropertyList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "label",
    "propertyList"
})
public class Node implements INode{

    protected long id;
    protected String label;
    
    @XmlElementWrapper(name="propertyList")
    @XmlElement(name="property")
    @XmlJavaTypeAdapter(Property.Adapter.class) protected List<IProperty> propertyList;

    /**
     * Gets the value of the id property.
     * 
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    @Override
    public void setId(long value) {
        this.id = value;
    }
    
    @Override
    public String getLabel(){
    	return label;
    }
    
    @Override
    public void setLabel(String value){
    	this.label=value;
    }
    /**
     * Gets the value of the propertyList property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyList }
     *     
     */
    @Override
    public List<IProperty> getPropertyList() {
        return propertyList;
    }

    /**
     * Sets the value of the propertyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyList }
     *     
     */
    @Override
    public void setPropertyList(List<IProperty> value) {
        this.propertyList = value;
    }
    
    @Override
    public String getJsonNode(){
    	StringBuffer jsonBody=new StringBuffer();
		jsonBody.append("{\n\t");
		if(label!=null){
			if(!isNumeric(label))
				jsonBody.append("\"label\" : "+"\""+label+"\"");
			else
				jsonBody.append("\"label\" : "+label);
		}
		if(propertyList!=null){
			for(IProperty property:propertyList){
				if(!isNumeric(property.getValue()))
					jsonBody.append(",\n\t\""+property.getName()+"\" : \""+property.getValue()+"\" ");
				else 
					jsonBody.append(",\n\t\""+property.getName()+"\" : "+property.getValue());
			}
		}
		jsonBody.append("\n}");
		return jsonBody.toString();
	}
    /**
	 * This method checks if String is numeric.
	 * @param  value   is a String
	 * @return         true if it is Numeric else return false
	 */
	private boolean isNumeric(String value) {
		return value.matches("(\\d*)");
	}
	
    public static class Adapter extends XmlAdapter<Node,INode> {
    	public INode unmarshal(Node v) { return v; }
    	public Node marshal(INode v) { return (Node)v; }
    }

	@Override
	public List<String> getNodeAsJsonForIndexing(String uri) {
		List<String> jsons = new ArrayList<String>();
		StringBuffer jsonBody=new StringBuffer();
		
		if(label != null){
			jsonBody.append("{\n\t");
			jsonBody.append("\"key\" : \"label\",");
			jsonBody.append("\n\t\"value\" : \""+ label + "\",");
			jsonBody.append("\n\t\"uri\" : \"" + uri + "\"");
			jsonBody.append("\n}");
			jsons.add(jsonBody.toString());
		}
		if(propertyList != null){
			for(IProperty property:propertyList){
				jsonBody=new StringBuffer();
				jsonBody.append("{\n\t");
				jsonBody.append("\"key\" : \""+property.getName()+"\",");
				jsonBody.append("\n\t\"value\" : \"" + property.getValue() + "\",");
				jsonBody.append("\n\t\"uri\" : \"" + uri + "\"");
				jsonBody.append("\n}");
				jsons.add(jsonBody.toString());
			}
		}
		
		return jsons;
	}

}