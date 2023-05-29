package com.bank.card.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "number")
    private String number;
    @Column(name = "holder_name")
    private String holderName;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_blocked")
    private boolean isBlocked;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_on")
    private Date updatedOn;

    @PrePersist
    void prePersist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    void preUpdate() {
        this.createdOn = new Date();
    }


}

