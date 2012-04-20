package com.slandir.submission.dao;

import com.google.common.collect.Lists;
import com.slandir.submission.model.Grievance;
import org.joda.time.DateTime;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestInMemoryGrievanceDao {

    private GrievanceDao grievanceDao;

    @BeforeMethod
    public void setUp() {
        this.grievanceDao = new InMemoryGrievanceDao();
    }

    @AfterMethod
    public void tearDown() {
        grievanceDao = null;
    }

    @Test
    public void testFetchByAccount() {
        UUID accountId = UUID.randomUUID();
        
        Grievance grievance = new Grievance(UUID.randomUUID(), accountId, UUID.randomUUID(), "Anonymous", "Grievance text goes here.", DateTime.now());
        
        grievanceDao.save(grievance);

        List<Grievance> expected = Lists.newArrayList(grievance);
        List<Grievance> actual = grievanceDao.fetchByAccount(accountId);
        
        assertEquals(actual, expected);
    }

    @Test
    public void testFetchByPerson() {
        UUID personId = UUID.randomUUID();

        Grievance grievance = new Grievance(UUID.randomUUID(), UUID.randomUUID(), personId, "Anonymous", "Grievance text goes here.", DateTime.now());

        grievanceDao.save(grievance);

        List<Grievance> expected = Lists.newArrayList(grievance);
        List<Grievance> actual = grievanceDao.fetchByPerson(personId);

        assertEquals(actual, expected);
    }

    @Test
    public void testRemove() {
        UUID grievanceId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID personId = UUID.randomUUID();
        
        Grievance grievance = new Grievance(grievanceId, accountId, personId, "Anonymous", "Grievance text goes here.", DateTime.now());

        grievanceDao.save(grievance);
        grievanceDao.remove(grievanceId);

        assertTrue(grievanceDao.fetchByAccount(accountId).isEmpty());
        assertTrue(grievanceDao.fetchByPerson(personId).isEmpty());
    }

}
