/**
 * 
 */
package com.employee.Employee.Managment;

import java.time.ZonedDateTime;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.employee.Employee.Managment.exception.BadRequestException;
import com.employee.Employee.Managment.exception.UnexpectedErrorException;

/**
 * @author Waris
 *
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * if you are changing size in msg then change it in application.properties file
	 * also spring.servlet.multipart.max-file-size
	 * spring.servlet.multipart.max-request-size
	 */
	@ExceptionHandler(value = { MaxUploadSizeExceededException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>("File is larger than 50 MB.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ BadRequestException.class })
	protected ResponseEntity<Object> duplicateException(RuntimeException ex, WebRequest request) {
		loggerExeption(ex, request);
		return new ResponseEntity<>(getErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { UnexpectedErrorException.class })
	protected ResponseEntity<Object> unexpectedErrorException(RuntimeException ex, WebRequest request) {
		loggerExeption(ex, request);
		return new ResponseEntity<>(getErrorMessage(request, HttpStatus.BAD_REQUEST, "Unexpected error occur"),
				HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> universalException(RuntimeException ex, WebRequest request) {
		loggerExeption(ex, request);
		return new ResponseEntity<>(getErrorMessage(request, HttpStatus.BAD_REQUEST, "Internel server error"),
				HttpStatus.BAD_REQUEST);
	}

	private String getErrorMessage(WebRequest request, HttpStatus httpStatus, String message) {
		try {
			JSONObject root = new JSONObject(message);
			JSONObject obj = root.getJSONObject("error");
			message = obj.getString("errormsg");
		} catch (Exception e) {
			// TODO: handle exception
		}
		StringBuilder sb = new StringBuilder();
		if (request.getDescription(false).contains("erp/")) {
			sb.append("{");
			sb.append("\"error\" : \"" + message + "\"");
			sb.append("}");
		} else {
			sb.append("{");
			sb.append("\"error\": true,");
			sb.append("\"statusCode\" : " + httpStatus.value() + ",");
			sb.append("\"timestamp\" : \"" + ZonedDateTime.now() + "\",");
			sb.append("\"message\" : \"" + message + "\"");
			sb.append("}");
		}
		return sb.toString();
	}

	private void loggerExeption(RuntimeException ex, WebRequest request) {
		logger.info("request url :: {}", request.getDescription(false));
		logger.error("InvalidToken", ex);
	}
}