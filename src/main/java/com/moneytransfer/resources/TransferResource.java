package com.moneytransfer.resources;

import com.codahale.metrics.annotation.Timed;
import com.moneytransfer.ServiceException;
import com.moneytransfer.dao.TransferDAO;
import com.moneytransfer.model.Transfer;
import com.moneytransfer.services.MoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource {

    private final static Logger logger = LoggerFactory.getLogger(TransferResource.class);

    final private MoneyService moneyService;
    final private TransferDAO transferDAO;

    public TransferResource(MoneyService moneyService, TransferDAO transferDAO) {
        this.moneyService = moneyService;
        this.transferDAO = transferDAO;
    }

    @POST
    @Timed
    @Path("/transfer")
    public Transfer transferFunds(@Valid Transfer transfer)  {
        try {
            return moneyService.processTransfer(transfer);
        } catch (ServiceException e) {
            throw new WebApplicationException(e.getMessage(), e, Response.Status.PRECONDITION_FAILED);
        }
    }

    @GET
    @Timed
    @Path("/transfer/{id}")
    public Transfer getTransfer(@PathParam("id") @NotNull String id) {
        final Transfer transfer = transferDAO.get(id);
        if (transfer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transfer;
    }

}
