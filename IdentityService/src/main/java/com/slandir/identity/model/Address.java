package com.slandir.identity.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class Address {
    
    private final String street;
    private final String city;
    private final String state;
    private final Integer zip;

    @JsonCreator
    public Address(
        @JsonProperty("street") String street,
        @JsonProperty("city") String city,
        @JsonProperty("state") String state,
        @JsonProperty("zip") Integer zip
    ) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("zip")
    public Integer getZip() {
        return zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (zip != null ? !zip.equals(address.zip) : address.zip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }
}
