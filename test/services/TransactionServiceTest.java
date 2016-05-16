package services;

import models.Transaction;
import models.TransactionDb;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;


public class TransactionServiceTest extends WithApplication {

    private TransactionDb transactionDb;
    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionDb = this.app.injector().instanceOf(TransactionDb.class);
        transactionService = this.app.injector().instanceOf(TransactionService.class);
    }
    @Test
    public void sum_noTransaction_0() {
        //given

        //when
        Double result = transactionService.sum(45.0);

        //then
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void sum_4Transaction_0() {
        //given
        transactionDb.putTransaction(new Transaction(1.0,0.1, "shopping"));
        transactionDb.putTransaction(new Transaction(2.0,1.0, "shopping"));
        transactionDb.putTransaction(new Transaction(3.0,10.0, "shopping", 4.0));
        transactionDb.putTransaction(new Transaction(4.0,100.0, "shopping", 1.0));
        transactionDb.putTransaction(new Transaction(5.0,1000.0, "shopping", 3.0));

        //when
        Double result = transactionService.sum(5.0);

        //then
        assertThat(result).isEqualTo(1110.1);
    }
}