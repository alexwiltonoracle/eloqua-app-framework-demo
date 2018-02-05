package com.oracle.eloqua.appframework.util;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public class Util {
	public static void appendIfNonNull(StringBuilder s, String name, String value) {
		if (value != null) {
			if (s.length() > 0) {
				s.append("\n");
			}
			s.append(padRight(name, 20) + ": ");
			s.append(value);

		}
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static void logIncomingRequest(Logger log, HttpServletRequest request) {

		log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		log.info("   >   Incoming URL: " + request.getRequestURL().toString());

		Map<String, String[]> allParams = request.getParameterMap();
		log.info("   >   URL Params:");
		for (Map.Entry<String, String[]> p : allParams.entrySet()) {

			log.info(String.format("      >   %s = %s", p.getKey(), Arrays.toString(p.getValue())));
		}
		
		
		//request.
		//log.info("   >   Form Params:");

		log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

	}
}
