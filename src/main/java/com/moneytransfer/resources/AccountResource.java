package com.moneytransfer.resources;

import com.codahale.metrics.annotation.Timed;
import com.moneytransfer.dao.AccountDAO;
import com.moneytransfer.dao.TransferDAO;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.Transfer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
    @Timed
    @Path("ping")
    public String ping() {
        return "Pong";
    }

    @GET
    @Timed
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @GET
    @Timed
    @Path("/accounts/{id}")
    public Account getAccount(@PathParam("id") String id) {
        Account account = accountDAO.getAccount(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return account;
    }

    @GET
    @Timed
    @Path("/accounts/{id}/transfers")
    public List<Transfer> getAllTransferForAccount(@PathParam("id") String id) {
        Account account = accountDAO.getAccount(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transferDAO.getAllTransferForAccount(id);
    }

}
