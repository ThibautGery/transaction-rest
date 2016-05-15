package models;


import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Singleton
final public class TransactionDb {
    protected Map<Double,Transaction> transactions;

    TransactionDb() {
        transactions = new HashMap<>();
    }

    public void putTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);

    }

    public Optional<Transaction> getTransaction(Double id) {
        return ofNullable(transactions.get(id));
    }

    public List<Double> getTransaction(String type) {
        return transactions.values().stream()
                .filter(t1 -> t1.getType().equals(type))
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }
}
