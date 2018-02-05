package com.oracle.eloqua.appframework.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.entity.CampaignEntry;
import com.oracle.eloqua.appframework.enums.RecordStatus;
import com.oracle.eloqua.appframework.repository.CampaignEntryRepository;

public class FeederService extends AbstractService {

	private CampaignEntryRepository campaignEntryRepository;

	private static final Logger log = LoggerFactory.getLogger(FeederService.class);

	@Override
	public void configure(WebHookParams params) {
		log.info("Configure Feeder Service");
		params.log();
	}

	public void checkForNewEntrants() {

		try {
			log.debug("Checking entries for instance: " + instanceId);

			List<CampaignEntry> ceList = campaignEntryRepository.findByInstanceIdAndStatus(instanceId,
					RecordStatus.NEW);
			if (ceList.size() > 0) {
				doBulkUpload(ceList);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void doBulkUpload(List<CampaignEntry> ceList) throws JSONException {

		JSONObject bulkApiDefinition = bulkApiDefinition("complete");

		JSONArray bulkApiPayload = new JSONArray();

		for (CampaignEntry campaignEntry : ceList) {
			campaignEntry.setStatus(RecordStatus.IN_PROGRESS);
			campaignEntryRepository.save(campaignEntry);
			log.info("Processing campaign entry: " + campaignEntry.toString());

			JSONObject jsonEmail = new JSONObject();
			jsonEmail.put("emailAddress", campaignEntry.getEmail());
			bulkApiPayload.put(jsonEmail);

		}

		eloquaBulkApi.doUpload(bulkApiDefinition.toString(), bulkApiPayload.toString(), "contacts/imports", installId);

		for (CampaignEntry campaignEntry : ceList) {
			campaignEntry.setStatus(RecordStatus.COMPLETE);
			campaignEntryRepository.save(campaignEntry);

		}

	}

	@Override
	public void stop() {
		log.info("Service stop. InstanceId=" + instanceId);
		// do anything here to prepare the service for shutdown
	}

	@Override
	public JSONObject buildCreateResponse() {

		try {

			JSONObject createResponse = new JSONObject();

			createResponse.put("requiresConfiguration", false);
			log.info(createResponse.toString(2));
			return createResponse;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void activated() {
		// TODO Any processes when the service is activated
		log.info("Instance activated. InstanceId=" + instanceId);

	}

	@Override
	public void draft() {
		// TODO Any processes when the service is changed to draft status
		log.info("Instance draft. InstanceId=" + instanceId);

	}

	public CampaignEntryRepository getCampaignEntryRepository() {
		return campaignEntryRepository;
	}

	public void setCampaignEntryRepository(CampaignEntryRepository campaignEntryRepository) {
		this.campaignEntryRepository = campaignEntryRepository;
	}

}
