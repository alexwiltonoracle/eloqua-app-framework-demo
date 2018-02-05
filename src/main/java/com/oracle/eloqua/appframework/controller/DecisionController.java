package com.oracle.eloqua.appframework.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.eloqua.appframework.bulkapi.EloquaApiConnector;
import com.oracle.eloqua.appframework.dto.WebHookParams;

@RestController
@RequestMapping("/decision")
public class DecisionController {

	@Autowired
	EloquaApiConnector bulkApiConnector;

	private static final Logger log = LoggerFactory.getLogger(DecisionController.class);

	@RequestMapping("/create")
	public String create(String instanceId, WebHookParams params) {

		log.info("New decision object");
		params.log();

		JSONObject createResponse = buildCreateResponse();
		return createResponse.toString();

	}

	@RequestMapping("/notify")
	public String notify(HttpServletResponse response, WebHookParams params) {

		log.info("New member at step");
		log.info(params.toString());
		response.setStatus(HttpStatus.SC_NO_CONTENT);
		return null;
	}

	private JSONObject buildCreateResponse() {

		JSONObject recordDefinition = new JSONObject();
		try {
			recordDefinition.put("ContactID", "{{Contact.Id}}");
			recordDefinition.put("EmailAddress", "{{Contact.Field(C_EmailAddress)}}");

			JSONObject createResponse = new JSONObject();

			createResponse.put("recordDefinition", recordDefinition);
			createResponse.put("requiresConfiguration", false);
			log.info(createResponse.toString(2));
			return createResponse;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

}