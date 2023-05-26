package com.playframework.cric.modules;

import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Singleton;

import com.playframework.cric.responses.Response;
import com.playframework.cric.exceptions.MyException;

@Singleton
public class ErrorHandler implements HttpErrorHandler {
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return CompletableFuture.completedFuture(Results.status(statusCode, "A client error occurred: " + message));
    }

    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        Integer httpsStatusCode = 500;
        String content = exception.getMessage();

        if((exception instanceof MyException))
        {
            MyException myException = (MyException) exception;
            httpsStatusCode = myException.getHttpStatusCode();
            content = myException.getDescription();
        }

        Response response = new Response(content);

        return CompletableFuture.completedFuture(Results.status(httpsStatusCode, Json.toJson(response)));
    }
}
