package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.w3c.dom.Document;


public class BusinessRulesCaller {

	public BusinessRulesCaller() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getFragFilePath(String attributeValue, String contextInfoPath) {
		// TODO Auto-generated method stub
		Document contextInfoDoc = XMLDOMHelper.getDocument(contextInfoPath);
		ContextInfo contextInfo = null;
		 try {
	            // load up the knowledge base
		        KieServices ks = KieServices.Factory.get();
	    	    KieContainer kContainer = ks.getKieClasspathContainer();
	        	KieSession kSession = kContainer.newKieSession("ksession-rules");
	        	contextInfo = new ContextInfo();
	    		contextInfo.setAbstractContainerAttrValue(attributeValue);
	    		contextInfo.setContextInfoDoc(contextInfoDoc);
	    		XMLDOMHelper xmlDOMHelper = new XMLDOMHelper();
	        	kSession.getAgenda().getAgendaGroup("Group1").setFocus(); 
	            kSession.insert(contextInfo);
	            kSession.insert(xmlDOMHelper);
	            kSession.fireAllRules();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
		System.out.println("the file path is " + contextInfo.getFragFilePath()); 
		return contextInfo.getFragFilePath();
	}
}
