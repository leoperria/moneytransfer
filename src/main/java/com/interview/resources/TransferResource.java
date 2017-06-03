package com.interview.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.dao.TransferDAO;
import com.interview.model.Transfer;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource {

    private final static Logger logger = LoggerFactory.getLogger(TransferResource.class);

    private TransferDAO transferDao;

    public TransferResource(TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    /*
      Rules:
      - sourceAccountId and destinationAccountId must be two valid account ids
      - sourceAccountId can't have a negative balance after the transaction is committed
     */

    @POST
    @Transaction
    @Path("/transfer")
    public Transfer createTransaction(@Valid Transfer transfer) throws JsonProcessingException {
        logger.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(transfer));
        final String transferId = transferDao.create(transfer);
        return transferDao.get(transferId);
    }

    @GET
    @Path("/transfer/{id}")
    public Transfer getTransfer(@PathParam("id") String id) {
        final Transfer transfer = transferDao.get(id);
        if (transfer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transfer;
    }

}
