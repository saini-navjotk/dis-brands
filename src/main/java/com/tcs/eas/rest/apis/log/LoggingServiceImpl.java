package com.tcs.eas.rest.apis.log;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.eas.rest.apis.Constants;
import com.tcs.eas.rest.apis.utility.Utility;

import lombok.extern.java.Log;
import net.logstash.logback.argument.StructuredArguments;

@Component
@Log
public class LoggingServiceImpl extends MdcLogging implements LoggingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

	@Override
	public void logRequest(HttpServletRequest request, Object body) {
		Map<String, String> map = new HashMap<>();
		String transationId = request.getHeader(Constants.TRANSACTION_ID);
		if (transationId == null) {
			transationId = Constants.NO_TRANSATION_ID;
		}
		map.put(Constants.TRANSACTION_ID, transationId);
		map.put(Constants.HTTP_METHOD, request.getMethod());
		map.put(Constants.REQUEST_URI, request.getRequestURI());
		map.put(Constants.HTTP_PROTOCOL, request.getProtocol());
		map.put(Constants.HEADER_CONTENT_TYPE, request.getContentType());
		map.put(Constants.CLIENT_IP, Utility.getClientIp(request));
		Map<String, String> httpHeadersMap = Utility.getRequestHeaderValues(request);
		setMDC(map);
		LOGGER.info("Request details", StructuredArguments.entries(httpHeadersMap));
		clearMDC();
		MDC.put(Constants.TRANSACTION_ID, transationId);
	}

	@Override
	public void logResponse(HttpServletRequest request, HttpServletResponse httpServletResponse, Object body) {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.HTTP_METHOD, request.getMethod());
		map.put(Constants.REQUEST_URI, request.getRequestURI());
		map.put(Constants.HTTP_PROTOCOL, request.getProtocol());
		map.put(Constants.HTTP_STATUS, httpServletResponse.getStatus() + "");
		setMDC(map);
		LOGGER.info("Response details", httpServletResponse.getStatus());
		clearMDC();
	}

	/**
	 * 
	 */
	@Override()
	public void clearMDC() {
		MDC.clear();
	}

	/**
	 * 
	 * @param map
	 */
	@Override
	protected void setMDC(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			MDC.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	@Override()
	protected Map<String, Object> from(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
		});
	}

	public void writeProcessLog(String httpMethod, String serviceName, String serviceMethod, Object object) {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.TRANSACTION_ID, MDC.get(Constants.TRANSACTION_ID));
		map.put(Constants.HTTP_METHOD, httpMethod);
		map.put(Constants.SERVICE_NAME, serviceName);
		map.put(Constants.SERVICE_METHOD, serviceMethod);
		setMDC(map);
		if ( !(object instanceof List)) {
			LOGGER.info(Constants.PROCESSING_DETAILS+ httpMethod + Constants.CALL_FOR + object.getClass().getName(),
					kv("jsonPayload", from(object)));
		} else if (object instanceof List) {
			LOGGER.info(Constants.PROCESSING_DETAILS + httpMethod +  Constants.CALL_FOR + object.getClass().getName(),
					kv("jsonPayload", writeListToJsonArray(object)));
		}

		else {
			LOGGER.info(Constants.PROCESSING_DETAILS + httpMethod +  Constants.CALL_FOR + object.getClass().getName());
		}
	}

	/**
	 * 
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public String writeListToJsonArray(Object list) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, list);
			final byte[] data = out.toByteArray();
			return new String(data);
		} catch (Exception e) {
			return "{\"error\":\"payload generation issue\"}";
		}
	}

	@Override
	public void logError(String errorMessage) {
		LOGGER.error("Error: " + errorMessage, Constants.HTTP_STATUS_500);
	}

}
