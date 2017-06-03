package com.interview.resources;

import com.interview.dao.AccountDAO;
import com.interview.model.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * http://www.dropwizard.io/1.0.6/docs/manual/core.html#resources
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountDAO dao;

    public AccountResource(AccountDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("ping")
    public String ping() {
        return "Pong";
    }

    @GET
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        return dao.getAllAccounts();
    }

    @GET
    @Path("/accounts/{id}")
    public Account getAllAccounts(@PathParam("id") String id) {
        Account account = dao.getAccount(id);
        if (account == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return account;
    }

}
