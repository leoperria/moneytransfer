package com.interview.dao;

import com.interview.dao.mapper.TransferResultMapper;
import com.interview.models.Transfer;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TransferResultMapper.class)
public interface TransferDAO {

    @SqlQuery("SELECT * FROM transfers WHERE id = :id")
    Transfer get(@Bind("id") String id);

    @SqlUpdate("INSERT INTO transfers (source_account_id, destination_account_id, amount) " +
            "values (:a.source_account_id, :a.destination_account_id, :a.amount)")
    @GetGeneratedKeys
    @Transaction
    String create(@BindBean("a") Transfer account);

}
