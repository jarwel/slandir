package com.slandir.account.dao;

import com.slandir.account.model.Account;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;


public class TestAccountDao {

    private AccountDao accountDao;

    @BeforeMethod
    public void setUp() {
        accountDao = new AccountDao();
    }

    @Test
    public void testFetch() {
        Account expected = new Account(UUID.randomUUID(), "someone@somewhere.com", "p@ssw0rd", "John", "Doe");

        accountDao.save(expected);

        Account actual = accountDao.fetch(expected.getEmail());

        assertEquals(actual, expected);
    }

    @Test
    public void testSave() {
        Account expected = new Account(UUID.randomUUID(), "someone@somewhere.com", "p@ssw0rd", "John", "Doe");
        
        accountDao.save(expected);
        
        Account actual = accountDao.fetch(expected.getEmail());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
    }

    @AfterMethod
    public void tearDown() {
        accountDao = null;
    }
}
