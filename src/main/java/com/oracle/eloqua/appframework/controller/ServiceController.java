package com.oracle.eloqua.appframework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.service.AbstractService;
import com.oracle.eloqua.appframework.service.AbstractServicePool;
import com.oracle.eloqua.appframework.service.ServicePools;
import com.oracle.eloqua.appframework.util.Util;

//handles service level webhook calls from Eloqua
@RestController
@RequestMapping("/service")
public class ServiceController {

	@Autowired
	ServicePools servicePools;

	private static final Logger log = LoggerFactory.getLogger(ServiceController.class);

	// Triggered when a new instances has been added to the campaign canvas
	@RequestMapping("/{serviceType}/create")
	public String create(WebHookParams params, @PathVariable String serviceType, HttpServletRequest request) {
		log.info("Incoming create request...");
		Util.logIncomingRequest(log, request);
		params.log();
		AbstractServicePool servicePool = servicePools.selectPool(serviceType);
		AbstractService service = servicePool.create(params);
		JSONObject createResponse = service.buildCreateResponse();
		return createResponse.toString();
	}

	// Triggered when an event happens on the instance (activated, draft etc...)
	@RequestMapping("/{serviceType}/notify")
	public String notify(HttpServletResponse response, WebHookParams params, @PathVariable String serviceType,
			HttpServletRequest request) {
		log.info("Incoming notify request...");
		Util.logIncomingRequest(log, request);
		params.log();
		AbstractServicePool servicePool = servicePools.selectPool(serviceType);

		servicePool.notification(params);
		response.setStatus(HttpStatus.SC_NO_CONTENT);
		return null;
	}

	// triggered when the service instance is deleted
	@RequestMapping("/{serviceType}/delete")
	public String delete(WebHookParams params, @PathVariable String serviceType, HttpServletRequest request) {
		log.info("Incoming delete request...");
		Util.logIncomingRequest(log, request);
		params.log();
		AbstractServicePool servicePool = servicePools.selectPool(serviceType);

		servicePool.delete(params);
		return null;
	}

}