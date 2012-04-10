package com.slandir.identity.dao;

import com.slandir.identity.model.Person;
import com.slandir.identity.type.State;

import java.util.List;
import java.util.UUID;

public interface PersonDao {
    Person get(UUID id);
    List<Person> fetch(String firstName, String middleName, String lastName, State state);
    void save(Person person);
}
