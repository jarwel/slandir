package com.slandir.identity.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.slandir.identity.model.Address;
import com.slandir.identity.model.Person;
import com.slandir.identity.type.State;
import org.joda.time.DateTime;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class TestInMemoryPersonDao {

    private PersonDao personDao;

    @BeforeMethod
    public void setUp() {
        this.personDao = new InMemoryPersonDao();
    }

    @AfterMethod
    public void tearDown() {
        personDao = null;
    }
    
    @Test
    public void testGet() {
        Person expected = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(expected);

        Person actual = personDao.get(expected.getId());
        
        assertEquals(actual, expected);
    }

    @Test
    public void testSave() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);

        List<Person> expected = Lists.newArrayList(johnDoe);

        assertEquals(personDao.fetch("John", null, null, null), expected);
        assertEquals(personDao.fetch(null, "M", null, null), expected);
        assertEquals(personDao.fetch(null, null, "Doe", null), expected);
        assertEquals(personDao.fetch(null, null, null, State.CA), expected);
        assertEquals(personDao.fetch("John", "M", null, null), expected);
        assertEquals(personDao.fetch("John", null, "Doe", null), expected);
        assertEquals(personDao.fetch("John", null, null, State.CA), expected);
        assertEquals(personDao.fetch(null, "M", "Doe", null), expected);
        assertEquals(personDao.fetch(null, "M", null, State.CA), expected);
        assertEquals(personDao.fetch(null, null, "Doe", State.CA), expected);
        assertEquals(personDao.fetch("John", "M", "Doe", State.CA), expected);
    }

    @Test
    public void testFetch() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "M", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);
        
        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch("John", "M", "Doe", State.CA));
        
        assertEquals(actual, expected);
    }

    @Test
    public void testFetchEmpty() {
        assertEquals(personDao.fetch("John", null, null, null), Collections.emptySet());
        assertEquals(personDao.fetch(null, "M", null, null), Collections.emptySet());
        assertEquals(personDao.fetch(null, null, "Doe", null), Collections.emptySet());
        assertEquals(personDao.fetch(null, null, null, State.CA), Collections.emptySet());
        assertEquals(personDao.fetch("John", "M", null, null), Collections.emptySet());
        assertEquals(personDao.fetch("John", null, "Doe", null), Collections.emptySet());
        assertEquals(personDao.fetch("John", null, null, State.CA), Collections.emptySet());
        assertEquals(personDao.fetch(null, "M", "Doe", null), Collections.emptySet());
        assertEquals(personDao.fetch(null, "M", null, State.CA), Collections.emptySet());
        assertEquals(personDao.fetch(null, null, "Doe", State.CA), Collections.emptySet());
        assertEquals(personDao.fetch("John", "M", "Doe", State.CA), Collections.emptySet());
    }

    @Test
    public void testFetchByFirstName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch("John", null, null, null));

        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByMiddleName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch(null, "M", null, null));

        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByLastName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe, janeDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch(null, null, "Doe", null));

        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByState() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Row", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe, janeDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch(null, null, null, State.CA));

        assertEquals(actual, expected);
    }

}
