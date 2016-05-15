package models;


public class Transaction {
    private Double _id;
    private Double parent_id;
    private Double amount;
    private String type;

    public Transaction(Double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public Transaction(Double id, Double amount, String type) {
        this._id = id;
        this.amount = amount;
        this.type = type;
    }

    public Double getId() {
        return _id;
    }

    public String getType() {
        return type;
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
