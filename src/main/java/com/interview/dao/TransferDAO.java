package com.interview.dao;

import com.interview.dao.mapper.TransferResultMapper;
import com.interview.model.Transfer;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TransferResultMapper.class)
public interface TransferDAO {

    @SqlQuery("SELECT * FROM transfers WHERE id = :id")
    Transfer get(@Bind("id") String id);

    @SqlUpdate("INSERT INTO transfers (source_account_id, destination_account_id, amount) " +
            "values (:a.sourceAccountId, :a.destinationAccountId, :a.amount)")
    @GetGeneratedKeys
    @Transaction
    String create(@BindBean("a") Transfer account);

}
