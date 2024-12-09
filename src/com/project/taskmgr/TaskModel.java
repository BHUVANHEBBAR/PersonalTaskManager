package com.project.taskmgr;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



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
		String exptdDt = sdf.format(task.getExpDt());
		try {
			bw = new BufferedWriter(new FileWriter(catName+".todo",true));
			bw.write(task.getTaskName()+":"+task.getDesc()+":"+task.getTags()+":"+exptdDt+":"+task.getPriority()+":"+curD);
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
	//ALL UPDATE LOGICS
	
	public boolean updateTaskName(String categoryName, String currentTaskName, String newTaskName) {
		List<TaskBean> tasks = getTasks(categoryName + ".todo");
	    if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found in category: " + categoryName);
	        return false;
	    }

	    boolean taskUpdated = false;

	    // Find and update the task
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(currentTaskName)) {
	            task.setTaskName(newTaskName);
	            taskUpdated = true;
	            break;
	        }
	    }

	    // Save the updated tasks back to the file
	    return taskUpdated && saveChanges(categoryName, tasks);
	}

	public boolean updateTaskDescription(String categoryName, String taskName, String newDescription) {
		List<TaskBean> tasks = getTasks(categoryName + ".todo");
	    if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found in category: " + categoryName);
	        return false;
	    }

	    boolean taskUpdated = false;

	    // Find and update the task
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            task.setDesc(newDescription);
	            taskUpdated = true;
	            break;
	        }
	    }

	    // Save the updated tasks back to the file
	    return taskUpdated && saveChanges(categoryName, tasks);
	}

	public boolean updateTaskTags(String categoryName, String taskName, String newTags) {
		List<TaskBean> tasks = getTasks(categoryName + ".todo");
	    if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found in category: " + categoryName);
	        return false;
	    }

	    boolean taskUpdated = false;

	    // Find and update the task
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            task.setTags(newTags);
	            taskUpdated = true;
	            break;
	        }
	    }

	    // Save the updated tasks back to the file
	    return taskUpdated && saveChanges(categoryName, tasks);
	}

	public boolean updateTaskExpectedDate(String categoryName, String taskName, Date newDate) {
		List<TaskBean> tasks = getTasks(categoryName + ".todo");
	    if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found in category: " + categoryName);
	        return false;
	    }

	    boolean taskUpdated = false;

	    // Find and update the task
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            task.setExpDt(newDate);
	            taskUpdated = true;
	            break;
	        }
	    }

	    // Save the updated tasks back to the file
	    return taskUpdated && saveChanges(categoryName, tasks);
	}

	public boolean updateTaskPriority(String categoryName, String taskName, int newPriority) {
		List<TaskBean> tasks = getTasks(categoryName + ".todo");
	    if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found in category: " + categoryName);
	        return false;
	    }

	    boolean taskUpdated = false;

	    // Find and update the task
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            task.setPriority(newPriority);
	            taskUpdated = true;
	            break;
	        }
	    }

	    // Save the updated tasks back to the file
	    return taskUpdated && saveChanges(categoryName, tasks);
	}

	// Helper method to find a task in a category
	public TaskBean findTask(String categoryName, String taskName) {
		categoryName+=".todo";
	    List<TaskBean> tasks = getTasks(categoryName);
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            return task;
	        }
	    }
	    return null;
	}

	// Helper method to save category tasks back to file
	private boolean saveChanges(String categoryName,List<TaskBean> tasks) {
	    // Logic to save the updated list of tasks to the file
		
			
		BufferedWriter bw = null;
		try {
			bw=new BufferedWriter(new FileWriter(categoryName+".todo"));
			
			for(TaskBean task : tasks) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	             String expDate = sdf.format(task.getExpDt());
				bw.write(task.getTaskName()+":"+
						task.getDesc()+":"+
						task.getTags()+":"+
						expDate+":"+
						task.getPriority()
						);
				bw.newLine();
			}
			
//			System.out.println("Task Overwritten Successfully");
			return true;
		}catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    // Return true if successful, false otherwise		
		return false;
	}

	
	//Remove a single task
	
	public boolean removeTask(String catName,String taskName) {
		List<TaskBean> tasks = getTasks(catName+".todo");
		if (tasks == null || tasks.isEmpty()) {
	        System.out.println("No tasks found or unable to read file.");
	        return false;
	    }
		boolean taskFound = false;
	    List<TaskBean> updatedTasks = new ArrayList<>();
	    for (TaskBean task : tasks) {
	        if (task.getTaskName().equalsIgnoreCase(taskName)) {
	            taskFound = true;
	        } else {
	            updatedTasks.add(task);
	        }
	    }

	    if (!taskFound) {
	        System.out.println("Task not found: " + taskName);
	        return false;
	    }
	    
	    BufferedWriter bw =null;
	    try {
	    	
	    	bw = new BufferedWriter(new FileWriter(catName+".todo"));
	    	for(TaskBean task:updatedTasks) {
	    		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	             String expDate = sdf.format(task.getExpDt());
	             bw.write(task.getTaskName()+":"+
	            		 task.getDesc()+":"+
	            		 task.getTags()+":"+
	            		 expDate+":"+
	            		 task.getPriority()
	            		 );
	             bw.newLine();
	    	}
	    	return true;
	    }catch(IOException e) {
	    
	    	e.printStackTrace();
	    	return false;
	    }
	    finally {
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
	
	
	
	
	
	public String exportAsPdf() {
		Document document = new Document();

        try {
            // Create a PdfWriter instance to write to the output file
            PdfWriter.getInstance(document, new FileOutputStream(Constants.PATH_CURRENT_DIR+Constants.PDF_NAME));

            // Open the document for writing
            document.open();

            // Get the list of all categories
            List<String> allCategories = listAll("D:"); // Adjust path as needed

            if (allCategories.isEmpty()) {
                return "No categories found to export.";
            }

            for (String category : allCategories) {
                // Add category name as a header
                document.add(new Paragraph("Category: " + category));
                document.add(new Paragraph("------------------------------------------------------"));

                // Fetch tasks for the category
                List<TaskBean> tasks = getTasks(category);

                if (tasks == null || tasks.isEmpty()) {
                    document.add(new Paragraph("No tasks found for this category."));
                } else {
                    for (TaskBean task : tasks) {
                        // Add task details
                        String taskDetails = String.format(
                            "Task Name: %s\nDescription: %s\nTags: %s\nPriority: %d\nExpected Date: %s\n\n",
                            task.getTaskName(), task.getDesc(), task.getTags(),
                            task.getPriority(), task.getExpDt()
                        );
                        document.add(new Paragraph(taskDetails));
                    }
                }

                // Add a blank line after each category
                document.add(new Paragraph("\n"));
            }

            // Successfully exported
            return "PDF export successful: " + Constants.PATH_CURRENT_DIR;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error during PDF export: " + e.getMessage();
        } finally {
            // Close the document
            if (document.isOpen()) {
                document.close();
            }
        }
	}
	public String exportAsExcel() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("TaskManager");
		
		
		Row headerRow = sheet.createRow(0);
		sheet.setColumnWidth(0, 4000); // Category
        sheet.setColumnWidth(1, 4000); // Task Name
        sheet.setColumnWidth(2, 6000); // Description
        sheet.setColumnWidth(3, 4000); // Tags
        sheet.setColumnWidth(4, 3000); // Priority
        sheet.setColumnWidth(5, 6000); // Expected Date
        
        headerRow.createCell(0).setCellValue("Category");
        headerRow.createCell(1).setCellValue("Task Name");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Tags");
        headerRow.createCell(4).setCellValue("Priority");
        headerRow.createCell(5).setCellValue("Expected Date");

        List<String> allCategories = listAll("D:");
        if(allCategories==null || allCategories.isEmpty()) {
        	try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return "No categories found to export";
        }
        
        int rowNum=1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        
        try {
        	for(String category : allCategories) {
        		String catName=category.replace(".todo","");
        		
        		List<TaskBean> tasks = getTasks(category);
        		
        		if(tasks==null || tasks.isEmpty()) {
        			Row row = sheet.createRow(rowNum++);
        			row.createCell(0).setCellValue(catName);
        			row.createCell(1).setCellValue("No tasks found");
        		}
        		else {
        			
        			for(TaskBean task:tasks) {
        				
        				Row row = sheet.createRow(rowNum++);
        				row.createCell(0).setCellValue(catName);  // Category Name
                        row.createCell(1).setCellValue(task.getTaskName());  // Task Name
                        row.createCell(2).setCellValue(task.getDesc());  // Description
                        row.createCell(3).setCellValue(task.getTags());  // Tags
                        row.createCell(4).setCellValue(task.getPriority());  // Priority
                        row.createCell(5).setCellValue(sdf.format(task.getExpDt()));  //Date
        				
        			}
        		}
        		
        	}
        	FileOutputStream fileOut =null;
        	try {
        		fileOut=new FileOutputStream(Constants.EXCEL_PATH);
        		workbook.write(fileOut);
        		
        		return "Excel Export successfull "+Constants.EXCEL_PATH;
        	}
        	
        	catch(IOException e) {
        		e.printStackTrace();
        		return "Error during exporting Excel "+e.getMessage();
        	}
        	finally {
        		workbook.close();
        		fileOut.close();
        	}
        }catch(IOException e) {
        	e.printStackTrace();
        }
        return Constants.SUCCESS;
	}
	}