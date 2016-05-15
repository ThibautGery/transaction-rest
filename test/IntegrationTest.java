import org.junit.*;

import play.libs.ws.*;
import play.test.*;

import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.*;
import play.libs.Json;



public class IntegrationTest {


    @Test
    public void testInServer() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/api/ping").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertThat(response.getStatus()).isEqualTo(OK);
                assertThat(response.getHeader("content-type")).startsWith("application/json");
                assertThat(response.asJson()).isEqualTo(Json.toJson("pong"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                assertThat(e.getMessage()).isEqualTo("");
            }
        });
    }

}
