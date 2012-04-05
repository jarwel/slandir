package com.slandir.account.resource;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.slandir.account.dao.AccountDao;
import com.slandir.account.dao.InMemoryAccountDao;
import com.slandir.account.model.Account;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("v1/account")
public class AccountResource {
    
    private final AccountDao accountDao;

    @Inject
    public AccountResource(AccountDao accountDao) {
        this.accountDao = Preconditions.checkNotNull(accountDao);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
            Account account = accountDao.fetch(email);
            String digestedPassword = digest(password);
            if(account != null && account.getPassword().equals(digestedPassword)) {
                return Response.ok(account).build();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Account account) {
        try {
            if(accountDao.fetch(account.getEmail()) == null) {
                String digestedPassword = digest(account.getPassword());
                Account digestedAccount = new Account(account.getId(), account.getEmail(), digestedPassword, account.getFirstName(), account.getLastName());
                accountDao.save(digestedAccount);
                return Response.ok().build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    private String digest(String value) throws NoSuchAlgorithmException {
        byte[] md5 = MessageDigest.getInstance("MD5").digest(value.getBytes(Charsets.UTF_8));
        byte[] base64 = Base64.encode(md5);
        return new String(base64);
    }

}
