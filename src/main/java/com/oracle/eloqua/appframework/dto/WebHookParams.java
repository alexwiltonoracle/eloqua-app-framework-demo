package com.oracle.eloqua.appframework.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oracle.eloqua.appframework.util.Util;

public class WebHookParams {

	private static final Logger log = LoggerFactory.getLogger(WebHookParams.class);

	private String appId;
	private String assetId;
	private String assetName;
	private String assetType;
	private String customObjectId;
	private String entityType;
	private String eventType;
	private String executionId;
	private String installId;
	private String instanceId;
	private String isPreview;
	private String originalAssetId;
	private String originalAssetName;
	private String originalInstallId;
	private String originalInstanceId;
	private String renderType;
	private int siteId = -1;
	private String siteName;
	private String userCulture;
	private String userId;
	private String userName;
	private String visitorId;

	public WebHookParams() {

	}

	public String toString() {

		return buildString().toString();

	}

	private StringBuilder buildString() {
		StringBuilder s = new StringBuilder();

		Util.appendIfNonNull(s, "appId", appId);
		Util.appendIfNonNull(s, "assetId", assetId);
		Util.appendIfNonNull(s, "assetName", assetName);
		Util.appendIfNonNull(s, "assetType", assetType);
		Util.appendIfNonNull(s, "customObjectId", customObjectId);
		Util.appendIfNonNull(s, "entityType", entityType);
		Util.appendIfNonNull(s, "eventType", eventType);
		Util.appendIfNonNull(s, "executionId", executionId);
		Util.appendIfNonNull(s, "installId", installId);
		Util.appendIfNonNull(s, "instanceId", instanceId);
		Util.appendIfNonNull(s, "isPreview", isPreview);
		Util.appendIfNonNull(s, "originalAssetId", originalAssetId);
		Util.appendIfNonNull(s, "originalAssetName", originalAssetName);
		Util.appendIfNonNull(s, "originalInstallId", originalInstallId);
		Util.appendIfNonNull(s, "originalInstanceId", originalInstanceId);
		Util.appendIfNonNull(s, "renderType", renderType);
		Util.appendIfNonNull(s, "siteId", siteId == -1 ? null : Integer.toString(siteId));
		Util.appendIfNonNull(s, "siteName", siteName);
		Util.appendIfNonNull(s, "userCulture", userCulture);
		Util.appendIfNonNull(s, "userId", userId);
		Util.appendIfNonNull(s, "userName", userName);
		Util.appendIfNonNull(s, "visitorId", visitorId);

		return s;
	}

	public void log() {
		String logString = "\r--------WebHook Params Received--------";
		logString += "\r" + buildString().toString();
		logString += "\r---------------------------------------";
		log.info(logString);

	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCustomObjectId() {
		return customObjectId;
	}

	public void setCustomObjectId(String customObjectId) {
		this.customObjectId = customObjectId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getInstallId() {
		return installId;
	}

	public void setInstallId(String installId) {
		this.installId = installId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(String isPreview) {
		this.isPreview = isPreview;
	}

	public String getOriginalAssetId() {
		return originalAssetId;
	}

	public void setOriginalAssetId(String originalAssetId) {
		this.originalAssetId = originalAssetId;
	}

	public String getOriginalAssetName() {
		return originalAssetName;
	}

	public void setOriginalAssetName(String originalAssetName) {
		this.originalAssetName = originalAssetName;
	}

	public String getOriginalInstallId() {
		return originalInstallId;
	}

	public void setOriginalInstallId(String originalInstallId) {
		this.originalInstallId = originalInstallId;
	}

	public String getOriginalInstanceId() {
		return originalInstanceId;
	}

	public void setOriginalInstanceId(String originalInstanceId) {
		this.originalInstanceId = originalInstanceId;
	}

	public String getRenderType() {
		return renderType;
	}

	public void setRenderType(String renderType) {
		this.renderType = renderType;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUserCulture() {
		return userCulture;
	}

	public void setUserCulture(String userCulture) {
		this.userCulture = userCulture;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

}
