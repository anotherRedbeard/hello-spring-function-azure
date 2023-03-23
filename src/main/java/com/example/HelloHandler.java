package com.example;

import java.util.Optional;
import java.util.function.Function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloHandler {

        @Autowired
        private Function<String, String> uppercase;
        
        @FunctionName("hello")
        public String execute(
                @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
                ExecutionContext context) {

                        //save result in variable
                        String result = this.uppercase.apply(request.getBody().get());
                        context.getLogger().info("here is the output:  " + result);
                        return result;
        }

        @FunctionName("sbprocessor")
        public void serviceBusProcess(
                @ServiceBusQueueTrigger(name = "msg", queueName = "demo-queue", connection = "SBConnectionString") String message, 
                final ExecutionContext context) {
                
                        String adjustedMessage = this.uppercase.apply(message);
                        context.getLogger().info(adjustedMessage);
        }
}
