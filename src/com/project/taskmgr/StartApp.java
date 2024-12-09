package com.project.taskmgr;

import java.io.IOException;
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
	Scanner s1 = null,s2=null;
	
	//Logger connecting
	try {
	Logger logger=Logger.getInstance();
//	System.out.println("Logger at"+Constants.PATH_CURRENT_DIR);
	logger.log("Logger created..");
	
	System.out.println("------Welcome to Personal Task Manager------");
	 s1 = new Scanner(System.in);
	 s2 = new Scanner(System.in);
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
				"Press 4 to List all category\n"+
				"Press 5 to Search for a category\n"+
				"Press 6 to Export\n"+
				"Press 7 to Exit");
		logger.log("Category menu viewed");
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
				logger.log("Task menu in Create Category viewed");
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
//							System.out.println(dt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logger.log(e.getMessage());
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
						//Task updating view and control
						/*
						 * Update can be done by replacing required part of task.
						 * Switch case to select part to be updated.
						 * */
						int upTaskCh=0;
						String curTask;
						System.out.println("Enter task you wish to update");
						curTask= s2.nextLine();
						while(upTaskCh!=6) {
						System.out.println("-------Updating Task Menu-------");
						System.out.println("Press 1 to Update Task Name\n"+
						"Press 2 to Update Description\n"+
								"Press 3 to Update Tags\n"+
						"Press 4 to Update Expected Completion Date\n"+
								"Press 5 to Update Priority\n"+
						"Press 6 to Go Back");
						upTaskCh=s1.nextInt();
						switch(upTaskCh) {
						case 1: // Update Task Name
		                    System.out.println("Enter new Task Name:");
		                    String newTaskName = s2.nextLine();
		                    if (tm.updateTaskName(catName, curTask, newTaskName)) {
		                        curTask = newTaskName;
		                        System.out.println("Task name updated successfully!");
		                        logger.log("Taskname updated "+curTask);
		                    } else {
		                        System.out.println("Failed to update Task Name.");
		                        logger.log("Taskname update failed. "+curTask);
		                    }
		                    break;

		                case 2: // Update Description
		                    System.out.println("Enter new Description:");
		                    String newDesc = s2.nextLine();
		                    if (tm.updateTaskDescription(catName, curTask, newDesc)) {
		                        System.out.println("Description updated successfully!");
		                        logger.log("Description updated "+curTask);
		                    } else {
		                        System.out.println("Failed to update Description.");
		                        logger.log("Description update failed "+curTask);
		                    }
		                    break;

		                case 3: // Update Tags
		                    System.out.println("Enter new Tags (comma-separated):");
		                    String newTags = s2.nextLine();
		                    if (tm.updateTaskTags(catName, curTask, newTags)) {
		                        System.out.println("Tags updated successfully!");
		                        logger.log("Tags updated "+curTask);
		                    } else {
		                        System.out.println("Failed to update Tags.");
		                        logger.log("Tags updated "+curTask);
		                    }
		                    break;

		                case 4: // Update Expected Completion Date
		                    System.out.println("Enter new Expected Completion Date (dd/MM/yyyy):");
		                    String newDate = s2.nextLine();
		                    try {
		                        Date newExpDate = sdf.parse(newDate);
		                        if (tm.updateTaskExpectedDate(catName, curTask, newExpDate)) {
		                            System.out.println("Expected Completion Date updated successfully!");
		                            logger.log("Date updated "+curTask);
		                        } else {
		                            System.out.println("Failed to update Expected Completion Date.");
		                            logger.log("Date update failed "+curTask);
		                        }
		                    } catch (ParseException e) {
		                        System.out.println("Invalid date format. Please try again.");
		                    }
		                    break;

		                case 5: // Update Priority
		                    System.out.println("Enter new Priority (1-10):");
		                    int newPriority = s1.nextInt();
		                    if (tm.updateTaskPriority(catName, curTask, newPriority)) {
		                        System.out.println("Priority updated successfully!");
		                        logger.log("Priority updated "+curTask);
		                    } else {
		                        System.out.println("Failed to update Priority.");
		                        logger.log("Priority update failed "+curTask);
		                    }
		                    break;

		                case 6: // Go Back
		                    System.out.println("Exiting Task Update Menu...");
		                    break;

		                default:
		                    System.out.println("Invalid option. Please try again.");
		            }
						}
						break;
						//end of update tasks
					case 3:
						//removing task
						System.out.println("-----Removing a task-------");
						System.out.println("Enter category name ");
						String remCat = s2.nextLine();
						if(TaskUtils.validateName(remCat)&& tm.checkCatergoryExists(remCat)) {
							System.out.println("Enter task name to remove (not case sensitive)");
							String remTask = s2.nextLine();
							if(tm.removeTask(remCat, remTask)) {
								System.out.println("Task removed successfully...");
							}
							else {
								System.out.println("Oh no...Error in removing task");
							}
						}
						break;
					
					case 4:
						//list all tasks
						System.out.println("Enter category name to list all tasks");
						String catList = s2.nextLine();
						List<TaskBean> ls = tm.getTasks(catList+".todo");
						for(TaskBean task:ls) {
							System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
						}
						
						break;
					
					case 5:
