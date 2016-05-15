package exceptions;


public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Transaction Not found");
    }
}
