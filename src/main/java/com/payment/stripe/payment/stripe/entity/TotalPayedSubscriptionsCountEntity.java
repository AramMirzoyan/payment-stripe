package com.payment.stripe.payment.stripe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class TotalPayedSubscriptionsCountEntity {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private int totalCount;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = this.created;
    }

    @PreRemove
    protected void onRemove() {
        this.deleted = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPayedSubscriptionsCountEntity that = (TotalPayedSubscriptionsCountEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, deleted);
    }
}
