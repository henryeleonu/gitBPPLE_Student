package com.sample;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VariantGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VariantGenerator vg = new VariantGenerator();
		vg.generateVariant();
	}
	
	public void generateVariant() {
		Node currentItem;
		String referenceProcessFilePath = "C:\\jBPM\\jbpm-installer-7.3.0.Final\\workspace\\BPPLE\\src\\main\\resources\\process\\StudentEnrolmentReference.bpmn2";
		String attributeName = "name";
		String contextInfoXMLFilePath = "C:\\jBPM\\jbpm-installer-7.3.0.Final\\workspace\\BPPLE\\src\\main\\resources\\data\\ContextInfo.xml";
		String variantFilePath = "C:\\jBPM\\jbpm-installer-7.3.0.Final\\workspace\\BPPLE\\src\\main\\resources\\process\\StudentEnrolmentVariant.bpmn2";
		Document referenceDoc = XMLDOMHelper.getDocument(referenceProcessFilePath);
		String xPathexp = "/definitions/process";
		String xPathexp2 = "/definitions/process/subProcess";
		String tagNameSubProcess = "bpmn2:subProcess";
		String tagName = "bpmn2:process"; 
		NodeList listOfSubProcesses = XMLDOMHelper.getNodeListByTagName(referenceDoc, xPathexp2);
		NodeList fragNodes = null;
		NodeList fragDiNodes = null;
		String diXPathexp = "/definitions/BPMNDiagram/BPMNPlane";
		String diTagName = "bpmndi:BPMNPlane"; 
		
		System.out.println(listOfSubProcesses.getLength());
		
		for (int i = 0; i < listOfSubProcesses.getLength(); i++)
        {
            currentItem = listOfSubProcesses.item(i);
            String attributeValue = currentItem.getAttributes().getNamedItem(attributeName).getNodeValue();
            System.out.println("the name of this subprocess: " + attributeValue);
            String fragFilePath = BusinessRulesCaller.getFragFilePath(attributeValue.trim(), contextInfoXMLFilePath); //invoke business rules to get file path of fragment
            if (fragFilePath != null && !fragFilePath.isEmpty()) {
            	Document fragDoc = XMLDOMHelper.getDocument(fragFilePath);
        		fragNodes = XMLDOMHelper.extractNodeListOfChildren(fragDoc, xPathexp, tagName); //from fragment bpmn
        		fragDiNodes = XMLDOMHelper.extractNodeListOfChildren(fragDoc, diXPathexp, diTagName); //from fragment BPMNDiagram
        		if(fragNodes.getLength() == 0) {
        			System.out.println("NO NODELIST TO ADD, IT IS NULL");
        		}
        		
        		XMLDOMHelper.addNodeListToNodeByAttributeValue(referenceDoc, xPathexp2, tagNameSubProcess, attributeName, attributeValue, fragNodes);
        		XMLDOMHelper.addNodeListToFirstNode(referenceDoc, diXPathexp, diTagName, fragDiNodes);
            	
            }
                
        }
			
		//save variant
		// Get the process element by tag name directly
		Node processNode = referenceDoc.getElementsByTagName(tagName).item(0);
		
		NamedNodeMap attr = processNode.getAttributes();
		Node nodeAttr = attr.getNamedItem("id");
		String processID = nodeAttr.getTextContent();
		processID = processID + "Variant";
		
		// update id attribute
		nodeAttr.setTextContent(processID);
		
		// Get the BPMNPlane element by tag name directly
		Node diProcessNode = referenceDoc.getElementsByTagName(diTagName).item(0);
		
		NamedNodeMap diAttr = diProcessNode.getAttributes();
		Node diNodeAttr = diAttr.getNamedItem("bpmnElement");
		
		// update bpmnElement attribute
		diNodeAttr.setTextContent(processID);

		XMLDOMHelper.saveXMLContent(referenceDoc, variantFilePath);
	}
}
