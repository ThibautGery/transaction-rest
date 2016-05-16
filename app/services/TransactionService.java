package services;

import models.Transaction;
import models.TransactionDb;

import javax.inject.Inject;

public class TransactionService {
    @Inject
    private TransactionDb transactionDb;

    public Double sum(Double id) {
        return transactionDb.getLinkedTransaction(id)
                .stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
