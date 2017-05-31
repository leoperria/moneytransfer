package com.interview;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * http://www.dropwizard.io/1.0.6/docs/manual/jdbi.html
 * http://jdbi.org/dbi_handle_and_statement/
 */

@RegisterMapper(AccountResultMapper.class)
public class ServiceDAO {

    private DBI dbi;

    public ServiceDAO(DBI dbi) {
        this.dbi = dbi;
    }


    public List<Account> getAllAccounts() {
        try (Handle h = dbi.open()) {
            return h.createQuery("SELECT * FROM accounts;")
                    .mapTo(Account.class)
                    .list();
        }
    }

    public Account getAccount(String id) {
        try (Handle h = dbi.open()) {
            return h.createQuery("SELECT * FROM accounts " +
                    " WHERE id=:id;")
                    .bind("id", id)
                    .mapTo(Account.class)
                    .first();
        }
    }


}
