//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.06 at 01:16:07 PM MST 
//


package edu.asu.lerna.iolaus.domain.dataset.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import edu.asu.lerna.iolaus.domain.dataset.IDatabase;
import edu.asu.lerna.iolaus.domain.dataset.INode;
import edu.asu.lerna.iolaus.domain.dataset.IProperty;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.digitalhps.lerna_dataset_model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NodeList_QNAME = new QName("http://digitalhps.org/lerna-Dataset-model", "NodeList");
    private final static QName _PropertyList_QNAME = new QName("http://digitalhps.org/lerna-Dataset-model", "PropertyList");
    private final static QName _DatabaseList_QNAME = new QName("http://digitalhps.org/lerna-Dataset-model", "DatabaseList");
    private final static QName _RelationList_QNAME = new QName("http://digitalhps.org/lerna-Dataset-model", "RelationList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.digitalhps.lerna_dataset_model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PropertyList }
     * 
     */
    public PropertyList createPropertyList() {
        return new PropertyList();
    }

    /**
     * Create an instance of {@link NodeList }
     * 
     */
    public NodeList createNodeList() {
        return new NodeList();
    }

    /**
     * Create an instance of {@link DatabaseList }
     * 
     */
    public DatabaseList createDatabaseList() {
        return new DatabaseList();
    }

    /**
     * Create an instance of {@link RelationList }
     * 
     */
    public RelationList createRelationList() {
        return new RelationList();
    }

    /**
     * Create an instance of {@link org.digitalhps.lerna_dataset_model.Relation }
     * 
     */
    public Relation createRelation() {
        return new Relation();
    }

    /**
     * Create an instance of {@link org.digitalhps.lerna_dataset_model.Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Dataset }
     * 
     */
    public Dataset createDataSet() {
        return new Dataset();
    }

    /**
     * Create an instance of {@link org.digitalhps.lerna_dataset_model.Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link org.digitalhps.lerna_dataset_model.Database }
     * 
     */
    public IDatabase createDatabase() {
        return new Database();
    }

    /**
     * Create an instance of {@link PropertyList.Property }
     * 
     */
    public IProperty createPropertyListProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link NodeList.Node }
     * 
     */
    public INode createNodeListNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link DatabaseList.Database }
     * 
     */
    public Database createDatabaseListDatabase() {
        return new Database();
    }

    /**
     * Create an instance of {@link RelationList.Relation }
     * 
     */
    public RelationList.Relation createRelationListRelation() {
        return new RelationList.Relation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://digitalhps.org/lerna-Dataset-model", name = "NodeList")
    public JAXBElement<NodeList> createNodeList(NodeList value) {
        return new JAXBElement<NodeList>(_NodeList_QNAME, NodeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://digitalhps.org/lerna-Dataset-model", name = "PropertyList")
    public JAXBElement<PropertyList> createPropertyList(PropertyList value) {
        return new JAXBElement<PropertyList>(_PropertyList_QNAME, PropertyList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatabaseList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://digitalhps.org/lerna-Dataset-model", name = "DatabaseList")
    public JAXBElement<DatabaseList> createDatabaseList(DatabaseList value) {
        return new JAXBElement<DatabaseList>(_DatabaseList_QNAME, DatabaseList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelationList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://digitalhps.org/lerna-Dataset-model", name = "RelationList")
    public JAXBElement<RelationList> createRelationList(RelationList value) {
        return new JAXBElement<RelationList>(_RelationList_QNAME, RelationList.class, null, value);
    }

}
