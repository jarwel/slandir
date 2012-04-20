package com.slandir.account.resource;

import com.slandir.account.dao.AccountDao;
import com.slandir.account.dao.InMemoryAccountDao;
import com.slandir.account.model.Account;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class TestAccountResource {

    private AccountResource accountResource;

    //Mocked dependencies
    private AccountDao mockAccountDao;
    
    @BeforeMethod
    public void setUp() {
        mockAccountDao = mock(InMemoryAccountDao.class);
        accountResource = new AccountResource(mockAccountDao);
    }
    
    @AfterMethod
    public void tearDown() {
        accountResource = null;
        mockAccountDao = null;
    }

    @Test
    public void testPost() {

        String email = "john@somewhere.com";
        Account account = new Account(UUID.randomUUID(), email, "12345678", "John", "Doe");

        doReturn(null).when(mockAccountDao).fetch(email);

        Response response = accountResource.post(account);

        verify(mockAccountDao).save(account);

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testPostExists() {

        String email = "john@somewhere.com";
        Account account = new Account(UUID.randomUUID(), email, null, "John", "Doe");
        
        doReturn(account).when(mockAccountDao).fetch(email);
        
        Response response = accountResource.post(account);
        
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        
    }
}
