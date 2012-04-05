package com.slandir.identity.util;

import com.slandir.identity.model.Address;
import com.slandir.identity.model.Person;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.UUID;

public class Simulacrum {
    
    private Person person;
    
    public Simulacrum(Person person) {
        this.person = person;
    }
    
    public Simulacrum assimilate(Person update) {
        UUID id = person.getId();
        String firstName = StringUtils.isBlank(person.getFirstName()) ? update.getFirstName() : person.getFirstName();
        String middleName = StringUtils.isBlank(person.getMiddleName()) ? update.getMiddleName() : person.getMiddleName();
        String lastName = StringUtils.isBlank(person.getLastName()) ? update.getLastName() : person.getLastName();
        String gender = StringUtils.isBlank(person.getGender()) ? update.getGender() : person.getGender();
        DateTime birthDate = person.getBirthDate() == null ? update.getBirthDate() : person.getBirthDate();
        String phone = StringUtils.isBlank(person.getPhone()) ? update.getPhone() : person.getPhone();
        String email = StringUtils.isBlank(person.getEmail()) ? update.getEmail() : person.getEmail();
        Address address = person.getAddress() == null ? update.getAddress() : person.getAddress();
        person = new Person(id, firstName, middleName, lastName, gender, birthDate, phone, email, address);
        return this;
    }
    
    public Person toPerson() {
        return person;
    }
}
