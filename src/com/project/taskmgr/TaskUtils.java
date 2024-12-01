package com.project.taskmgr;

public class TaskUtils {

	//Requirement : name must be only alphanumeric - cannot start with number or special char
	public static boolean validateName(String n) {
		if(n==null)
			return false;
		if(n.trim().equals(" "))
			return false;
		if(!Character.isLetter(n.charAt(0)))
			return false;
		
		if(n.split(" ").length > 1)
			return false;
		
		for(int i=1;i<n.length();i++) {
			char c=n.charAt(i);
			
			if(!(Character.isLetter(c) || Character.isDigit(c)))
				return false;
		}
		return true; 
	}
	
}
