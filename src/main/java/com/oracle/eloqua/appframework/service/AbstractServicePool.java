package com.oracle.eloqua.appframework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.eloqua.appframework.bulkapi.EloquaBulkApi;
import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.entity.Instance;
import com.oracle.eloqua.appframework.enums.InstanceStatus;
import com.oracle.eloqua.appframework.enums.ServiceType;
import com.oracle.eloqua.appframework.repository.InstanceRepository;
import com.oracle.eloqua.appframework.util.EloquaStrings;

public abstract class AbstractServicePool {

	private static final Logger log = LoggerFactory.getLogger(AbstractServicePool.class);

	@Autowired
	InstanceRepository instanceRepository;

	@Autowired
	protected EloquaBulkApi eloquaBulkApi;

	protected Map<String, AbstractService> runningInstances = new HashMap<String, AbstractService>();

	protected ServiceType serviceType;

	public AbstractService checkAndCreateRunningInstance(String installId, String instanceId) {

		AbstractService runningInstance = findInstance(instanceId);

		if (runningInstance == null) {

			runningInstance = createRunningInstance(); // calls to sub class to create correct
											// object type
			runningInstance.setup(serviceType, eloquaBulkApi, instanceId, installId);
			runningInstances.put(instanceId, runningInstance);

		} else {
			log.info("...live instance exists, do not create");

		}

		return runningInstance;
	}

	protected abstract AbstractService createRunningInstance();

	public abstract String configure(WebHookParams params, Map<String, String[]> allRequestParams);

	public AbstractService findInstance(String instanceId) {
		AbstractService f = runningInstances.get(instanceId);
		return f;
	}

	public AbstractService create(WebHookParams params) {
		AbstractService result = checkAndCreateRunningInstance(params.getInstallId(), params.getInstanceId());
		Instance instance = new Instance(serviceType, InstanceStatus.DRAFT, params);
		instanceRepository.save(instance);
		return result;
	}

	public void notification(WebHookParams params) {
		log.info(serviceType + " service notify");

		Instance savedInstance = instanceRepository.findOne(params.getInstanceId());

		AbstractService runningInstance = runningInstances.get(params.getInstanceId());

		if (savedInstance != null) {
			String eventType = params.getEventType();
			if (eventType.equals(EloquaStrings.EVENT_TYPE_ACTIVATED)) {
				runningInstance.activated();
				savedInstance.setInstanceStatus(InstanceStatus.ACTIVATED);
			} else if (eventType.equals(EloquaStrings.EVENT_TYPE_DRAFT)) {
				runningInstance.draft();
				savedInstance.setInstanceStatus(InstanceStatus.DRAFT);
			}
			instanceRepository.save(savedInstance);
		} else {
			log.info("Cannot find saved instance. instanceId=" + params.getInstanceId());
		}

	}

	public void delete(WebHookParams params) {
		log.info("Delete " + serviceType + " Service");

		AbstractService runningInstance = runningInstances.get(params.getInstanceId());
		if (runningInstance != null) {
			log.info("Stopping service instance");
			runningInstance.stop();
			runningInstances.remove(params.getInstanceId());
		}

		Instance instance = instanceRepository.findOne(params.getInstanceId());

		if (instance != null) {
			instance.setInstanceStatus(InstanceStatus.DELETED);
			log.info("Marking as deleted in DB: " + instance.toString());
			instanceRepository.save(instance);
		} else {
			log.info("Cannot find instance to delete. instanceId=" + params.getInstanceId());
		}

	}

	public void startExisting() {
		log.info("Starting existing " + serviceType);
		List<Instance> savedInstances = instanceRepository.findByServiceType(serviceType);
		log.info("Total found: " + savedInstances.size());
		for (Instance savedInstance : savedInstances) {
			String instanceId = savedInstance.getInstanceId();
			log.info("Checking whether to start instance: " + instanceId);
			if (savedInstance.getInstanceStatus() != InstanceStatus.DELETED) {
				log.info("...creating instance from DB record: " + savedInstance.toString());
				checkAndCreateRunningInstance(savedInstance.getInstallId(), instanceId);
			} else {
				log.info("...running instance deleted, do not create");

			}

		}

	}

}
