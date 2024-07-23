package com.microservices.partner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDetail {

    @Id
    private long partnerNumber;
    private String partnerName;
    @Column(unique = true)
    private String partnerUuid;
    @Column(unique = true)
    private String partnerSecret;
}
