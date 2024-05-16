package com.cthtc.office.entity;

import java.util.Date;

import com.cthtc.office.model.TaskStatus;

public class TaskDTO {

	private String title;
	
	private String description;
	
	private String priority;
	
	private Date dueDate;
	
	private TaskStatus taskStatus;
	
	private Long guestID;
	
	private String guestName;

	public TaskDTO(String title, String description, String priority, Date dueDate, TaskStatus taskStatus,
			Long guestID, String guestName) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
		this.taskStatus = taskStatus;
		this.guestID = guestID;
		this.guestName = guestName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getGuestID() {
		return guestID;
	}

	public void setGuestID(Long guestID) {
		this.guestID = guestID;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	@Override
	public String toString() {
		return "TaskDTO [title=" + title + ", description=" + description + ", priority=" + priority + ", dueDate="
				+ dueDate + ", taskStatus=" + taskStatus + ", guestID=" + guestID + ", guestName=" + guestName + "]";
	}
	
	
	
	
	

	
	
	

	
	
	

}
