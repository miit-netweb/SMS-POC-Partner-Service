package com.microservices.partner.controller;


import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.dto.SubscriptionData;
import com.microservices.partner.entity.SubscriptionType;
import com.microservices.partner.proxy.JwtServerProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerController.class);

	@Autowired
	private PartnerService service;
	@Autowired
	private JwtServerProxy jwtServerProxy;

	@PostMapping("/validate/{partnerNumber}")
	public ResponseEntity<?> validatePartner(@RequestBody PartnerServiceDto partnerServiceDto,
			@PathVariable Long partnerNumber) {
		LOGGER.info("Starting validation partner number -> {}",partnerNumber);
		if(service.checkPartnerNumber(partnerServiceDto.getPartnerCredential(), partnerNumber)){
			LOGGER.info("Validation for partner number -> {} completed",partnerNumber);
			SubscriptionType subscriptionType = service.checkSubTypeDetails(partnerServiceDto.getSubscriptionData(), partnerNumber);

			if(subscriptionType!=null){
				LOGGER.info("Subtype validation for partner number -> {} completed",partnerNumber);
				return new ResponseEntity<>(new SubscriptionData(subscriptionType.getSubtypeNumber(),subscriptionType.getPricingRoutine(),subscriptionType.getFrequency()),HttpStatus.OK);
			}
			else {
				LOGGER.info("Subtype validation for partner number -> {} is null",partnerNumber);
				return new ResponseEntity<>("Invalid Subscription Data",HttpStatus.BAD_REQUEST);
			}
		}
		else {
			LOGGER.error("Invalid partner credential for partner number -> {}",partnerNumber);
			return new ResponseEntity<>("Invalid Partner Credentials",HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping("/validate/{partnerNumber}/create/token")
	public ResponseEntity<?> validatePartnerCreateToken(@RequestBody PartnerCredential partnerServiceDto,
											 @PathVariable Long partnerNumber) {
		LOGGER.info("Started validation partner number -> {}",partnerNumber);
		if(service.checkPartnerNumber(partnerServiceDto, partnerNumber)){
				LOGGER.info("Successfully completed validation of partner number -> {}",partnerNumber);

				LOGGER.info("Initiating token creation for partner number -> {}",partnerNumber);
				ResponseEntity<?> responseEntity = jwtServerProxy.tokenGenerationForPartner(partnerNumber);
				LOGGER.info("Token created successfully for partner number -> {}",partnerNumber);

				return new ResponseEntity<>(responseEntity,HttpStatus.OK);
    }
		else {
			LOGGER.error("Invalid partner credential for partner number -> {}.",partnerNumber);
			return new ResponseEntity<>("Invalid Partner Credentials",HttpStatus.BAD_REQUEST);
		}
	}
}
