package com.microservices.partner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.service.PartnerService;

@RestController
@RequestMapping("/partner")
public class PartnerController {
	
	@Autowired
	private PartnerService service;

	@PostMapping("/validate/{partnerNumber}")
	public ResponseEntity<?> validatePartner(@RequestBody PartnerCredential credential,@PathVariable Long partnerNumber){
		return new ResponseEntity<>(service.checkPartnerNumber(credential, partnerNumber),HttpStatus.OK);
	}
}
