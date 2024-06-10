package com.libraryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @JsonProperty("createdAt")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createAt;

    @JsonProperty("updatedAt")
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updateAt;
}
