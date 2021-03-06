package com.moneytransfer.model;

public class Account {

    private String id;
    private Integer balance;

    public Account() {
        // Jackson constructor
    }

    public Account(String id, Integer balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
