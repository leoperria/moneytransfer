package com.interview.dao.mapper;

import com.interview.models.Transfer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferResultMapper implements ResultSetMapper<Transfer> {

    @Override
    public Transfer map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Transfer(r.getString("id"), r.getString("source_account_id"), r.getString("destination_account_id"), r.getInt("amount"));
    }
}
