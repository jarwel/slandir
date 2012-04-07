package com.slandir.identity.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.slandir.identity.model.Address;
import com.slandir.identity.model.Person;
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
        Person expected = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(expected);

        Person actual = personDao.get(expected.getId());
        
        assertEquals(actual, expected);
    }

    @Test
    public void testSave() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(johnDoe);

        List<Person> expected = Lists.newArrayList(johnDoe);

        assertEquals(personDao.fetch("John", null, null), expected);
        assertEquals(personDao.fetch(null, "M", null), expected);
        assertEquals(personDao.fetch(null, null, "Doe"), expected);
        assertEquals(personDao.fetch("John", "M", null), expected);
        assertEquals(personDao.fetch(null, "M", "Doe"), expected);
        assertEquals(personDao.fetch("John", "M", "Doe"), expected);
    }

    @Test
    public void testFetch() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "M", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);
        
        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch("John", "M", "Doe"));
        
        assertEquals(actual, expected);
    }

    @Test
    public void testFetchEmpty() {
        assertEquals(personDao.fetch("John", "M", "Doe"), Collections.emptySet());
        assertEquals(personDao.fetch("John", null, "Doe"), Collections.emptySet());
        assertEquals(personDao.fetch(null, "M", "Doe"), Collections.emptySet());
        assertEquals(personDao.fetch("John", null, null), Collections.emptySet());
        assertEquals(personDao.fetch(null, null, "Doe"), Collections.emptySet());
        assertEquals(personDao.fetch(null, null, null), Collections.emptySet());
    }

    @Test
    public void testFetchByFirstName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch("John", null, null));

        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByMiddleName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch(null, "M", null));

        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByLastName() {
        Person johnDoe = new Person(UUID.randomUUID(), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", "ST", 11111));
        Person janeDoe = new Person(UUID.randomUUID(), "Jane", "N", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@somewhere.com", new Address("123 Street", "City", "ST", 11111));

        personDao.save(johnDoe);
        personDao.save(janeDoe);

        Set<Person> expected = Sets.newHashSet(johnDoe, janeDoe);
        Set<Person> actual = Sets.newHashSet(personDao.fetch(null, null, "Doe"));

        assertEquals(actual, expected);
    }

}
