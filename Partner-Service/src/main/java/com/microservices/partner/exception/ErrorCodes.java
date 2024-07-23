package com.microservices.partner.exception;

import lombok.Getter;

public enum ErrorCodes {
	
	NO_PARTNER_EXIST(1015,"No such partner exist"),
	INVALID_UUID(1016,"Partner number is invalid,wrong UUID"),
	INVALID_SECRET_KEY(1017,"Partner number is invalid,wrong Secret Key");
	
	@Getter
	private int errorCode;
	@Getter
	private String errorMessage;
	
	private ErrorCodes(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
