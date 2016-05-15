package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

public class Transaction {
    @Constraints.Required
    private Double _id;

    private Double parent_id;

    @Constraints.Required
    private Double amount;

    @Constraints.Required
    private String type;

    public Transaction() {
    }

    public Transaction(Double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public Transaction(Double id, Double amount, String type) {
        this._id = id;
        this.amount = amount;
        this.type = type;
    }
    @JsonIgnore
    public Double getId() {
        return _id;
    }

    public String getType() {
        return type;
    }

    public Double getParent_id() {
        return parent_id;
    }

    public Double getAmount() {
        return amount;
    }

    @JsonIgnore
    public void setId(Double id) {
        this._id = id;
    }

    public void setParent_id(Double parent_id) {
        this.parent_id = parent_id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (parent_id != null ? !parent_id.equals(that.parent_id) : that.parent_id != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (parent_id != null ? parent_id.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
