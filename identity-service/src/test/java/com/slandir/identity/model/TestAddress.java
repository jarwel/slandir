package com.slandir.identity.model;

import com.proofpoint.json.JsonCodec;
import com.slandir.identity.type.State;
import org.testng.annotations.Test;

import static com.proofpoint.testing.EquivalenceTester.equivalenceTester;
import static org.testng.Assert.assertEquals;

public class TestAddress {

    private static final JsonCodec<Address> addressCodec = JsonCodec.jsonCodec(Address.class);

    @Test
    public void testRoundTrip() {
        Address expected = new Address("123 Street", "City", State.CA.toString(), 11111);
        Address actual = addressCodec.fromJson(addressCodec.toJson(expected));

        assertEquals(actual.getStreet(), expected.getStreet());
        assertEquals(actual.getCity(), expected.getCity());
        assertEquals(actual.getState(), expected.getState());
        assertEquals(actual.getZip(), expected.getZip());
    }

    @Test
    public void testEquivalence() {
        equivalenceTester()
            .addEquivalentGroup(
                new Address("123 One Street", "Old City", State.CA.toString(), 11111),
                new Address("123 One Street", "Old City", State.CA.toString(), 11111)
            )
            .addEquivalentGroup(
                new Address("456 Two Street", "Old City", State.CA.toString(), 11111),
                new Address("456 Two Street", "Old City", State.CA.toString(), 11111)
            )
            .addEquivalentGroup(
                new Address("123 One Street", "New City", State.CA.toString(), 11111),
                new Address("123 One Street", "New City", State.CA.toString(), 11111)
            )
            .addEquivalentGroup(
                new Address("123 One Street", "Old City", State.TX.toString(), 11111),
                new Address("123 One Street", "Old City", State.TX.toString(), 11111)
            )
            .addEquivalentGroup(
                new Address("123 One Street", "Old City", State.CA.toString(), 22222),
                new Address("123 One Street", "Old City", State.CA.toString(), 22222)
            )
        .check();
    }
}
