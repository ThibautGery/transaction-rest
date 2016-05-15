package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Transaction;
import models.TransactionDb;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.TestServer;
import play.test.WithServer;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class TransactionControllerTest extends WithServer {

    private static JsonNodeFactory factory;
    private static TransactionDb transactionDb;

    @Before
    public void setup() throws ClassNotFoundException {
        factory = JsonNodeFactory.instance;
        transactionDb = this.app.injector().instanceOf(TransactionDb.class);
    }

    @Test
    public void putTransaction_correctParameters_200() throws Exception {
        //given
        WSClient ws = WS.newClient(port);
        ObjectNode input = new ObjectNode(factory);
        input.put("amount", 456.0);
        input.put("type", "shopping");
        input.put("parent_id", 1.0);

        //when
        CompletionStage<WSResponse> completionStage = ws.url("/api/transactions/34").put(input);
        WSResponse response = completionStage.toCompletableFuture().get();
        ws.close();

        //then
        assertThat(response.getStatus()).isEqualTo(OK);
        assertThat(response.getHeader("content-type")).startsWith("application/json");
        assertThat(response.asJson()).isEqualTo(input);
        Optional<Transaction> transaction = transactionDb.getTransaction(34.0);
        assertThat(transaction.isPresent()).isTrue();
        assertThat(transaction.get().getAmount()).isEqualTo(456.0);
    }

    @Test
    public void putTransaction_withoutTypeParameters_400() throws Exception {
        //given
        WSClient ws = WS.newClient(port);
        ObjectNode input = new ObjectNode(factory);
        input.put("amount", 456.0);
        input.put("parent_id", 1.0);

        //when
        CompletionStage<WSResponse> completionStage = ws.url("/api/transactions/34").put(input);
        WSResponse response = completionStage.toCompletableFuture().get();
        ws.close();

        //then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
        Optional<Transaction> transaction = transactionDb.getTransaction(34.0);
        assertThat(transaction.isPresent()).isFalse();
    }

    @Test
    public void putTransaction_withoutAmount_400() throws Exception {
        //given
        WSClient ws = WS.newClient(port);
        ObjectNode input = new ObjectNode(factory);
        input.put("type", "shopping");
        input.put("parent_id", 1.0);

        //when
        CompletionStage<WSResponse> completionStage = ws.url("/api/transactions/34").put(input);
        WSResponse response = completionStage.toCompletableFuture().get();
        ws.close();

        //then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
        Optional<Transaction> transaction = transactionDb.getTransaction(34.0);
        assertThat(transaction.isPresent()).isFalse();
    }


    @Test
    public void putTransaction_withoutParentIdParameters_200() throws Exception {
        //given
        WSClient ws = WS.newClient(port);
        ObjectNode input = new ObjectNode(factory);
        input.put("amount", 456.0);
        input.put("type", "shopping");

        //when
        CompletionStage<WSResponse> completionStage = ws.url("/api/transactions/34").put(input);
        WSResponse response = completionStage.toCompletableFuture().get();
        ws.close();

        //then
        assertThat(response.getStatus()).isEqualTo(OK);
        assertThat(response.getHeader("content-type")).startsWith("application/json");
        Optional<Transaction> transaction = transactionDb.getTransaction(34.0);
        assertThat(transaction.isPresent()).isTrue();
        assertThat(transaction.get().getAmount()).isEqualTo(456.0);
    }


}