package com.oracle.eloqua.appframework.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.enums.ServiceType;
import com.oracle.eloqua.appframework.repository.CampaignEntryRepository;

@Component
public class FeederServicePool extends AbstractServicePool {

	private static final Logger log = LoggerFactory.getLogger(FeederServicePool.class);

	@Autowired
	private CampaignEntryRepository campaignEntryRepository;

	public FeederServicePool() {
		serviceType = ServiceType.FEEDER;
	}

	@Override
	protected AbstractService createRunningInstance() {
		FeederService f = new FeederService();
		f.setCampaignEntryRepository(campaignEntryRepository);
		return f;
	}

	@Scheduled(fixedDelay = 5000)
	private void fireTimer() {
		for (Map.Entry<String, AbstractService> entry : runningInstances.entrySet()) {
			FeederService feederService = (FeederService) entry.getValue();
			feederService.checkForNewEntrants();
		}

	}

	@Override
	public String configure(WebHookParams params, Map<String, String[]> allRequestParams) {
		log.info("Configure Feeder Service");
		return "configForm";
	}

}
