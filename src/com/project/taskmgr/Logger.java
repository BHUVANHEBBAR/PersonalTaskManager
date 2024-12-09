package com.project.taskmgr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {

	File p = new File(System.getProperty("user.dir")+"\\TaskManagerLog.log");
	
	private static Logger lg;
	private Logger() {
		// TODO Auto-generated constructor stub
	}
	public static Logger getInstance() {
		if(lg==null) {
			lg=new Logger();
		}
		return lg;
	}
	
	
	
	
	
	public void log(final String data) {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedWriter w =null;
				try {
					w=new BufferedWriter(new FileWriter(p,true));
					w.write(data+"\n");
					w.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally {
					try {
						w.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}.start();
			
		}
}
