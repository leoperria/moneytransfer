package com.interview.resources;

import com.interview.dao.AccountDAO;
import com.interview.dao.TransferDAO;
import com.interview.model.Account;
import com.interview.model.Transfer;

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

    final private AccountDAO accountDAO;
    final private TransferDAO transferDAO;

    public AccountResource(AccountDAO accountDAO, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @GET
    @Path("ping")
    public String ping() {
        return "Pong";
    }

    @GET
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @GET
    @Path("/accounts/{id}")
    public Account getAccount(@PathParam("id") String id) {
        Account account = accountDAO.getAccount(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return account;
    }

    @GET
    @Path("/accounts/{id}/transfers")
    public List<Transfer> getAllTransferForAccount(@PathParam("id") String id) {
        Account account = accountDAO.getAccount(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transferDAO.getAllTransferForAccount(id);
    }

}
