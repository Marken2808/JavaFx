package com.cthtc.office.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cthtc.office.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String priority;
	
	@Column
	private Date dueDate;
	
	@Column
	private TaskStatus taskStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonProperty
	private AccountEntity user;
	
	/*	Test API body
	{
	   "title":"DDD",
	   "description":"DDD",
	   "dueDate":"2024-05-16",
	   "priority":"HIGH",
	   "user": {
	       "id":2
	   }
	}
	*/
	public TaskEntity() {}

	public TaskEntity(String title, String description, String priority, Date dueDate, TaskStatus taskStatus, AccountEntity user) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
		this.taskStatus = taskStatus;
		this.user = user;
	}
	
//	public TaskDTO getTaskDTO() {
//		return new TaskDTO(this.title, this.description, this.priority, this.dueDate, this.taskStatus, this.user.getId(), this.user.getUsername());
//	}


	@Override
	public String toString() {
		return "TaskEntity [id=" + id + ", title=" + title + ", description=" + description + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", taskStatus=" + taskStatus + ", user=" + user + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public AccountEntity getUser() {
		return user;
	}

	public void setUser(AccountEntity user) {
		this.user = user;
	}
	
	
	
	
	
}
