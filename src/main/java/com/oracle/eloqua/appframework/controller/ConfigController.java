package com.oracle.eloqua.appframework.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.eloqua.appframework.dto.WebHookParams;
import com.oracle.eloqua.appframework.service.AbstractServicePool;
import com.oracle.eloqua.appframework.service.ServicePools;
import com.oracle.eloqua.appframework.util.Util;

//handles service level webhook calls from Eloqua
@Controller
@RequestMapping("/service")
public class ConfigController {

	@Autowired
	ServicePools servicePools;

	private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

	// Triggered when the configure button is pressed on the service instance
	@RequestMapping("/{serviceType}/configure")
	public String configure(WebHookParams params, @PathVariable String serviceType, HttpServletRequest request,
			Model model) {
		log.info("Incoming configure request...");
		Util.logIncomingRequest(log, request);
		params.log();
		AbstractServicePool servicePool = servicePools.selectPool(serviceType);
		model.addAttribute("params", params);
		model.addAttribute("serviceType", serviceType);
		return servicePool.configure(params, request.getParameterMap());
	}

}