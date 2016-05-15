package models;

import exceptions.TransactionNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;




public class TransactionDbTest {

    private TransactionDb transactionDb;

    @Before
    public void setUp() throws Exception {
        transactionDb = new TransactionDb();
    }

    @Test
    public void addTransaction_oneTransaction_1() {
        //given
        Transaction transaction = new Transaction(1.0,345.0, "shopping");

        //when
        transactionDb.putTransaction(transaction);

        //then
        assertThat(transactionDb.transactions).hasSize(1);
        assertThat(transactionDb.transactions).containsValues(new Transaction(1.0, 345.0, "shopping"));
    }

    @Test
    public void addTransaction_twodifferentTransactions_2() {
        //given
        Transaction transaction1 = new Transaction(1.0,345.0, "shopping");
        Transaction transaction2 = new Transaction(2.0,346.0, "cars");

        //when
        transactionDb.putTransaction(transaction1);
        transactionDb.putTransaction(transaction2);

        //then
        assertThat(transactionDb.transactions).hasSize(2);
        assertThat(transactionDb.transactions)
                .containsValues(new Transaction(1.0, 345.0, "shopping"),
                        new Transaction(2.0, 346.0, "cars"));
    }

    @Test
    public void addTransaction_oneTransactionTwoTime_1() {
        //given
        Transaction transaction1 = new Transaction(1.0,345.0, "shopping");
        Transaction transaction2 = new Transaction(1.0,346.0, "cars");

        //when
        transactionDb.putTransaction(transaction1);
        transactionDb.putTransaction(transaction2);

        //then
        assertThat(transactionDb.transactions).hasSize(1);
        assertThat(transactionDb.transactions)
                .containsValues(new Transaction(1.0, 346.0, "cars"));
    }

    @Test
    public void getTransaction_withoneTransaction_1() {
        //given
        Transaction transaction = new Transaction(1.0,345.0, "shopping");
        transactionDb.putTransaction(transaction);

        //when
        Transaction result = transactionDb.getTransaction(1.0);

        //then
        assertThat(result).isEqualTo(transaction);
    }


    @Test(expected= TransactionNotFoundException.class)
    public void getTransaction_withoutTransaction_throwError() {
        transactionDb.getTransaction(1.0);
    }
}