package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Transaction;
import models.TransactionDb;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.TestServer;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class TransactionControllerTest {
    private static Application fakeApp = fakeApplication();
    private static TestServer server;
    private static int port;
    private static JsonNodeFactory factory;
    private static TransactionDb transactionDb;

    @BeforeClass
    public static void setup() throws ClassNotFoundException {
        port = 3333;
        server = testServer(port, fakeApp);
        factory = JsonNodeFactory.instance;
        transactionDb = fakeApp.injector().instanceOf(TransactionDb.class);
    }

    @Test
    public void testInServer() throws Exception {

        running(server, () -> {
            try {
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
                assertThat(e.getMessage()).isEqualTo("");
            }
        });
    }

}