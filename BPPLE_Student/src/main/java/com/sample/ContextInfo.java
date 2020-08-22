package com.sample;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class ContextInfo {
	Document contextInfoDoc;
	String abstractContainerAttrValue;
	String fragFilePath;
	Map< String,String> contextList =  new HashMap< String,String>();

	public ContextInfo() {
		// TODO Auto-generated constructor stub
	}

	public Document getContextInfoDoc() {
		return contextInfoDoc;
	}

	public void setContextInfoDoc(Document contextInfoDoc) {
		this.contextInfoDoc = contextInfoDoc;
		setContextList(contextInfoDoc);
	}
	
	public void setContextList(Document doc) {
		 NodeList contextNodeList = doc.getElementsByTagName("context");
		 for (int i = 0; i < contextNodeList.getLength(); i++) {
			 Node aNode = contextNodeList.item(i);
			 Element contextElement = (Element)aNode;
			 String contextKey = contextElement.getAttribute("key");
			 String contextValue = getContextValueByKey(contextKey);
			 contextList.put(contextKey.trim(), contextValue);
		 }
	}

	public String getAbstractContainerAttrValue() {
		return abstractContainerAttrValue;
	}

	public void setAbstractContainerAttrValue(String abstractContainerAttrValue) {
		this.abstractContainerAttrValue = abstractContainerAttrValue;
	}
	
	public String getContextValueByKey(String key) {
		
		String xPathexp = "/contextInfo/context";
		String tagName = "context";
		Node contextNode = XMLDOMHelper.getNodeByAttributeValue(contextInfoDoc, xPathexp, tagName, "key", key);
		String str = contextNode.getTextContent();
		return str.trim();
	}
	
	public void setContextValueByKey(String key, String newValue) {
		String xPathexp = "/contextInfo/context";
		String tagName = "context";
		Node contextNode = XMLDOMHelper.getNodeByAttributeValue(contextInfoDoc, xPathexp, tagName, "key", key);
		Node contextValue = contextNode.getFirstChild();
		contextValue.setNodeValue(newValue);
	}

	public String getFragFilePath() {
		return fragFilePath;
	}

	public void setFragFilePath(String fragFilePath) {
		this.fragFilePath = fragFilePath;
	}

	public Map<String, String> getContextList() {
		return contextList;
	}
}
