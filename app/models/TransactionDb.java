package models;


import exceptions.TransactionNotFoundException;

import java.util.HashMap;
import java.util.Map;

final public class TransactionDb {
    protected Map<Double,Transaction> transactions;

    TransactionDb() {
        transactions = new HashMap<>();
    }

    public void putTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public Transaction getTransaction(Double id) {
        Transaction transaction = transactions.get(id);
        if(transaction == null)
            throw new TransactionNotFoundException();
        return transaction;
    }
}
