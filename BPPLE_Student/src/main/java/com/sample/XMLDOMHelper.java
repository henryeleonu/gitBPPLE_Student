package com.sample;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.File;
import java.io.Writer;
import java.io.StringWriter;

public class XMLDOMHelper {

	public XMLDOMHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static Document getDocument(String path_to_file) {
		Document xml = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(path_to_file);
			xml = builder.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return xml;
	}
	
	public static String getXMLContent (Document xml) {
		Transformer tf;
		String result = "";
		try {
			tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        tf.setOutputProperty(OutputKeys.INDENT, "yes");
	        Writer out = new StringWriter();
	        tf.transform(new DOMSource(xml), new StreamResult(out));
	        result = out.toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String extractTextChildren(Element parentNode) {
		//Extract all text children of an element
	    NodeList childNodes = parentNode.getChildNodes();
	    String result = new String();
	    for (int i = 0; i < childNodes.getLength(); i++) {
	      Node node = childNodes.item(i);
	      if (node.getNodeType() == Node.TEXT_NODE) {
	        result += node.getNodeValue();
	      }
	    }
	    return result;
	}
	
	//Extract nodelist of children of bpmn2:process or bpmndi:BPMNPlane tags
	public static NodeList extractNodeListOfChildren(Document doc, String xPathexp, String tagName) {
		Node parentNode;
		NodeList nodeList = (NodeList)doc.getElementsByTagName(tagName);
        parentNode = (Node)nodeList.item(0);
        NodeList childNodes = parentNode.getChildNodes();
        return childNodes;  
	}
	
	public static Document removeNodeFromDOMDocument(Document doc, String tag_name) {
		// retrieve the element 'link'
        Element element = (Element) doc.getElementsByTagName(tag_name).item(0);
 
        // remove the specific node
        element.getParentNode().removeChild(element);
         
        // Normalize the DOM tree, puts all text nodes in the
        // full depth of the sub-tree underneath this node
        doc.normalize();
        return doc;
	}
	
	public static void addNodeListToNodeByAttributeValue(Document destinationDoc, String xPathexp, String tagName, String attributeName, String attributeValue, NodeList node_list) {
		Node nodeToInsertIn = getNodeByAttributeValue(destinationDoc, xPathexp, tagName, attributeName, attributeValue); //node to insert node_list in
		for (int i = 0; i < node_list.getLength(); i++) {
            Node node = node_list.item(i);
            Node importedNode = destinationDoc.importNode(node, true);
            nodeToInsertIn.appendChild(importedNode);
        }	
	}
	
	public static void addNodeListToFirstNode(Document destinationDoc, String xPathexp, String tagName, NodeList node_list) {
		Node nodeToInsertIn = getFirstNodeFromNodeList(destinationDoc, xPathexp, tagName); //node to insert node_list in
		for (int i = 0; i < node_list.getLength(); i++) {
            Node node = node_list.item(i);
            Node importedNode = destinationDoc.importNode(node, true);
            nodeToInsertIn.appendChild(importedNode);
        }
		//saveXMLContent(destinationDoc);
		
	}
	
	  public static Node getNodeByAttributeValue(Document doc, String xPathexp, String tagName, String attributeName, String attributeValue) {
		  Node currentItem = null;
		  NodeList nodes = null;
		  nodes = doc.getElementsByTagName(tagName); 
			for (int i = 0; i < nodes.getLength(); i++)
	        {
	            currentItem = nodes.item(i);
	            String value = currentItem.getAttributes().getNamedItem(attributeName).getNodeValue();
	            if(value.equals(attributeValue)) {
	            	return currentItem;
	            } 
	        }
			return null;
	  }
	  
	  public static Node getFirstNodeFromNodeList(Document doc, String xPathexp, String tagName) {
		  Node currentItem = null;
		  NodeList nodes = null;
		  XPath xPath = XPathFactory.newInstance().newXPath();
	        XPathExpression xPathExpression;
			try {
				xPathExpression = xPath.compile(xPathexp);
				nodes = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       currentItem = nodes.item(0);    
	       return currentItem;
	  }
	  
	  public static NodeList getNodeListByTagName(Document doc, String xPathexp) {
			XPath xPath =  XPathFactory.newInstance().newXPath();     
	         NodeList nodeList = null;
			try {
				nodeList = (NodeList) xPath.compile(xPathexp).evaluate(doc, XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(nodeList.getLength() + "yes");
	        return nodeList;
	  }
	
	public static void saveXMLContent (Document document, String xmlFilePath) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
	        transformer.transform(domSource, streamResult);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Done creating XML File");	
	}

}
