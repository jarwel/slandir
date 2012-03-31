package com.slandir.submission.resource;

import com.google.common.collect.Lists;
import com.slandir.submission.dao.GrievanceDao;
import com.slandir.submission.model.Grievance;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class TestGrievanceResource {

    private GrievanceResource grievanceResource;

    //Mocked dependencies
    private GrievanceDao mockGrievanceDao;
    
    @BeforeMethod
    public void setUp() {
        mockGrievanceDao = mock(GrievanceDao.class);
        grievanceResource = new GrievanceResource(mockGrievanceDao);
    }
    
    @AfterMethod
    public void tearDown() {
        grievanceResource = null;
        mockGrievanceDao = null;
    }
    
    @Test
    public void testGetWithPersonId() {
        UUID personId = UUID.randomUUID();
        Grievance grievance = new Grievance(UUID.randomUUID(), UUID.randomUUID(), personId, "Anonymous", "Someone did something horrible.", new Date(0));

        List<Grievance> expected = Lists.newArrayList(grievance);
        doReturn(expected).when(mockGrievanceDao).fetchByPerson(personId);
        
        Response response = grievanceResource.get(personId, null);
        List<Grievance> actual = (List<Grievance>)response.getEntity();

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(actual, expected);
    }

    @Test
    public void testGetWithAccountId() {
        UUID accountId = UUID.randomUUID();
        Grievance grievance = new Grievance(UUID.randomUUID(), accountId, UUID.randomUUID(), "Anonymous", "Someone did something horrible.", new Date(0));

        List<Grievance> expected = Lists.newArrayList(grievance);
        doReturn(expected).when(mockGrievanceDao).fetchByAccount(accountId);

        Response response = grievanceResource.get(null, accountId);
        List<Grievance> actual = (List<Grievance>)response.getEntity();

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(actual, expected);
    }

    @Test
    public void testGetWithBoth() {
        Response response = grievanceResource.get(UUID.randomUUID(), UUID.randomUUID());

        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testDelete() {
        UUID grievanceId = UUID.randomUUID();

        Response response = grievanceResource.delete(grievanceId);

        verify(mockGrievanceDao).remove(grievanceId);

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }
}
