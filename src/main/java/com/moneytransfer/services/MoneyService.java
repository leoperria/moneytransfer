package com.moneytransfer.services;


import com.moneytransfer.ServiceException;
import com.moneytransfer.dao.AccountDAO;
import com.moneytransfer.dao.TransferDAO;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.Transfer;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

public abstract class MoneyService {

    @CreateSqlObject
    abstract TransferDAO transferDAO();

    @CreateSqlObject
    abstract AccountDAO accountDAO();

    @Transaction
    public Transfer processTransfer(Transfer transfer) {

        if (transfer.getSourceAccountId().equals(transfer.getDestinationAccountId())) {
            throw new ServiceException("Source account must be different from destination account");
        }

        Account sourceAccount = accountDAO().getAccount(transfer.getSourceAccountId());
        if (sourceAccount == null) {
            throw new ServiceException("Source account not found");
        }

        Account destinationAccount = accountDAO().getAccount(transfer.getDestinationAccountId());
        if (destinationAccount == null) {
            throw new ServiceException("Destination account not found");
        }

        if (sourceAccount.getBalance() < transfer.getAmount()) {
            throw new ServiceException("Source account has not enough funds available for the transfer");
        }

        // Make the actual transfer
        sourceAccount.setBalance(sourceAccount.getBalance() - transfer.getAmount());
        accountDAO().updateAccount(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + transfer.getAmount());
        accountDAO().updateAccount(destinationAccount);

        // Store the fund transfer
        final String transferId = transferDAO().create(transfer);

        return transferDAO().get(transferId);
    }

}
