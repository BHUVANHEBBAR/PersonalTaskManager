package com.project.taskmgr;

import java.util.Date;
import java.util.Objects;
public class TaskBean implements Comparable<TaskBean>{

	private String taskName;
	private String desc;
	private String tags;
	private Date expDt;
	private int priority;
	
	@Override
	public int compareTo(TaskBean o) {
		// TODO Auto-generated method stub
		return o.taskName.compareTo(this.taskName);
	}
	
	
	public TaskBean() {
		
	}
	

	public TaskBean(String taskName, String desc, String tags, Date expDt, int priority) {
		super();
		this.taskName = taskName;
		this.desc = desc;
		this.tags = tags;
		this.expDt = expDt;
		this.priority = priority;
	}


	@Override
	public String toString() {
		return "TaskBean [taskName=" + taskName + ", desc=" + desc + ", tags=" + tags + ", expDt=" + expDt
				+ ", priority=" + priority + "]";
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Date getExpDt() {
		return expDt;
	}
	public void setExpDt(Date expDt) {
		this.expDt = expDt;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
			this.priority = priority;
	}
	@Override
	public int hashCode() {
		return Objects.hash(desc, expDt, priority, tags, taskName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskBean other = (TaskBean) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(expDt, other.expDt) && priority == other.priority
				&& Objects.equals(tags, other.tags) && Objects.equals(taskName, other.taskName);
	}
	
}
