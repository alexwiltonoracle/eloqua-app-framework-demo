package com.oracle.eloqua.appframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.eloqua.appframework.util.EloquaStrings;

@Component
public class ServicePools {

	@Autowired
	FeederServicePool feederServicePool;

	// handles the allocation of the request to the correct service pool
	public AbstractServicePool selectPool(String serviceType) {

		switch (serviceType) {
		case EloquaStrings.SERVICE_TYPE_ACTION:

			break;
		case EloquaStrings.SERVICE_TYPE_CONTENT:

			break;
		case EloquaStrings.SERVICE_TYPE_DECISION:

			break;
		case EloquaStrings.SERVICE_TYPE_FEEDER:
			return feederServicePool;
		case EloquaStrings.SERVICE_TYPE_FIREHOSE:

			break;
		case EloquaStrings.SERVICE_TYPE_MENU:

			break;
		default:
			break;
		}

		return null;
	}
}
