package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Transaction;
import models.TransactionDb;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class TransactionController extends Controller {

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private TransactionDb transactionDb;


    @BodyParser.Of(BodyParser.Json.class)
    public Result putTransaction(Double id) {
        try {
            JsonNode json = request().body().asJson();
            Transaction transaction = objectMapper.treeToValue(json, Transaction.class);
            transaction.setId(id);
            transactionDb.putTransaction(transaction);
            return ok(Json.toJson(transaction));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return badRequest(Json.toJson(ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return internalServerError(Json.toJson(ex.getMessage()));
        }

    }

}
