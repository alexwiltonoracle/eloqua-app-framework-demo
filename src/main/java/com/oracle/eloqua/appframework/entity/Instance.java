package com.oracle.eloqua.appframework.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.enums.InstanceStatus;
import com.oracle.eloqua.appframework.enums.ServiceType;

@Entity
public class Instance {

	@Id
	private String instanceId;
	@Enumerated(EnumType.STRING)
	private ServiceType serviceType;
	@Enumerated(EnumType.STRING)
	private InstanceStatus instanceStatus;
	
	private String appId;
	private String installId;
	private int siteId;
	private String userName;
	

	public Instance() {
	}
	
	public Instance(ServiceType serviceType, InstanceStatus instanceStatus, WebHookParams params) {
		this.installId = params.getInstallId();
		this.instanceId = params.getInstanceId();
		this.appId = params.getAppId();
		this.siteId = params.getSiteId();
		this.userName = params.getUserName();
		this.serviceType = serviceType;
		this.instanceStatus = instanceStatus;
	}


	@Override
	public String toString() {
		return String.format("Instance [instanceId=%s, serviceType=%s, appId=%s, installId=%s, siteId=%s, userName=%s]",
				instanceId, serviceType, appId, installId, siteId, userName);
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInstallId() {
		return installId;
	}

	public void setInstallId(String installId) {
		this.installId = installId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public InstanceStatus getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(InstanceStatus instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

}