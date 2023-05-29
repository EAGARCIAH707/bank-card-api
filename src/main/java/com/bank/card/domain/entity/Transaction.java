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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Long transactionId;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(name = "is_cancelled")
    private boolean isCancelled;
    @Column(name = "currency")
    private String currency;
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
