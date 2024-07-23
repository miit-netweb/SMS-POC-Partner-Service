package com.microservices.partner.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

	private String errorid;
	private int errorcode;
	private String message;
	private HttpStatus status;
	private String timestamp;
}