//						search task
						System.out.println("Enter category to search in..");
						String catSearch=s2.nextLine();
						if(TaskUtils.validateName(catSearch)&& tm.checkCatergoryExists(catSearch)) {
						
						
						System.out.println("Enter task name to search..");
						String searchFor = s2.nextLine();
						TaskBean foundTask=null;
						if((foundTask= tm.findTask(catSearch,searchFor))!=null ) {
							System.out.println("Task found!!");
							System.out.println("Task Name: "+foundTask.getTaskName()+" Task Desc: "+foundTask.getDesc()+" Task Tags: "+foundTask.getTags()+" Task Priority: "+foundTask.getPriority());
							
						}
						}
						else {
							System.out.println("Category doesn't exist retry!!");
						}
						break;
					
					case 6:
						//go back
						System.out.println("Back to main menu...");
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
			logger.log("Category "+catLoad+ "file is loaded");
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
				logger.log("Task Menu in Load Category viewed");

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
//						System.out.println(dt);
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
					//Update a task.
					//Task updating view and control
					/*
					 * Update can be done by replacing required part of task.
					 * Switch case to select part to be updated.
					 * */
					int upTaskCh=0;
					String curTask;
					System.out.println("Enter task you wish to update");
					curTask= s2.nextLine();
					if(tm.findTask(catLoad,curTask)!=null)
					{while(upTaskCh!=6) {
					System.out.println("-------Updating Task Menu-------");
					System.out.println("Press 1 to Update Task Name\n"+
					"Press 2 to Update Description\n"+
							"Press 3 to Update Tags\n"+
					"Press 4 to Update Expected Completion Date\n"+
							"Press 5 to Update Priority\n"+
					"Press 6 to Go Back");
					upTaskCh=s1.nextInt();
				
					switch(upTaskCh) {
					case 1: // Update Task Name
	                    System.out.println("Enter new Task Name:");
	                    String newTaskName = s2.nextLine();
	                    if (tm.updateTaskName(catLoad, curTask, newTaskName)) {
	                        curTask = newTaskName;
	                        System.out.println("Task name updated successfully!");
	                        logger.log("Taskname updated "+curTask);
	                    } else {
	                        System.out.println("Failed to update Task Name.");
	                        logger.log("Taskname updated "+curTask);
	                    }
	                    break;

	                case 2: // Update Description
	                    System.out.println("Enter new Description:");
	                    String newDesc = s2.nextLine();
	                    if (tm.updateTaskDescription(catLoad, curTask, newDesc)) {
	                        System.out.println("Description updated successfully!");
	                        logger.log("Description updated "+curTask);
	                    } else {
	                        System.out.println("Failed to update Description.");
	                        logger.log("Description update failed "+curTask);
	                    }
	                    break;

	                case 3: // Update Tags
	                    System.out.println("Enter new Tags (comma-separated):");
	                    String newTags = s2.nextLine();
	                    if (tm.updateTaskTags(catLoad, curTask, newTags)) {
	                        System.out.println("Tags updated successfully!");
	                        logger.log("Tags Updated "+curTask);
	                    } else {
	                        System.out.println("Failed to update Tags.");
	                        logger.log("Failed to update tags "+curTask);
	                    }
	                    break;

	                case 4: // Update Expected Completion Date
	                    System.out.println("Enter new Expected Completion Date (dd/MM/yyyy):");
	                    String newDate = s2.nextLine();
	                    try {
	                        Date newExpDate = sdf.parse(newDate);
	                        if (tm.updateTaskExpectedDate(catLoad, curTask, newExpDate)) {
	                            System.out.println("Expected Completion Date updated successfully!");
	                            logger.log("Date updated "+curTask);
	                        } else {
	                            System.out.println("Failed to update Expected Completion Date.");
	                            logger.log("Failed to update date "+curTask);
	                        }
	                    } catch (ParseException e) {
	                        System.out.println("Invalid date format. Please try again.");
	                    }
	                    break;

	                case 5: // Update Priority
	                    System.out.println("Enter new Priority (1-10):");
	                    int newPriority = s1.nextInt();
	                    if (tm.updateTaskPriority(catLoad, curTask, newPriority)) {
	                        System.out.println("Priority updated successfully!");
	                        logger.log("Priority updated "+curTask);
	                    } else {
	                        System.out.println("Failed to update Priority.");
	                        logger.log("Priority update failed "+curTask);
	                    }
	                    break;

	                case 6: // Go Back
	                    System.out.println("Exiting Task Update Menu...");
	                    break;

	                default:
	                    System.out.println("Invalid option. Please try again.");
	            }
					}
					}
					else {
						System.out.println("Task not found..");
					}

					
					break;
					
				case 3:
					//removing task
					System.out.println("-----Removing a task-------");
					System.out.println("Enter category name ");
					String remCat = s2.nextLine();
					if(TaskUtils.validateName(remCat)&& tm.checkCatergoryExists(remCat)) {
						System.out.println("Enter task name to remove (not case sensitive)");
						String remTask = s2.nextLine();
						if(tm.removeTask(remCat, remTask)) {
							System.out.println("Task removed successfully...");
							logger.log("Removed task "+remTask);
						}
						else {
							System.out.println("Oh no...Error in removing task");
						}
					}
					break;
				
				case 4:
					System.out.println("All tasks in Category "+catLoad);
					List<TaskBean> tasks = tm.getTasks(catLoad+".todo");
					for(TaskBean task:tasks) {
						System.out.println("Task Name: "+task.getTaskName()+" Task Desc: "+task.getDesc()+" Task Tags: "+task.getTags()+" Task Priority: "+task.getPriority());
					}
					
					break;
				
				case 5:
