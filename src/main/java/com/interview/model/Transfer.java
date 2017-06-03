package com.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonSnakeCase
public class Transfer {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return Objects.equal(getId(), transfer.getId()) &&
                Objects.equal(getSourceAccountId(), transfer.getSourceAccountId()) &&
                Objects.equal(getDestinationAccountId(), transfer.getDestinationAccountId()) &&
                Objects.equal(getAmount(), transfer.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, sourceAccountId, destinationAccountId, amount);
    }

    @JsonIgnore
    @ValidationMethod(message = "source_account_id must be different from destination_account_id")
    public boolean isNotSourceAndDestinationEqual() {
        return !(this.sourceAccountId != null && this.destinationAccountId != null) || !this.sourceAccountId.equals(this.destinationAccountId);
    }

}
