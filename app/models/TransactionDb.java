package models;


import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Singleton
final public class TransactionDb {
    protected Map<Double,Transaction> transactions;

    TransactionDb() {
        transactions = new HashMap<>();
    }

    public void putTransaction(Transaction transaction) {
        synchronized (this) {
            transactions.put(transaction.getId(), transaction);
        }
    }

    public Optional<Transaction> getTransaction(Double id) {
        synchronized (this) {
            return ofNullable(transactions.get(id));
        }
    }

    public List<Double> getTransaction(String type) {
        synchronized (this) {
            return transactions.values().stream()
                    .filter(t1 -> t1.getType().equals(type))
                    .map(Transaction::getId)
                    .collect(Collectors.toList());
        }
    }

    public List<Transaction> getLinkedTransaction(Double id) {
        List<Transaction> result = new ArrayList<>();
        Optional<Double> idToCheck = ofNullable(id);
        synchronized (this) {
            while (idToCheck.isPresent()) {
                Optional<Transaction> currentOpt = getTransaction(idToCheck.get());
                idToCheck = empty();
                if (currentOpt.isPresent()) {
                    Transaction current = currentOpt.get();
                    result.add(current);
                    if (!isParentInArray(result, current)) {
                        idToCheck = ofNullable(current.getParent_id());
                    }
                }
            }
            return result;
        }
    }

    private boolean isParentInArray(List<Transaction> transactions, Transaction current) {
        return transactions.stream()
                .anyMatch(t -> t.getId() != null
                        && t.getId().equals(current.getParent_id()));
    }

}
