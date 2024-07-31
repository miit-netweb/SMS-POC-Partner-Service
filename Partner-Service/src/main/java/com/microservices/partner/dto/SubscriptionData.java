package com.microservices.partner.dto;

public class SubscriptionData {

	private Long subtypeNumber;
	private String pricingRoutine;
	private String frequency;
	
	public SubscriptionData() {
	}

	public SubscriptionData(Long subtypeNumber, String pricingRoutine, String frequency) {
		this.subtypeNumber = subtypeNumber;
		this.pricingRoutine = pricingRoutine;
		this.frequency = frequency;
	}

	public Long getSubtypeNumber() {
		return subtypeNumber;
	}
	public void setSubtypeNumber(Long subtypeNumber) {
		this.subtypeNumber = subtypeNumber;
	}
	public String getPricingRoutine() {
		return pricingRoutine;
	}
	public void setPricingRoutine(String pricingRoutine) {
		this.pricingRoutine = pricingRoutine;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	
}
