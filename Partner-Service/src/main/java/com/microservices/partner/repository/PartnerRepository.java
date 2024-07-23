package com.microservices.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.partner.entity.PartnerDetail;

public interface PartnerRepository extends JpaRepository<PartnerDetail,Long> {
    PartnerDetail findByPartnerNumber(long partnerNumber);
}
