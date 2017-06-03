package com.interview.dao;

import com.interview.model.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.util.List;


public interface AccountDAO {

    @SqlQuery("SELECT * FROM accounts")
    List<Account> getAllAccounts();

    @SqlQuery("SELECT * FROM accounts WHERE id=:id")
    Account getAccount(@Bind("id") String id);

}
