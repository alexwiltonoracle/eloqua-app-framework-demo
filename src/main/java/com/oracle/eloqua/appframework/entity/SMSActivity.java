package com.oracle.eloqua.appframework.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.oracle.eloqua.appframework.enums.RecordStatus;

@Entity
public class SMSActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String email;

	@Enumerated(EnumType.STRING)
	private RecordStatus status;
	private String instanceId;

	public SMSActivity() {
	}

	@Override
	public String toString() {
		return String.format("CampaignEntry [instanceId=%s, email=%s, status=%s]", instanceId, email, status);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public RecordStatus getStatus() {
		return status;
	}

	public void setStatus(RecordStatus status) {
		this.status = status;
	}

}