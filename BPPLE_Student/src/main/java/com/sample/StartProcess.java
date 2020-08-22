package com.sample;

import java.util.HashMap;
import java.util.Map;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

public class StartProcess {

	public StartProcess() {
		// TODO Auto-generated constructor stub
	}
	
	public String startProcess(Object contextInfo, Object fragmentID){
		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	KieSession kSession = kContainer.newKieSession("ksession-fragment");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("contextInfo", contextInfo);
    	String fragId = (String) fragmentID;
    	//params.put("message", );
    	ProcessInstance processInstance = kSession.startProcess(fragId, params);
        kSession.fireAllRules();
        RuleFlowProcessInstance rfpi = (RuleFlowProcessInstance)processInstance;
        Object variable = rfpi.getVariable("message");
        String message = (String) variable;
		return message;
	}
}
