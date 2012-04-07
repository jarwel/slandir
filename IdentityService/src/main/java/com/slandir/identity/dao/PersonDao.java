package com.slandir.identity.dao;

import com.slandir.identity.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao {
    Person get(UUID id);
    List<Person> fetch(String firstName, String middleName, String lastName);
    void save(Person person);
}
