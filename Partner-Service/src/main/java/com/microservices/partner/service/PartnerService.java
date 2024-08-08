package com.microservices.partner.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.dto.SubscriptionData;
import com.microservices.partner.entity.PartnerDetail;
import com.microservices.partner.entity.SubscriptionType;
import com.microservices.partner.exception.ErrorCodes;
import com.microservices.partner.exception.SubTypeNotFoundException;
import com.microservices.partner.exception.ValidationException;
import com.microservices.partner.repository.PartnerRepository;
import com.microservices.partner.repository.SubscriptionTypeRepository;

@Service
public class PartnerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerService.class);
	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private SubscriptionTypeRepository subtypeRepository;

	public boolean checkPartnerNumber(PartnerCredential credential, Long partnerNumber) throws ValidationException {
		LOGGER.info("Partner repository called");
		PartnerDetail partnerDetail = partnerRepository.findByPartnerNumber(partnerNumber);
		LOGGER.info("Partner repository data fetched successfully");
		if (partnerDetail == null) {
			LOGGER.error("Partner data null");
			throw new ValidationException(ErrorCodes.NO_PARTNER_EXIST.getErrorCode(),
					ErrorCodes.NO_PARTNER_EXIST.getErrorMessage(), HttpStatus.BAD_REQUEST);
		} else if (!partnerDetail.getPartnerUuid().equals(credential.getPartnerUuid())) {
			LOGGER.error("Partner uuid invalid");
			throw new ValidationException(ErrorCodes.INVALID_UUID.getErrorCode(),
					ErrorCodes.INVALID_UUID.getErrorMessage(), HttpStatus.BAD_REQUEST);
		} else if (!partnerDetail.getPartnerSecret().equals(credential.getPartnerSecret())) {
			LOGGER.error("Partner secret invalid");
			throw new ValidationException(ErrorCodes.INVALID_SECRET_KEY.getErrorCode(),
					ErrorCodes.INVALID_SECRET_KEY.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("Partner validated successfully");
		return true;
	}

	public SubscriptionType checkSubTypeDetails(SubscriptionData subData, Long partnerNumber) throws RuntimeException {
		try {
			LOGGER.info("Subtype repository called");
			Optional<SubscriptionType> subType = subtypeRepository.findSubscriptionType(partnerNumber,
					subData.getSubtypeNumber(), subData.getPricingRoutine(), subData.getFrequency());
			LOGGER.info("Subtype data fetched successfully");
			if (subType.isEmpty()) {
				LOGGER.error("Subtype not found");
				throw new SubTypeNotFoundException(ErrorCodes.SUBTYPE_NOT_FOUND.getErrorCode(),
					ErrorCodes.SUBTYPE_NOT_FOUND.getErrorMessage(), HttpStatus.BAD_REQUEST);
			}
			LOGGER.info("Subtype validated successfully");
			return subType.get();
		} catch (RuntimeException e) {
			LOGGER.error("Runtime exception -> {}",e.getMessage());
			throw new SubTypeNotFoundException(ErrorCodes.SUBTYPE_NOT_FOUND.getErrorCode(),
					ErrorCodes.SUBTYPE_NOT_FOUND.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

	}
}
