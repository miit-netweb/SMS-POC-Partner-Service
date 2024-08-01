package com.microservices.partner.handler;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.microservices.partner.dto.ErrorIDGenerator;
import com.microservices.partner.dto.ExceptionResponse;
import com.microservices.partner.exception.SubTypeNotFoundException;
import com.microservices.partner.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException exception) {
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(exception.getErrorcode());
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus());
		response.setTimestamp(LocalTime.now().toString());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SubTypeNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleSubTypeNotFoundException(SubTypeNotFoundException exception) {
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(exception.getErrorcode());
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus());
		response.setTimestamp(LocalTime.now().toString());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex){
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(500);
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setTimestamp(LocalTime.now().toString());
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
