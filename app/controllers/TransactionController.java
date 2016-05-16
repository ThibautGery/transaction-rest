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
import services.TransactionService;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionController extends Controller {

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private TransactionDb transactionDb;

    @Inject
    private TransactionService transactionService;

    @Inject
    private Validator validator;

    @BodyParser.Of(BodyParser.Json.class)
    public Result putTransaction(Double id) {
        try {
            JsonNode json = request().body().asJson();
            Transaction transaction = objectMapper.treeToValue(json, Transaction.class);
            transaction.setId(id);
            Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(transaction);
            if(!constraintViolations.isEmpty()) {
                List<String> errorMessages = constraintViolations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList());

                return badRequest(Json.toJson(errorMessages));
            }
            transactionDb.putTransaction(transaction);
            return ok(Json.toJson(transaction));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return badRequest(Json.toJson(ex.getMessage()));
        } catch(IllegalArgumentException ex) {
            ex.printStackTrace();
            return badRequest(Json.toJson(ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return internalServerError(Json.toJson(ex.getMessage()));
        }

    }

    public Result getTransaction(Double id) {
        try {
            Optional<Transaction> transaction = transactionDb.getTransaction(id);
            if(!transaction.isPresent()) {
                return notFound(Json.toJson("The transaction was not found"));
            }
            return ok(Json.toJson(transaction.get()));
        }  catch (Exception ex) {
            ex.printStackTrace();
            return internalServerError(Json.toJson(ex.getMessage()));
        }
    }


    public Result getTransactionByType(String type) {
        try {
            List<Double> transactionsId = transactionDb.getTransaction(type);
            return ok(Json.toJson(transactionsId));
        }  catch (Exception ex) {
            ex.printStackTrace();
            return internalServerError(Json.toJson(ex.getMessage()));
        }
    }

    public Result getSum(Double id) {
        try {
            Double result = transactionService.sum(id);
            return ok(Json.toJson(result));
        }  catch (Exception ex) {
            ex.printStackTrace();
            return internalServerError(Json.toJson(ex.getMessage()));
        }
    }

}
