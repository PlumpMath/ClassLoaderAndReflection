package ru.svetozarov.reflection;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Evgenij Svetozarov on 10.02.2017.
 */
public class Reflection {
    public void serialize(Object object){
        try {
            Class obj=object.getClass();
            Field[] fields=obj.getDeclaredFields();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


            Document doc = docBuilder.newDocument();

            Element header = doc.createElement("Object");
            doc.appendChild(header);

            Attr attr_header = doc.createAttribute("type");
            attr_header.setValue(obj.getName().substring(obj.getName().lastIndexOf(".")+1));
            header.setAttributeNode(attr_header);

            for (Field field :
                    fields) {
                try {
                    field.setAccessible(true);
                    createTag(doc, header,field.getType().getName(),field.getName(),field.get(object).toString());

                } catch (IllegalAccessException e) {
                    System.out.println("Error read value field...");
                    e.printStackTrace();
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("file.xml"));

            transformer.transform(source, result);
            System.out.println("Serialization is over");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void deserealize(){
        try {

            File fXmlFile = new File("file.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("field");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                   // System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("int").item(0).getTextContent());
                    //System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    //System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    //System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createTag(Document doc, Element head,  String typeValue, String idValue, String value) throws IllegalAccessException{
        Element element = doc.createElement("field");
        head.appendChild(element);

        Attr attrType = doc.createAttribute("type");
        attrType.setValue(typeValue.substring(typeValue.lastIndexOf(".")+1));
        element.setAttributeNode(attrType);

        Attr attrId = doc.createAttribute("id");
        attrId.setValue(idValue);
        element.setAttributeNode(attrId);

        Attr attrValue = doc.createAttribute("value");
        attrValue.setValue(value);
        element.setAttributeNode(attrValue);
    }
}
