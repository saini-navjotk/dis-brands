package com.tcs.eas.rest.apis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand origin does not exist", value = HttpStatus.NOT_FOUND)
public class BrandOriginNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8640194513320259327L;

	/**
	 * 
	 */

	public BrandOriginNotFound(String message) {
		super(message);
	}

}
