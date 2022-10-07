package com.tcs.eas.rest.apis.utility;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.dis.DisEntity;
import com.tcs.dis.KafkaPublish;
import com.tcs.eas.rest.apis.Constants;
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;

/**
 * 
 * @author 44745
 *
 */
public class Utility {
	private Utility() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Object> from(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
		});
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestHeaderValues(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		return remoteAddr;
	}

	/**
	 * 
	 * @param headers
	 * @return
	 */
	public static HttpHeaders getCustomResponseHeaders(Map<String, String> headers) {
		// setting required response headers
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(Constants.TRANSACTION_ID,
				headers.get(Constants.TRANSACTION_ID) == null ? Constants.NO_TRANSATION_ID : headers.get(Constants.TRANSACTION_ID));
		String correlationId = headers.get(Constants.CORRELATION_ID);
		if (correlationId != null) {
			responseHeaders.set(Constants.CORRELATION_ID, correlationId);
		}

		return responseHeaders;
	}
	

}
