package com.oracle.eloqua.appframework.bulkapi;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EloquaBulkApi {

	private static final Logger log = LoggerFactory.getLogger(EloquaBulkApi.class);

	@Autowired
	private EloquaApiConnector eloquaApiConnector;

	public void doUpload(String bulkApiDefinition, String bulkApiPayload, String string, String installId)
			throws JSONException {

		String createDefinitionUri = createDefinition(bulkApiDefinition, installId);

		uploadPayload(bulkApiPayload, installId, createDefinitionUri);

		String syncUri = startSync(installId, createDefinitionUri);

		waitForSyncComplete(installId, syncUri);

		log.info("Bulk upload complete.");

	}

	private void waitForSyncComplete(String installId, String syncUri) throws JSONException {
		boolean complete = false;

		while (!complete) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String statusResponse = eloquaApiConnector.doGet(syncUri, installId);

			JSONObject statusResponseJson = new JSONObject(statusResponse);

			String status = statusResponseJson.get("status").toString();

			log.info("Bulk API Upload Status: " + status);

			if (status.equals("success")) {

				complete = true;
			}

		}
	}

	private String startSync(String installId, String createDefinitionUri) throws JSONException {
		JSONObject syncJsonBody = new JSONObject();
		syncJsonBody.put("syncedInstanceURI", createDefinitionUri);

		String syncResponse = eloquaApiConnector.doPost(syncJsonBody.toString(), "syncs", installId);

		JSONObject syncResponseJson = new JSONObject(syncResponse);

		String syncUri = syncResponseJson.getString("uri");
		return syncUri;
	}

	private void uploadPayload(String bulkApiPayload, String installId, String createDefinitionUri)
			throws JSONException {
		eloquaApiConnector.doPost(bulkApiPayload, createDefinitionUri + "/data", installId);
	}

	private String createDefinition(String bulkApiDefinition, String installId) throws JSONException {
		String createResponse = eloquaApiConnector.doPost(bulkApiDefinition, "contacts/imports", installId);

		JSONObject responseJson = new JSONObject(createResponse);

		String createDefinitionUri = responseJson.getString("uri");

		log.info("Bulk Upload URI: " + createDefinitionUri);
		return createDefinitionUri;
	}

}
