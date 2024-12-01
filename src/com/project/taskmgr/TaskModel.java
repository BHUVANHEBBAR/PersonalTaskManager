package com.project.taskmgr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskModel{

	public boolean checkCatergoryExists(String c) {
		return new File(c+".todo").exists();
	}
	
	
	public List<TaskBean> getTasks(String catName) {
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line;
		try {
			br= new BufferedReader(new FileReader(catName));
			TaskBean task;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			while((line=br.readLine())!=null) {
				String[] eachTask = line.split(":");
				Date d=null;
				try {
					d = sdf.parse(eachTask[3]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				task=new TaskBean(eachTask[0],eachTask[1],eachTask[2],d,Integer.parseInt(eachTask[4]));
				tasks.add(task);
			}
			return tasks;
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(br!=null) {
					br.close();
				}
			}
			catch (Exception e) {
				// TODO: handle 
				e.printStackTrace();
				return null;
			}
		}
	}
	
	
	
	public String addTask(TaskBean task,String catName) {
		BufferedWriter bw = null;
		Date curDT = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String curD=sdf.format(curDT);
		try {
			bw = new BufferedWriter(new FileWriter(catName+".todo",true));
			bw.write(task.getTaskName()+":"+task.getDesc()+":"+task.getTags()+":"+task.getExpDt()+":"+task.getPriority()+":"+curD);
			bw.newLine();
			
			return Constants.SUCCESS;
		}catch(IOException e) {
			
			e.printStackTrace();
			return "Task addition was unsuccessfull..."+e.getMessage();
		}finally {
			if(bw!=null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//Load all categorys and tasks
	
	public void loadAll() {
		List<String> allCatFiles = listAll("D:");
		System.out.println(allCatFiles);
		for(String eachCat:allCatFiles) {
			List<TaskBean> ls = getTasks(eachCat);
			System.out.println("------Tasks of "+eachCat+" -------");
			for(TaskBean task:ls) {
				
				System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
			}
		}
	}
	
	//Delete Model
	public String deleteCat(String catDel) {
		if(new File(catDel).exists()) {
			File f = null;
			try {
				f=new File(catDel);
				if(f.delete()) {
					return Constants.SUCCESS;
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return Constants.FAILURE;
	}
	
	//List all Categorys
	public List<String> listAll(String p){
		File f=null;
		List<String> files = new ArrayList<>();
		try {
			f=new File(p);
			File[] fa = f.listFiles();
			for(File x:fa) {
				if(x.getAbsolutePath().endsWith(".todo")) {
					files.add(x.getName());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return files;
	}
	//Search for category
	public String search(String c) {
		List<String> allFiles = new ArrayList<>();
		try {
			allFiles = listAll("D:");
			for(String f:allFiles) {
				if(f.endsWith(c+".todo")) {
					return "Category Exists";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Oh no! Category you searched for doesn't exist!!";
	}
}
