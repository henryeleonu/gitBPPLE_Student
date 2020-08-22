package com.sample;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.testng.Assert;
import org.w3c.dom.Document;

/**
 * This is a sample file to launch a process.
 */
public class ProcessTest {

    public static final void main(String[] args) {
        try {
        	// load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-process");
        	
        	kSession.getWorkItemManager().registerWorkItemHandler(
        			  "PauseNode", 
        			  new ContinueWorkItemHandler() 
        			);
        	kSession.getWorkItemManager().registerWorkItemHandler(
      			  "BindFragmentModel", 
      			  new BindFragmentModelWorkItemHandler() 
      			);
        	
        	Map<String, Object> params = new HashMap<String, Object>();
        	String contextInfoPath = "C:\\jBPM\\jbpm-installer-7.3.0.Final\\workspace\\BPPLE\\src\\main\\resources\\data\\ContextInfo.xml";
        	Document contextInfoDoc = XMLDOMHelper.getDocument(contextInfoPath);
    		ContextInfo contextInfo = new ContextInfo();
    		contextInfo.setContextInfoDoc(contextInfoDoc);
            params.put("contextInfo", contextInfo);
            
            // start a new process instance
            ProcessInstance processInstance = kSession.startProcess("defaultPackage.StudentEnrolmentReferenceVariant", params);
            kSession.fireAllRules();
            
            //Long pid = processInstance.getId();
            //System.out.println("processInstanceId = "+ pid);
            
            RuleFlowProcessInstance rfpi = (RuleFlowProcessInstance)processInstance;
            Object variable = rfpi.getVariable("messageFrag");
            String actualMessage = (String) variable;
            System.out.println("message from fragment ==" + actualMessage);
            //assertProcessInstanceCompleted(processInstance.getId(), kSession);
            String expectedMessage = "Executing Self-Financing";
            //Assert.assertEquals(actualMessage, expectedMessage);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
