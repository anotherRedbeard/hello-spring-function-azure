package com.example;

import com.example.model.Greeting;
import com.example.model.User;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class HelloHandler {

        //@Autowired
        private Function<String, String> uppercase;

        //pulling in uppercase function from HelloFunction.java without using @Autowired
        //private final Function<String, String> uppercase = payload -> payload.toUpperCase();
        
        @FunctionName("hello")
        public HttpResponseMessage execute(
                @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<User>> request,
                ExecutionContext context) {
                User user = request.getBody()
                        .filter((u -> u.getName() != null))
                        .orElseGet(() -> new User(
                                request.getQueryParameters()
                                        .getOrDefault("name", "world")));

                context.getLogger().info("Greeting user name: " + user.getName());
                context.getLogger().info("UPPERCASED Greeting user name: " + uppercase.apply(user.getName()));
                /*return request
                        .createResponseBuilder(HttpStatus.OK)
                        .body(handleRequest(user, context))
                        .header("Content-Type", "application/json")
                        .build();*/

                return request
                        .createResponseBuilder(HttpStatus.OK)
                        .body(new Greeting("Hello, " + this.uppercase.apply(user.getName()) + "!\n"))
                        .header("Content-Type", "application/json")
                        .build();
        }
}
