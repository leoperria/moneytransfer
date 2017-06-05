package com.interview.client;


import com.interview.model.Account;
import com.interview.model.Transfer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class MoneyTransferClient {

    private final Client httpClient;

    private final String endPoint;

    public MoneyTransferClient(Client httpClient, String endPoint) {
        this.httpClient = httpClient;
        this.endPoint = endPoint;
    }

    public List<Account> getAllAccounts() {
        return httpClient.target(this.endPoint)
                .path("accounts")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Account>>() {
                });
    }

    public Account getAccount(String id) {
        return httpClient.target(this.endPoint)
                .path("accounts/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Account.class);
    }

    public List<Transfer> getAllTransferForAccount(String id) {
        return httpClient.target(this.endPoint)
                .path("accounts/" + id + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Transfer>>() {
                });
    }

    public Transfer getTransfer(String id) {
        return httpClient.target(this.endPoint)
                .path("transfer/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Transfer.class);
    }

    public Transfer transferFunds(Transfer transfer) {
        return httpClient.target(this.endPoint)
                .path("transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transfer, MediaType.APPLICATION_JSON), Transfer.class);
    }

}
