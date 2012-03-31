package com.slandir.account.model;

import com.google.common.base.Preconditions;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

public class Account {
    
    private final UUID id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public Account(    
        @JsonProperty("id") UUID id,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName
        
    ) {
        this.id = Preconditions.checkNotNull(id);
        this.email = Preconditions.checkNotNull(email);
        this.firstName = Preconditions.checkNotNull(firstName);
        this.lastName = Preconditions.checkNotNull(lastName);
        this.password = password;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!id.equals(account.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
