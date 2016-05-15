package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class SystemController extends Controller {


    public Result ping() {
        return ok(Json.toJson("pong"));
    }

}
