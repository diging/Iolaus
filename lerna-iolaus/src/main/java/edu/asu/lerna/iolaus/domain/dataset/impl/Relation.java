//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.06 at 01:16:07 PM MST 
//


package edu.asu.lerna.iolaus.domain.dataset.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.asu.lerna.iolaus.domain.dataset.IRelation;
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
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="propertyList" type="{http://digitalhps.org/lerna-Dataset-model}PropertyList" minOccurs="0"/>
 *         &lt;element name="startNode" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="endNode" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "type",
    "propertyList",
    "startNode",
    "endNode"
})
public class Relation implements IRelation {

	@XmlElement(required = true)
	protected long id;
    @XmlElement(required = true)
    protected String type;
   
    @XmlElementWrapper(name="propertyList")
    @XmlElement(name="property")
    @XmlJavaTypeAdapter(Property.Adapter.class) protected List<IProperty> propertyList;
    
    @XmlElement(required = true)
    protected long startNode;
    @XmlElement(required = true)
    protected long endNode;


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

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Override
    public void setType(String value) {
        this.type = value;
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

    /**
     * Gets the value of the startNode property.
     * 
     */
    @Override
    public long getStartNode() {
        return startNode;
    }

    /**
     * Sets the value of the startNode property.
     * 
     */
    @Override
    public void setStartNode(long value) {
        this.startNode = value;
    }

    /**
     * Gets the value of the endNode property.
     * 
     */
    @Override
    public long getEndNode() {
        return endNode;
    }

    /**
     * Sets the value of the endNode property.
     * 
     */
    @Override
    public void setEndNode(long value) {
        this.endNode = value;
    }
    
    
    @Override
    public String getJsonRelation(String endNode) {
    	StringBuffer jsonBody=new StringBuffer();
		if(endNode==null || type==null){
			return null;	
		}
		else{
			jsonBody.append("{\n");
			jsonBody.append("\t\"to\" : "+"\""+endNode+"\"");
			jsonBody.append(" ,\n\t\"type\" : "+"\""+type+"\", ");
			if(propertyList!=null){
				jsonBody.append("\n\t\"data\" : {");
				boolean firstProperty=true;
				for(IProperty property:propertyList){
					if(firstProperty){
						if(!isNumeric(property.getValue()))
							jsonBody.append(" \n\t\t\""+property.getName()+"\" : "+"\""+property.getValue()+"\"");
						else
							jsonBody.append(" \n\t\t\""+property.getName()+"\" : "+property.getValue());
						firstProperty=false;
					}
					else{
						if(!isNumeric(property.getValue()))
							jsonBody.append(" ,\n\t\t\""+property.getName()+"\" : "+"\""+property.getValue()+"\"");
						else
							jsonBody.append(" ,\n\t\t\""+property.getName()+"\" : "+property.getValue());
					}
				}
				jsonBody.append(" ,\n\t\t\"label\" : "+"\""+type+"\"");
				jsonBody.append("\n\t}");
			}
			jsonBody.append("\n}");
		}
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
    
    public static class Adapter extends XmlAdapter<Relation,IRelation> {
    	public IRelation unmarshal(Relation v) { return v; }
    	public Relation marshal(IRelation v) { return (Relation)v; }
    }

}