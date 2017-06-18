package com.moneytransfer.resources;

import com.moneytransfer.client.MoneyTransferClient;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.Transfer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutoTestResource {

    final private MoneyTransferClient moneyTransferClient;

    public AutoTestResource(MoneyTransferClient moneyTransferClient) {
        this.moneyTransferClient = moneyTransferClient;
    }

    // Client example

    @GET
    @Path("/autotest")
    public List<Transfer> getAllAccounts() {

        Account account = moneyTransferClient.getAccount("1");
        if (!account.getId().equals("1")) {
            throw new AssertionError("Account 1 not loaded");
        }

        Transfer transfer = new Transfer();
        transfer.setSourceAccountId("1");
        transfer.setDestinationAccountId("2");
        transfer.setAmount("50");
        moneyTransferClient.transferFunds(transfer);

        return moneyTransferClient.getAllTransferForAccount("1");
    }

}