//					search task
					System.out.println("Enter category to search in..");
					String catSearch=s2.nextLine();
					if(TaskUtils.validateName(catSearch)&& tm.checkCatergoryExists(catSearch)) {
					
					
					System.out.println("Enter task name to search..");
					String searchFor = s2.nextLine();
					TaskBean foundTask=tm.findTask(catSearch, searchFor);;
					if(foundTask!=null) {
						System.out.println("Task found!!");
						System.out.println("Task Name: "+foundTask.getTaskName()+" Task Desc: "+foundTask.getDesc()+" Task Tags: "+foundTask.getTags()+" Task Priority: "+foundTask.getPriority());
						
					}
					}
					else {
						System.out.println("Category doesn't exist retry!!");
					}
					break;
				
				case 6:
					//Back to Category Menu
					System.out.println("Back to category menu");
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
			logger.log("Requested Category "+catDel+" deleted");
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
			logger.log("All category files listed");
			break;
		case 5:
			System.out.println("Search for a category..");
			String cat = s2.nextLine();
			System.out.println("Searching....");
			System.out.println( tm.search(cat) );
			
			break;
		case 6:
			//Exporting it into Excel and PDF files
			System.out.println(tm.exportAsPdf());
			System.out.println(tm.exportAsExcel());
			logger.log("File export successfully done");
			break;
		case 7:
			System.out.println("Task Manager closed!!!");
			logger.log("End of access to task manager\n -----------------------------------------------------------------");
			logger.log("");
			break;
		default:
			System.out.println("Please recheck your choices....");
		}
		
	}
}
	catch(Exception e) {
		System.out.println("Logging error");
		e.printStackTrace();
	}
	finally {
		s1.close();
		s2.close();
	}
}

}
