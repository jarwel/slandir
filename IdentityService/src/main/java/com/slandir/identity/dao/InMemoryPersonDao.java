package com.slandir.identity.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.slandir.identity.model.Person;
import com.slandir.identity.type.State;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class InMemoryPersonDao implements PersonDao {

    private final Map<UUID, Person> persons = Maps.newHashMap();
    private final Map<String, Set<Person>> nameIndex = Maps.newHashMap();
    private final Map<State, Set<Person>> stateIndex = Maps.newHashMap();
    
    @Override
    public Person get(UUID id) {
        return persons.get(id);
    }
    
    @Override
    public List<Person> fetch(String firstName, String middleName, String lastName, State state) {

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
        if(state != null) {
            sets.add(stateIndex.get(state));
        }

        if(sets.isEmpty()) {
            return Collections.emptyList();
        }
        
        Set<Person> persons = sets.get(0);
        for(Set<Person> set : sets.subList(1, sets.size())) {
            if(persons == null || set == null) {
                return Collections.emptyList();
            }
            persons = Sets.intersection(persons, set);
        }
        
        if(persons == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(persons);
    }

    @Override
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

        if(person.getAddress() != null && person.getAddress().getState() != null) {
            if(stateIndex.get(State.valueOf(person.getAddress().getState())) == null) {
                stateIndex.put(State.valueOf(person.getAddress().getState()), new HashSet<Person>());
            }
            stateIndex.get(State.valueOf(person.getAddress().getState())).add(person);
        }
    }

}
