package com.oracle.eloqua.appframework.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.oracle.eloqua.appframework.bulkapi.EloquaBulkApi;
import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.enums.ServiceType;

public abstract class AbstractService {

	protected EloquaBulkApi eloquaBulkApi;

	protected ServiceType serviceType;

	protected String instanceId;

	protected String installId;

	public void setup(ServiceType serviceType, EloquaBulkApi eloquaBulkApi, String instanceId, String installId) {
		this.serviceType = serviceType;
		this.eloquaBulkApi = eloquaBulkApi;
		this.instanceId = instanceId;
		this.installId = installId;
	}

	// public abstract void notification(WebHookParams params);

	// public abstract void delete(WebHookParams params);

	public abstract void configure(WebHookParams params);

	public abstract void stop();

	public abstract void activated();

	public abstract void draft();

	public abstract JSONObject buildCreateResponse();

	protected JSONObject bulkApiDefinition(String status) {
		String instanceIdNoDash = instanceId.replace("-", "");

		String definitionName = String.format(serviceTypeString() + " Bulk Import [Instance=%s, result=%s]", instanceId,
				status);
		try {
			JSONObject body = new JSONObject();
			JSONObject fields = new JSONObject();
			JSONObject syncActions = new JSONObject();

			fields.put("emailAddress", "{{Contact.Field(C_EmailAddress)}}");

			syncActions.put("destination", "{{" + serviceTypeString() + "Instance(" + instanceIdNoDash + ")}}");
			syncActions.put("action", "setStatus");
			syncActions.put("status", status);

			JSONArray syncActionsArray = new JSONArray();
			syncActionsArray.put(syncActions);

			body.put("name", definitionName);
			body.put("fields", fields);
			body.put("syncActions", syncActionsArray);
			body.put("identifierFieldName", "emailAddress");

			return body;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected String serviceTypeString() {

		String s = serviceType.toString();

		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
