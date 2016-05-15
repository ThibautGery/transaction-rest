package models;

import exceptions.TransactionNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    public void getTransactionById_withoneTransaction_1() {
        //given
        Transaction transaction = new Transaction(1.0,345.0, "shopping");
        transactionDb.putTransaction(transaction);

        //when
        Transaction result = transactionDb.getTransaction(1.0);

        //then
        assertThat(result).isEqualTo(transaction);
    }


    @Test(expected= TransactionNotFoundException.class)
    public void getTransactionById_withoutTransaction_throwError() {
        transactionDb.getTransaction(1.0);
    }


    @Test
    public void getTransactionByType_withNoTransactionOfTheType_0() {
        //given
        transactionDb.putTransaction(new Transaction(1.0,345.0, "shopping"));
        transactionDb.putTransaction(new Transaction(2.0,346.0, "car"));

        //when
        List<Double> results = transactionDb.getTransaction("housing");

        //then
        assertThat(results).isEmpty();
    }


    @Test
    public void getTransactionByType_withTransaction_0() {
        //given
        transactionDb.putTransaction(new Transaction(1.0,345.0, "shopping"));
        transactionDb.putTransaction(new Transaction(2.0,346.0, "car"));
        transactionDb.putTransaction(new Transaction(3.0,347.0, "shopping"));


        //when
        List<Double> results = transactionDb.getTransaction("shopping");

        //then
        assertThat(results).hasSize(2);
        assertThat(results).contains(1.0, 3.0);
    }
}