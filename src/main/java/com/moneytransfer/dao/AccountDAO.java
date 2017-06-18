package com.moneytransfer.dao;

import com.moneytransfer.model.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;


public interface AccountDAO {

    @SqlQuery("SELECT * FROM accounts")
    List<Account> getAllAccounts();

    @SqlQuery("SELECT * FROM accounts WHERE id=:id")
    Account getAccount(@Bind("id") String id);

    @SqlUpdate("UPDATE accounts SET balance=:a.balance WHERE id=:a.id")
    int updateAccount(@BindBean("a") Account account);

}
