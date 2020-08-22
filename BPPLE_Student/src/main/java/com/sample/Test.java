package com.sample;

import org.w3c.dom.Document;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String contextInfoXMLFilePath = "C:\\Users\\eleon\\eclipse-workspace\\BPPL\\src\\main\\resources\\data\\ContextInfo.xml";
		Document contextInfoDoc = XMLDOMHelper.getDocument(contextInfoXMLFilePath);
		
		ContextInfo contextInfo = null;
		contextInfo = new ContextInfo();
		//contextInfo.setAbstractContainerAttrValue(attributeValue);
		contextInfo.setContextInfoDoc(contextInfoDoc);
		contextInfo.getContextValueByKey("Funding");
		//System.out.println("The University is: " + contextInfo.getContextValueByKey("Funding"));

	}

}
