//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 09:21:19 AM MST 
//


package edu.asu.lerna.iolaus.domain.queryobject.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.lerna.iolaus.domain.queryobject.IDatabase;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement
public class Database implements IDatabase {

    @XmlAttribute(name = "id", required = true)
    protected String id;

    /* (non-Javadoc)
	 * @see edu.asu.lerna.iolaus.domain.queryobject.impl.IDatabase#getId()
	 */
    @Override
	public String getId() {
        return id;
    }

    /* (non-Javadoc)
	 * @see edu.asu.lerna.iolaus.domain.queryobject.impl.IDatabase#setId(java.lang.String)
	 */
    @Override
	public void setId(String value) {
        this.id = value;
    }

    public static class Adapter extends XmlAdapter<Database,IDatabase> {
    	public IDatabase unmarshal(Database v) { return v; }
    	public Database marshal(IDatabase v) { return (Database)v; }
     }
}
