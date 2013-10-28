//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 09:21:19 AM MST 
//


package edu.asu.lerna.iolaus.domain.queryobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aspectj.weaver.ast.Instanceof;
import org.hamcrest.core.IsInstanceOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.lerna.iolaus.rest.QueryController;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://digitalhps.org/lerna-query-model}property" minOccurs="0"/>
 *         &lt;element ref="{http://digitalhps.org/lerna-query-model}relationship" minOccurs="0"/>
 *         &lt;element ref="{http://digitalhps.org/lerna-query-model}and" minOccurs="0"/>
 *         &lt;element ref="{http://digitalhps.org/lerna-query-model}or" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="return" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"propertyOrRelationshipOrAnd"
})
@XmlRootElement(name = "node")
public class Node {

	private static final Logger logger = LoggerFactory
			.getLogger(Node.class);

	@XmlElementRefs({
		@XmlElementRef(name = "or", namespace = "http://digitalhps.org/lerna-query-model", type = JAXBElement.class, required = false),
		@XmlElementRef(name = "and", namespace = "http://digitalhps.org/lerna-query-model", type = JAXBElement.class, required = false),
		@XmlElementRef(name = "relationship", namespace = "http://digitalhps.org/lerna-query-model", type = Relationship.class, required = false),
		@XmlElementRef(name = "property", namespace = "http://digitalhps.org/lerna-query-model", type = Property.class, required = false)
	})
	protected List<Object> propertyOrRelationshipOrAnd;
	@XmlAttribute(name = "return")
	protected Boolean _return;
	@XmlAttribute(name = "id")
	protected String id;

	/**
	 * Gets the value of the propertyOrRelationshipOrAnd property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the propertyOrRelationshipOrAnd property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getPropertyOrRelationshipOrAnd().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Relationship }
	 * {@link JAXBElement }{@code <}{@link Operator }{@code >}
	 * {@link JAXBElement }{@code <}{@link Operator }{@code >}
	 * {@link Property }
	 * 
	 * 
	 */
	public List<Object> getPropertyOrRelationshipOrAnd() {
		if (propertyOrRelationshipOrAnd == null) {
			propertyOrRelationshipOrAnd = new ArrayList<Object>();
		}
		return this.propertyOrRelationshipOrAnd;
	}

	/**
	 * Gets the value of the return property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Boolean }
	 *     
	 */
	public Boolean isReturn() {
		return _return;
	}

	/**
	 * Sets the value of the return property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Boolean }
	 *     
	 */
	public void setReturn(Boolean value) {
		this._return = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setId(String value) {
		this.id = value;
	}


	/**
	 * Gets the details of the node.
	 * 
	 * @return
	 *     possible object in
	 *     {@link Node }
	 *     
	 */
	public void getNodeDetails(edu.asu.lerna.iolaus.domain.queryobject.Node node){
		
		logger.info("Node return status : "+_return);
		List<Object> nodeDetails = node.propertyOrRelationshipOrAnd;
		Iterator<Object> nodeDetailsIterator = nodeDetails.iterator();
		int count =0;
		while(nodeDetailsIterator.hasNext()){
			JAXBElement<?> element = (JAXBElement<Object>) nodeDetailsIterator.next();

			if(element.getName().toString().contains("}and")){
				logger.info("We have a AND operator");
				Operator opAnd = (Operator) element.getValue();
				opAnd.parseOperator(opAnd);
			}else if(element.getName().toString().contains("}or")){
				logger.info("We have a OR operator");
				Operator opOr = (Operator) element.getValue();
				opOr.parseOperator(opOr);
			}else if(element.getName().toString().contains("}Property")){
				logger.info("Found property object");
    			logger.info("Property element : "+element.getClass());
    			Property prop = (Property) element.getValue();
    			prop.parseProperty(prop);
    		}

			count++;
		}
		logger.info("Count : "+count);

	}

}
