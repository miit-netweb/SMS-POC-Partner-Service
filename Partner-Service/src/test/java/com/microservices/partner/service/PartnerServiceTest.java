package com.microservices.partner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.microservices.partner.dto.PartnerCredential;
import com.microservices.partner.entity.PartnerDetail;
import com.microservices.partner.exception.ErrorCodes;
import com.microservices.partner.exception.ValidationException;
import com.microservices.partner.repository.PartnerRepository;

@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {

	@Mock
	private PartnerRepository partnerRepository;
	@InjectMocks
	private PartnerService service;

	@Test
	public void testPartnerNotFound() {
		when(partnerRepository.findByPartnerNumber(ArgumentMatchers.anyLong())).thenReturn(null);
		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			service.checkPartnerNumber(mock(PartnerCredential.class), 88889999L);
		});
		assertEquals(ErrorCodes.NO_PARTNER_EXIST.getErrorCode(), thrown.getErrorcode());
		assertEquals(ErrorCodes.NO_PARTNER_EXIST.getErrorMessage(), thrown.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
	}

	@Test
	public void testInvalidPartnerByUuid() {
		PartnerDetail mockPartnerDetail = new PartnerDetail();
		mockPartnerDetail.setPartnerUuid("wedfehbfdiu2hdiu2hw");
		when(partnerRepository.findByPartnerNumber(anyLong())).thenReturn(mockPartnerDetail);

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			service.checkPartnerNumber(mock(PartnerCredential.class), 88889999L);
		});
		assertEquals(ErrorCodes.INVALID_UUID.getErrorCode(), thrown.getErrorcode());
		assertEquals(ErrorCodes.INVALID_UUID.getErrorMessage(), thrown.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
	}

	@Test
	public void testInvalidPartnerBySecret() {
		PartnerDetail mockPartnerDetail = new PartnerDetail();
		mockPartnerDetail.setPartnerUuid("9c494d20-7e3f-4bf3-b136-5fac32d547f4");
		mockPartnerDetail.setPartnerSecret("jhbdhbdhbd12");

		when(partnerRepository.findByPartnerNumber(anyLong())).thenReturn(mockPartnerDetail);

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			service.checkPartnerNumber(mock(PartnerCredential.class), 88889999L);
		});
		assertEquals(ErrorCodes.INVALID_UUID.getErrorCode(), thrown.getErrorcode());
		assertEquals(ErrorCodes.INVALID_UUID.getErrorMessage(), thrown.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
	}

	@Test
	public void testValidPartnerNumber() {
		PartnerDetail mockPartnerDetail = new PartnerDetail();
		mockPartnerDetail.setPartnerUuid("9c494d20-7e3f-4bf3-b136-5fac32d547f4");
		mockPartnerDetail.setPartnerSecret("abcd1234abcd1234");
		when(partnerRepository.findByPartnerNumber(anyLong())).thenReturn(mockPartnerDetail);
		PartnerCredential credential = new PartnerCredential();
		credential.setPartnerSecret("abcd1234abcd1234");
		credential.setPartnerUuid("9c494d20-7e3f-4bf3-b136-5fac32d547f4");
		assertTrue(service.checkPartnerNumber(credential, 88889999L));

	}

}
