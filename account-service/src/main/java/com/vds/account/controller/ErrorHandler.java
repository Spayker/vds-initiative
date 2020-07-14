package com.vds.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Custom Exception handler class. Used automatically by Spring when some request can't be processed.
 *  Reasons of such situations can be many: starting from internal server error, auth based issues, network connectivity
 *  and so on.
 **/
@ControllerAdvice
public class ErrorHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 *  Logs bad request situation registered on server.
	 *  @param e - instance of IllegalArgumentException
	 **/
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void processValidationError(IllegalArgumentException e) {
		log.info("Returning HTTP 400 Bad Request", e);
	}
}
