package com.sample;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class ContinueWorkItemHandler implements WorkItemHandler{
	
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		 System.out.println("Executing Awesome handler");
		 final Object lock = new Object();
		 
		 //Get user permission
		 User user = new User("Thread-1");
		 user.lock = lock;
		 user.start();
		 
		 synchronized(lock){
			 try {
				 System.out.println("Waiting for permision of user...");
				 lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 //notify manager that workItem has completed
	     manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// TODO Auto-generated method stub
		System.out.println("Aborting");
	}
	
}
