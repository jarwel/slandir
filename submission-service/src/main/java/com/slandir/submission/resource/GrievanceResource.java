package com.slandir.submission.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.slandir.submission.dao.GrievanceDao;
import com.slandir.submission.model.Grievance;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("v1/grievance")
public class GrievanceResource {

    private final GrievanceDao grievanceDao;

    @Inject
    public GrievanceResource(GrievanceDao grievanceDao) {
        this.grievanceDao = Preconditions.checkNotNull(grievanceDao, "grievanceDao cannot be null");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("personId") UUID personId, @QueryParam("accountId") UUID accountId) {
        if(personId != null && accountId == null) {
            List<Grievance> grievances = Lists.newArrayList(grievanceDao.fetchByPerson(personId));
            return Response.ok(grievances).build();
        }

        if(accountId != null && personId == null) {
            List<Grievance> grievances = Lists.newArrayList(grievanceDao.fetchByAccount(accountId));
            return Response.ok(grievances).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Grievance grievance) {
        grievanceDao.save(grievance);
        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE
    public Response delete(@PathParam("id") UUID id) {
        grievanceDao.remove(id);
        return Response.ok().build();
    }
}
