package com.interview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@JsonSnakeCase
public class Transaction {

    @NotEmpty
    private String sourceAccountId;

    @NotEmpty
    private String destinationAccountId;

    @Min(1)
    private Integer amount;

    public Transaction() {
        // Jackson constructor
    }

    public Transaction(String sourceAccountId, String destinationAccountId, Integer amount) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        try {
            this.amount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("amount must be an integer value");
        }
    }

    @JsonIgnore
    @ValidationMethod(message = "source_account_id must be different from destination_account_id")
    public boolean isNotSourceAndDestinationEqual() {
        if (this.sourceAccountId != null && this.destinationAccountId != null)
            return !this.sourceAccountId.equals(this.destinationAccountId);
        else
            return true;
    }

}
