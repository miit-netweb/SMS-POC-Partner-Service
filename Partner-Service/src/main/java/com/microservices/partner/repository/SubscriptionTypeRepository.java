package com.microservices.partner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservices.partner.entity.SubscriptionType;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {
	@Query(value = "SELECT * FROM SUBSCRIPTION_TYPE WHERE PARTNER_NUMBER = :partnerNumber AND SUBTYPE_NUMBER = :subtypeNo AND PRICING_ROUTINE = :pricingRoutine AND FREQUENCY = :frequency", nativeQuery = true)
	public Optional<SubscriptionType> findSubscriptionType(@Param("partnerNumber") Long partnerNumber,
			@Param("subtypeNo") Long subtypeNumber, @Param("pricingRoutine") String pricingRoutine,
			@Param("frequency") String frequency);
}
