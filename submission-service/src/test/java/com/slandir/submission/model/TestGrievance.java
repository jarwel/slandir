package com.slandir.submission.model;

import com.proofpoint.json.JsonCodec;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.UUID;

import static com.proofpoint.testing.EquivalenceTester.equivalenceTester;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestGrievance {

    private static final JsonCodec<Grievance> grievanceCodec = JsonCodec.jsonCodec(Grievance.class);

    @Test
    public void testRoundTrip() {
        Grievance expected = new Grievance(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Anonymous", "Someone did something bad.", DateTime.now());
        Grievance actual = grievanceCodec.fromJson(grievanceCodec.toJson(expected));

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getPersonId(), expected.getPersonId());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertTrue(actual.getCreated().isEqual(expected.getCreated()));
    }

    @Test
    public void testEquivalence() {
        equivalenceTester()
            .addEquivalentGroup(
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("749110db-b903-4fe8-912d-d04289b98279"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Someone", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("dd454870-6828-47b6-a248-ad4542361f17"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "This person is a very bad apple.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f7745c74-56aa-45c0-950d-5663aacd879a"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MIN_VALUE))
            )
            .addEquivalentGroup(
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("749110db-b903-4fe8-912d-d04289b98279"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Someone", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("dd454870-6828-47b6-a248-ad4542361f17"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "This person is a very bad apple.", new DateTime(Integer.MAX_VALUE)),
                new Grievance(UUID.fromString("f6800cd6-61a7-4f22-86d1-04df14f3f4a6"), UUID.fromString("c0c48fd0-2f72-4a40-9ff2-4d1f9730a1c4"), UUID.fromString("204f53ad-c6e4-434e-bc07-a4ea6dca6811"), "Anonymous", "Someone did something horrible.", new DateTime(Integer.MIN_VALUE))
            )
        .check();
    }

}
