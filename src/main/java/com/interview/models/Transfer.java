package com.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonSnakeCase
public class Transfer {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty
    private String id;

    @NotEmpty
    private String sourceAccountId;

    @NotEmpty
    private String destinationAccountId;

    @NotNull
    @Min(1)
    private Integer amount;

    public Transfer() {
        // Jackson constructor
    }

    public Transfer(String id, String sourceAccountId, String destinationAccountId, Integer amount) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;

        Transfer transfer = (Transfer) o;

        if (!getId().equals(transfer.getId())) return false;
        if (!getSourceAccountId().equals(transfer.getSourceAccountId())) return false;
        if (!getDestinationAccountId().equals(transfer.getDestinationAccountId())) return false;
        return getAmount().equals(transfer.getAmount());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSourceAccountId().hashCode();
        result = 31 * result + getDestinationAccountId().hashCode();
        result = 31 * result + getAmount().hashCode();
        return result;
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
