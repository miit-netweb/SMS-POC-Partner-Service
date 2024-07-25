package com.microservices.partner.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubTypeNotFoundException extends RuntimeException {

	private int errorcode;
	private String message;
	private HttpStatus status;
}
