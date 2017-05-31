package com.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    final static Logger logger = LoggerFactory.getLogger(TransactionResource.class);


    private ServiceDAO dao;

    public TransactionResource(ServiceDAO dao) {
        this.dao = dao;
    }

    /*
      Rules:
      - sourceAccountId and destinationAccountId must be two valid account ids
      - sourceAccountId can't have a negative balance after the transaction is committed
     */

    @POST
    @Path("/transaction")
    public Transaction createTransaction(@Valid Transaction transaction) throws JsonProcessingException {
        logger.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
        return null;
    }

    @GET
    @Path("/transaction/{id}")
    public Transaction getAllAccounts() {
        return null;
    }

}
