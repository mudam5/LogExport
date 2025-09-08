package com.cisco.LogsMonitoringApp.LogExport.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime timestamp;

	private String message;
	
	private String level;

	public LogEntity() {}

	public LogEntity(String message, String level) {
	        this.timestamp = LocalDateTime.now();
	        this.message = message;
	        this.level= level;
	    }

	// getters and setters
	public Long getId() {
		return id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "LogEntity [id=" + id + ", timestamp=" + timestamp + ", message=" + message + ", level=" + level + "]";
	}
	
}

