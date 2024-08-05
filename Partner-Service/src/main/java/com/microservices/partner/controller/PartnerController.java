package com.microservices.partner.controller;


import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.dto.SubscriptionData;
import com.microservices.partner.entity.SubscriptionType;
import com.microservices.partner.proxy.JwtServerProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.partner.dto.PartnerServiceDto;
import com.microservices.partner.service.PartnerService;

@RestController
@RequestMapping("/partner")
public class PartnerController {

	@Autowired
	private PartnerService service;
	@Autowired
	private JwtServerProxy jwtServerProxy;

	// partner credential
	// partner number
	// subscription data

	@PostMapping("/validate/{partnerNumber}")
	public ResponseEntity<?> validatePartner(@RequestBody PartnerServiceDto partnerServiceDto,
			@PathVariable Long partnerNumber) {
		if(service.checkPartnerNumber(partnerServiceDto.getPartnerCredential(), partnerNumber)){
			SubscriptionType subscriptionType = service.checkSubTypeDetails(partnerServiceDto.getSubscriptionData(), partnerNumber);
			System.out.println(subscriptionType);
			if(subscriptionType!=null){
				return new ResponseEntity<>(new SubscriptionData(subscriptionType.getSubtypeNumber(),subscriptionType.getPricingRoutine(),subscriptionType.getFrequency()),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Invalid Subscription Data",HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return new ResponseEntity<>("Invalid Partner Credentials",HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping("/validate/{partnerNumber}/create/token")
	public ResponseEntity<?> validatePartnerCreateToken(@RequestBody PartnerCredential partnerServiceDto,
											 @PathVariable Long partnerNumber) {
		if(service.checkPartnerNumber(partnerServiceDto, partnerNumber)){
				ResponseEntity<?> responseEntity = jwtServerProxy.tokenGenerationForPartner(partnerNumber);
				return new ResponseEntity<>(responseEntity,HttpStatus.OK);
    }
		else {
			return new ResponseEntity<>("Invalid Partner Credentials",HttpStatus.BAD_REQUEST);
		}
	}
}
