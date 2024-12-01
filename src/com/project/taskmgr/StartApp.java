package com.project.taskmgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StartApp {
public static void main(String[] args) {
	/*
	 * Create menu facing user 
	 * 
	 * Best Practice : USE MVC pattern
	 * 
	 * Main()-> is the view
	 * Choices -> create controls
	 * Models -> Apply logic on data and perform other operations
	 * 
	 * */
	System.out.println("------Welcome to Personal Task Manager------");
	Scanner s1 = new Scanner(System.in);
	Scanner s2 = new Scanner(System.in);
	TaskUtils tu = new TaskUtils();
	TaskModel tm = new TaskModel();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String taskName,desc,tags,expDt;int priority=0;
	//Choice variable
	int categoryChoice=0;int taskChoice=0;
	String catName;
	//show choices
	while(categoryChoice!=7) {
		System.out.println("------Category Menu------");
		System.out.println("Press 1 to Create a category\n"+
				"Press 2 to Load a category\n"+
				"Press 3 to Delete a category\n"+
				"Press 4 to List category\n"+
				"Press 5 to Search for a category\n"+
				"Press 6 to Export\n"+
				"Press 7 to Exit");
		
		categoryChoice=s1.nextInt();
		
		switch(categoryChoice) {
		case 1:
			System.out.println("------Creating Category------");
			System.out.println("Enter the name for category..");
			 catName=s2.nextLine();
			while(!TaskUtils.validateName(catName)) {
				System.out.println("Invalid format must start with aplhabet and no spaces are allowed...Try again...");
				catName=s2.nextLine();
			}
			//business validation : check if file with name already exists
			if(tm.checkCatergoryExists(catName+".todo")) {
				System.out.println("File already exits try updating it....");
			}
			
			else {
				while(taskChoice!=6) {
					System.out.println("------In Task Menu------");
					System.out.println("Press 1 to Create task");
					System.out.println("Press 2 to Update task");
					System.out.println("Press 3 to Remove task");
					System.out.println("Press 4 to List all tasks");
					System.out.println("Press 5 to Search task");
					System.out.println("Press 6 to Go Back");
					taskChoice=s1.nextInt();
					
					//Each task has : name,desc,tags,expected end date,priority
					switch(taskChoice) {
					
					case 1:
						System.out.println("Enter task name");
						taskName=s2.nextLine();
						System.out.println("Enter description");
						desc = s2.nextLine();
						System.out.println("Enter tags (comma seperated)");
						tags = s2.nextLine();
						System.out.println("Enter expected date of completion(dd/MM/yyyy)");
						expDt = s2.nextLine();
						Date dt=null;
						try {
							dt = sdf.parse(expDt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						while(priority<1 || priority>10) {
						System.out.println("Enter priority (1-very low, 10-critical)");
						priority=s1.nextInt();
						}
						//pass all this data to one bean
						TaskBean bean = new TaskBean(taskName,desc,tags,dt,priority);
						String result = tm.addTask(bean,catName);
						if(result.equals(Constants.SUCCESS)) {
							System.out.println("Task "+taskName+" added successfully");
						}
						else {
							System.out.println("Task addition failed. Msg is "+result);
						}
						break;
						
					case 2:
						
						break;
						
					case 3:
						break;
					
					case 4:
						System.out.println("Enter category name to list all tasks");
						String catList = s2.nextLine();
						List<TaskBean> ls = tm.getTasks(catName);
						for(TaskBean task:ls) {
							System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
						}
						
						break;
					
					case 5:
						break;
					
					case 6:
						break;
					default:
						System.out.println("Enter valid options...");
					
					}
				}
			}
			
			break;
			
		case 2: //Load a category
		
			System.out.println("------Load Category------");
			System.out.println("Enter name of category...");
			String catLoad= s2.nextLine();
			if(tm.checkCatergoryExists(catLoad)) {
			
			
			List<TaskBean> ls = tm.getTasks(catLoad+".todo");
			for(TaskBean task:ls) {
				System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
			}
			
			int chUp=0;
			//Give ability to make changes on this  category tasks
			while(chUp!=6) {
				System.out.println("------In Task Menu------");
				System.out.println("Press 1 to Create task");
				System.out.println("Press 2 to Update task");
				System.out.println("Press 3 to Remove task");
				System.out.println("Press 4 to List all tasks");
				System.out.println("Press 5 to Search task");
				System.out.println("Press 6 to Go Back");
				chUp=s1.nextInt();
				
				//Each task has : name,desc,tags,expected end date,priority
				switch(chUp) {
				
				case 1:
					System.out.println("Enter task name");
					taskName=s2.nextLine();
					System.out.println("Enter description");
					desc = s2.nextLine();
					System.out.println("Enter tags (comma seperated)");
					tags = s2.nextLine();
					System.out.println("Enter expected date of completion(dd/MM/yyyy)");
					expDt = s2.nextLine();
					Date dt=null;
					try {
						dt = sdf.parse(expDt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					while(priority<1 || priority>10) {
					System.out.println("Enter priority (1-very low, 10-critical)");
					priority=s1.nextInt();
					}
					//pass all this data to one bean
					TaskBean bean = new TaskBean(taskName,desc,tags,dt,priority);
					String result = tm.addTask(bean,catLoad);
					if(result.equals(Constants.SUCCESS)) {
						System.out.println("Task "+taskName+" added successfully");
					}
					else {
						System.out.println("Task addition failed. Msg is "+result);
					}
					break;
					
				case 2:
					
					break;
					
				case 3:
					break;
				
				case 4:
					System.out.println("All tasks in Category "+catLoad);
					List<TaskBean> tasks = tm.getTasks(catLoad+".todo");
					for(TaskBean task:tasks) {
						System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
					}
					
					break;
				
				case 5:
					break;
				
				case 6:
					break;
				default:
					System.out.println("Enter valid options...");
				
				}
			}
		
			
			
			}
			else {
				System.out.println("Requested Category doesn't exist..");
			}
			break;
			
			
		case 3:
			//delete 
			System.out.println("------Delete Category------");
			System.out.println("Enter the name of category..");
			String catDel= s2.nextLine();
			if(tm.checkCatergoryExists(catDel)) {
				String deleteOp=tm.deleteCat(catDel+".todo");
				if(deleteOp.equals(Constants.SUCCESS)) {
					System.out.println("Category "+catDel+" deleted successfully!!");
				}
				else {
					System.out.println("Category Deletion Failed!!");
				}
			}
			else {
				System.out.println("File doesn't exist to delete.");
			}
			
			break;
		case 4:
			System.out.println("Listing all Categories...");
			tm.loadAll();
			break;
		case 5:
			System.out.println("Search for a category..");
			String cat = s2.nextLine();
			System.out.println("Searching....");
			System.out.println( tm.search(cat) );
			
			break;
		case 6:
			break;
		case 7:
			System.out.println("Task Manager closed!!!");
			break;
		default:
			System.out.println("Please recheck your choices....");
		}
		
	}
}
}
