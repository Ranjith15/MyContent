package com.edassist.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edassist.constants.Constants;
import com.edassist.models.errorObjects.RestErrors;

@ControllerAdvice
public class RestControllerAdvice {

	private static Logger log = Logger.getLogger(RestControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<RestErrors> handleGeneralException(Exception ex) {
		log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
		return new ResponseEntity<RestErrors>(new RestErrors(Constants.REST_UNEXPECTED_ERROR), HttpStatus.BAD_REQUEST);
	}
}