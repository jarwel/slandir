package com.slandir.identity.model;

import com.google.common.base.Preconditions;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import javax.annotation.concurrent.Immutable;
import java.util.UUID;

@Immutable
public class Person {
    
    private final UUID id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String gender;
    private final DateTime birthDate;
    private final String phone;
    private final String email;
    private final Address address;

    @JsonCreator
    public Person(
        @JsonProperty("id") UUID id,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("middleName") String middleName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("gender") String gender,
        @JsonProperty("birthDate") DateTime birthDate,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") Address address

    ) {
        this.id = Preconditions.checkNotNull(id, "id cannot be null");
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("birthDate")
    public DateTime getBirthDate() {
        return birthDate;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append(id).append(" ")
            .append(firstName).append(" ").append(lastName)
         .toString();
    }
}
