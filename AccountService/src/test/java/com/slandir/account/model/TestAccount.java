package com.slandir.account.model;

import com.proofpoint.json.JsonCodec;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.proofpoint.testing.EquivalenceTester.equivalenceTester;
import static org.testng.Assert.assertEquals;

public class TestAccount {

    private static final JsonCodec<Account> addressCodec = JsonCodec.jsonCodec(Account.class);

    @Test
    public void testRoundTrip() {
        Account expected = new Account(UUID.randomUUID(), "someone@somewhere.com", "p@ssw0rd", "John", "Doe");
        Account actual = addressCodec.fromJson(addressCodec.toJson(expected));

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
    }

    @Test
    public void testEquivalence() {
        equivalenceTester()
            .addEquivalentGroup(
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "john@somewhere.com", "p@ssw0rd", "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "john@somewhere.com", "Sl@nd1r", "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "john@somewhere.com", null, "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "john@somewhere.com", "p@ssw0rd", "Jane", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "john@somewhere.com", "p@ssw0rd", "John", "Roe")
            )
            .addEquivalentGroup(
                new Account(UUID.fromString("02e0a47c-0bb9-424f-b20a-cd08001dd6a1"), "john@somewhere.com", "p@ssw0rd", "John", "Doe"),
                new Account(UUID.fromString("02e0a47c-0bb9-424f-b20a-cd08001dd6a1"), "john@somewhere.com", "Sl@nd1r", "John", "Doe"),
                new Account(UUID.fromString("02e0a47c-0bb9-424f-b20a-cd08001dd6a1"), "john@somewhere.com", null, "John", "Doe"),
                new Account(UUID.fromString("02e0a47c-0bb9-424f-b20a-cd08001dd6a1"), "john@somewhere.com", "p@ssw0rd", "Jane", "Doe"),
                new Account(UUID.fromString("02e0a47c-0bb9-424f-b20a-cd08001dd6a1"), "john@somewhere.com", "p@ssw0rd", "John", "Roe")
            )
            .addEquivalentGroup(
                new Account(null, "john@somewhere.com", "p@ssw0rd", "John", "Doe"),
                new Account(null, "john@somewhere.com", "Sl@nd1r", "John", "Doe"),
                new Account(null, "john@somewhere.com", null, "John", "Doe"),
                new Account(null, "john@somewhere.com", "p@ssw0rd", "Jane", "Doe"),
                new Account(null, "john@somewhere.com", "p@ssw0rd", "John", "Roe")
            )
            .addEquivalentGroup(
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "jane@somewhere.com", "p@ssw0rd", "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "jane@somewhere.com", "Sl@nd1r", "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "jane@somewhere.com", null, "John", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "jane@somewhere.com", "p@ssw0rd", "Jane", "Doe"),
                new Account(UUID.fromString("2c3c4d11-ddc0-4be1-932c-89c0525d3d2e"), "jane@somewhere.com", "p@ssw0rd", "John", "Roe")
            )
        .check();
    }

}
