package com.sample;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class User extends Thread{

	public JFrame frame;
	JButton btnNewButton;
	private Thread t;
	private String threadName;
	Object lock;
	
	public User(String name) {
		threadName = name;
	      System.out.println("Creating " +  threadName );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnNewButton = new JButton("Resume Execution");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synchronized(lock){					
						lock.notify();
				}
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.CENTER);
	}
	
	@Override
	public void run(){
		initialize();
		frame.setVisible(true);
	}
	
	@Override
	public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}
