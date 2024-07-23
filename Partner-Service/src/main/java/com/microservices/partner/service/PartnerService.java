package com.microservices.partner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.entity.PartnerDetail;
import com.microservices.partner.exception.ErrorCodes;
import com.microservices.partner.exception.ValidationException;
import com.microservices.partner.repository.PartnerRepository;

@Service
public class PartnerService {
	@Autowired
	private PartnerRepository partnerRepository;

	public boolean checkPartnerNumber(PartnerCredential credential, Long partnerNumber) {
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
}
