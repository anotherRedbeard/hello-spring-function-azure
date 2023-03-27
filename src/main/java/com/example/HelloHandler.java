package com.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.function.Function;

@Component
public class HelloHandler {

    @Autowired
    private Function<String, String> uppercase;

    @FunctionName("hello")
    public String execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
       
                Logger logger = context.getLogger();
                //save result in variable
                String result = this.uppercase.apply(request.getBody().get());
                logger.info("here is the output:  " + result);
                return result;
    }

    @FunctionName("sbprocessor")
        public void serviceBusProcess(
                @ServiceBusQueueTrigger(name = "msg", queueName = "demo-queue", connection = "SBConnectionString") String message, 
            final ExecutionContext context) {

                Logger logger = context.getLogger();
                String adjustedMessage = this.uppercase.apply(message);
                logger.info(adjustedMessage);
        }
}
