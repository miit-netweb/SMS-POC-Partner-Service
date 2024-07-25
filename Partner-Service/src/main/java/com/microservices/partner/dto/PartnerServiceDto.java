package com.microservices.partner.dto;

public class PartnerServiceDto {

	private PartnerCredential partnerCredential;
	private SubscriptionData subscriptionData;

	public PartnerCredential getPartnerCredential() {
		return partnerCredential;
	}

	public void setPartnerCredential(PartnerCredential partnerCredential) {
		this.partnerCredential = partnerCredential;
	}

	public SubscriptionData getSubscriptionData() {
		return subscriptionData;
	}

	public void setSubscriptionData(SubscriptionData subscriptionData) {
		this.subscriptionData = subscriptionData;
	}

	public PartnerServiceDto() {
	}
}
