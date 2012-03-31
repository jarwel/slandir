package com.slandir.identity.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.slandir.identity.model.Address;
import com.slandir.identity.model.Person;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PersonDao {

    private final Map<UUID, Person> persons = Maps.newHashMap();
    private final Map<String, Set<Person>> nameIndex = Maps.newHashMap();
    
    public Person get(UUID id) {
        return persons.get(id);
    }
    
    public Set<Person> fetch(String firstName, String middleName, String lastName) {

        List<Set<Person>> sets = Lists.newArrayList();
        if(StringUtils.isNotBlank(firstName)) {
            sets.add(nameIndex.get(firstName.toLowerCase()));
        }
        if(StringUtils.isNotBlank(middleName)) {
            sets.add(nameIndex.get(middleName.toLowerCase()));
        }
        if(StringUtils.isNotBlank(lastName)) {
            sets.add(nameIndex.get(lastName.toLowerCase()));
        }

        if(sets.isEmpty()) {
            return Collections.emptySet();
        }
        
        Set<Person> persons = sets.get(0);
        for(Set<Person> set : sets.subList(1, sets.size())) {
            if(persons == null || set == null) {
                return Collections.emptySet();
            }
            persons = Sets.intersection(persons, set);
        }
        
        if(persons == null) {
            return Collections.emptySet();
        }
        return persons;
    }

    public void save(Person person) {
        persons.put(person.getId(), person);

        if(StringUtils.isNotBlank(person.getFirstName())) {
            if(nameIndex.get(person.getFirstName().toLowerCase()) == null) {
                nameIndex.put(person.getFirstName().toLowerCase(), new HashSet<Person>());
            }
            nameIndex.get(person.getFirstName().toLowerCase()).add(person);
        }

        if(StringUtils.isNotBlank(person.getMiddleName())) {
            if(nameIndex.get(person.getMiddleName().toLowerCase()) == null) {
                nameIndex.put(person.getMiddleName().toLowerCase(), new HashSet<Person>());
            }
            nameIndex.get(person.getMiddleName().toLowerCase()).add(person);
        }

        if(StringUtils.isNotBlank(person.getLastName())) {
            if(nameIndex.get(person.getLastName().toLowerCase()) == null) {
                nameIndex.put(person.getLastName().toLowerCase(), new HashSet<Person>());
            }
            nameIndex.get(person.getLastName().toLowerCase()).add(person);
        }
    }

    public void update(Person updatePerson) {
        Person oldPerson = persons.get(updatePerson.getId());

        persons.remove(oldPerson);
        if(StringUtils.isNotBlank(oldPerson.getFirstName())) {
            nameIndex.get(oldPerson.getFirstName().toLowerCase()).remove(oldPerson);
        }
        if(StringUtils.isNotBlank(oldPerson.getMiddleName())) {
            nameIndex.get(oldPerson.getMiddleName().toLowerCase()).remove(oldPerson);
        }
        if(StringUtils.isNotBlank(oldPerson.getLastName())) {
            nameIndex.get(oldPerson.getLastName().toLowerCase()).remove(oldPerson);
        }
        
        Person newPerson = combine(oldPerson, updatePerson);
        save(newPerson);
    }
    
    private Person combine(Person oldPerson, Person updatePerson) {
        UUID id = oldPerson.getId();
        String firstName = oldPerson.getFirstName();
        String middleName = oldPerson.getMiddleName();
        String lastName = oldPerson.getLastName();
        String gender = oldPerson.getGender();
        DateTime birthDate = oldPerson.getBirthDate();
        String phone = oldPerson.getPhone();
        String email = oldPerson.getEmail();
        Address address = oldPerson.getAddress();
        
        if(StringUtils.isBlank(firstName)) {
            firstName = updatePerson.getFirstName();
        }
        if(StringUtils.isBlank(middleName)) {
            middleName = updatePerson.getMiddleName();
        }
        if(StringUtils.isBlank(lastName)) {
            lastName = updatePerson.getLastName();
        }
        if(StringUtils.isBlank(gender)) {
            gender = updatePerson.getGender();
        }
        if(birthDate == null) {
            birthDate = updatePerson.getBirthDate();
        }
        if(StringUtils.isBlank(phone)) {
            phone = updatePerson.getPhone();
        }
        if(StringUtils.isBlank(email)) {
            email = updatePerson.getEmail();
        }
        if(address == null) {
            address = updatePerson.getAddress();
        }
        
        return new Person(id, firstName, middleName, lastName, gender, birthDate, phone, email, address);
    }

}
