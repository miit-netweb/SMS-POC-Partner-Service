package com.microservices.partner.dto;

import java.util.UUID;

public class ErrorIDGenerator {

	public synchronized static String getErrorId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}