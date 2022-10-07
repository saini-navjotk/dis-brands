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
import com.tcs.eas.rest.apis.model.Brand;
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;


public class Utility {
	private Utility() {
		super();
	
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
	
	public static void sendToKafka(ProductBrandApiModel brand) {
		KafkaPublish publish = new KafkaPublish();
		ObjectMapper mapper = new ObjectMapper();
		publish.send(DisEntity.BRANDS, mapper.convertValue(brand, JsonNode.class));
	}

	public static void sendToKafka(List<ProductBrandApiModel> brandList) {
		KafkaPublish publish = new KafkaPublish();
		ObjectMapper mapper = new ObjectMapper();
		publish.send(DisEntity.BRANDS, mapper.convertValue(brandList, JsonNode.class)); 
		
	}

	

}
