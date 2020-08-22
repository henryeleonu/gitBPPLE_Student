package com.sample;

import java.util.HashMap;
import java.util.Map;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class BindFragmentModelWorkItemHandler implements WorkItemHandler{

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		System.out.println("Aborting");
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
		Object ci = workItem.getParameter("ContextInformation");
		Object fragId = workItem.getParameter("FragmentIdentity");
		System.out.println("This is the ID of the fragment to be invoked: " + (String)fragId);
		StartProcess start = new StartProcess();
		String messageFromFragment = start.startProcess(ci, fragId);
		
		System.out.println("This is the message from the fragment invoked: " + messageFromFragment);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("result", messageFromFragment);
		
		//notify manager that workItem has completed
	     manager.completeWorkItem(workItem.getId(), results);
	    
	}

}
