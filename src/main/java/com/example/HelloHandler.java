package com.example;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

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
            final ExecutionContext context,
            @BindingName("DeliveryCount") long deliveryCount,
            @BindingName("MessageId") String messageId) {

                Logger logger = context.getLogger();
                logger.info("Delivery count: " + deliveryCount);
                logger.info("Message body: " + this.uppercase.apply(message));
                logger.info("Message id: " + messageId);

                if (message.equals("throw exception") && deliveryCount <= 3)
                {
                        throw new RuntimeException("Error processing message");
                }
        }
}
