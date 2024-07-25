package com.microservices.partner.service;

import java.util.Optional;

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
	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private SubscriptionTypeRepository subtypeRepository;

	public boolean checkPartnerNumber(PartnerCredential credential, Long partnerNumber) throws ValidationException {
		PartnerDetail partnerDetail = partnerRepository.findByPartnerNumber(partnerNumber);
		if (partnerDetail == null) {
			throw new ValidationException(ErrorCodes.NO_PARTNER_EXIST.getErrorCode(),
					ErrorCodes.NO_PARTNER_EXIST.getErrorMessage(), HttpStatus.BAD_REQUEST);
		} else if (!partnerDetail.getPartnerUuid().equals(credential.getPartnerUuid())) {
			throw new ValidationException(ErrorCodes.INVALID_UUID.getErrorCode(),
					ErrorCodes.INVALID_UUID.getErrorMessage(), HttpStatus.BAD_REQUEST);
		} else if (!partnerDetail.getPartnerSecret().equals(credential.getPartnerSecret())) {
			throw new ValidationException(ErrorCodes.INVALID_SECRET_KEY.getErrorCode(),
					ErrorCodes.INVALID_SECRET_KEY.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		return true;
	}

	public boolean checkSubTypeDetails(SubscriptionData subData, Long partnerNumber) throws RuntimeException {
		try {
			Optional<SubscriptionType> subType = subtypeRepository.findSubscriptionType(partnerNumber,
					subData.getSubtypeNumber(), subData.getPricingRoutine(), subData.getFrequency());

			if (subType.isEmpty()) {
				throw new SubTypeNotFoundException(ErrorCodes.SUBTYPE_NOT_FOUND.getErrorCode(),
					ErrorCodes.SUBTYPE_NOT_FOUND.getErrorMessage(), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}
}
