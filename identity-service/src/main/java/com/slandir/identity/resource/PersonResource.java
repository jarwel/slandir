package com.slandir.identity.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.slandir.identity.dao.PersonDao;
import com.slandir.identity.model.Person;
import com.slandir.identity.type.State;
import com.slandir.identity.model.Simulacrum;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("v1/person")
public class PersonResource {

    private final PersonDao personDao;
    
    @Inject
    public PersonResource(PersonDao personDao) {
        this.personDao = Preconditions.checkNotNull(personDao, "personDao cannot be null");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") UUID id) {
        Person person = personDao.get(id);
        
        if(person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(person).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
        @QueryParam("firstName") String firstName,
        @QueryParam("middleName") String middleName,
        @QueryParam("lastName") String lastName,
        @QueryParam("state") String state
    ) {
        
        State stateType = !StringUtils.isBlank(state) ? State.valueOf(state.toUpperCase()) : null;
        List<Person> persons = Lists.newArrayList(personDao.fetch(firstName, middleName, lastName, stateType));
        return Response.ok(persons).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Person person) {
        personDao.save(person);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(Person person) {
        Person original = personDao.get(person.getId());
        personDao.save(new Simulacrum(original).assimilate(person).toPerson());
        return Response.ok().build();
    }
}
