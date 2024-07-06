package com.libraryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "patrons")
public class Patron extends BaseEntity {
    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("phoneNumber")
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    @Column(name = "email", unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    public Patron() {
    }

    public Patron(String name, String phoneNumber, String email, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
