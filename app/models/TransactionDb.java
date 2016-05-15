package models;


import exceptions.TransactionNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Double> getTransaction(String type) {
        return transactions.values().stream()
                .filter(t1 -> t1.getType().equals(type))
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }
}
