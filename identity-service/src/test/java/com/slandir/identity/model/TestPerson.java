package com.slandir.identity.model;

import com.slandir.identity.type.State;
import com.proofpoint.json.JsonCodec;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.proofpoint.testing.EquivalenceTester.equivalenceTester;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestPerson {
    
    private static final JsonCodec<Person> personCodec = JsonCodec.jsonCodec(Person.class);
    
    @Test
    public void testRoundTrip() {
        Person expected = new Person(UUID.randomUUID(), "John", "N", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "123-456-7890", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111));
        Person actual = personCodec.fromJson(personCodec.toJson(expected));

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getMiddleName(), expected.getMiddleName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertTrue(actual.getBirthDate().isEqual(expected.getBirthDate()));
        assertEquals(actual.getPhone(), expected.getPhone());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getAddress(), expected.getAddress());
    }
    
    @Test
    public void testEquivalence() {
        equivalenceTester()
            .addEquivalentGroup(
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "N", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Roe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "Jane", "M", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Doe", "Male", new DateTime(Integer.MIN_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "222-222-2222", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@elsewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("0de8861b-9d80-4396-8d45-1e681b6f69a8"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("456 Street", "City", State.TX.toString(), 22222))
            )
            .addEquivalentGroup(
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "Jane", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Roe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Female", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Male", new DateTime(Integer.MIN_VALUE), "111-111-1111", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "222-222-2222", "john@somewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "jane@elsewhere.com", new Address("123 Street", "City", State.CA.toString(), 11111)),
                new Person(UUID.fromString("d4e7a756-5034-4815-b0f3-c338d802b5d6"), "John", "M", "Doe", "Male", new DateTime(Integer.MAX_VALUE), "111-111-1111", "john@somewhere.com", new Address("456 Street", "City", State.TX.toString(), 22222))
            )
        .check();
    }
}
