package com.microservices.partner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long subtypeNumber;
	private String pricingRoutine;
	private String service;
	private String frequency;
	
	@ManyToOne
	@JoinColumn(name = "partnerNumber", nullable = false)
	private PartnerDetail partnerNumber;

	@Override
	public String toString() {
		return "SubscriptionType{" +
				"id=" + id +
				", subtypeNumber=" + subtypeNumber +
				", pricingRoutine='" + pricingRoutine + '\'' +
				", service='" + service + '\'' +
				", frequency='" + frequency + '\'' +
				", partnerNumber=" + partnerNumber +
				'}';
	}
}
