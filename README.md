---
page_type: sample
languages:
- java
products:
- azure
description: "This is a sample application to showcase the use of Spring Cloud Function on top of Azure Functions."
urlFragment: hello-spring-function-azure
---

# Example "Hello, world" Spring Boot application that runs on Azure Functions

This is a sample application to showcase the use of Spring Cloud Function on top of Azure Functions.

## Build tool

This branch uses __Gradle__ to build the application:
 
- It uses the Azure Functions Plugin for Gradle, documented at [https://github.com/microsoft/azure-gradle-plugins/tree/master/azure-functions-gradle-plugin](https://github.com/microsoft/azure-gradle-plugins/tree/master/azure-functions-gradle-plugin)
- If you want to use __Maven__ instead, please [Switch to the `master` branch](../../tree/master/)

## Features

This is just a "Hello, world", with dependency injection of an uppercase function.  I'm using this to show how to do DI in a Java function.

## Getting Started

### Prerequisites

This project uses the Gradle Wrapper, so you will need Java installed.  This also has a ServiceBusQueueTrigger so you will need to setup a ServiceBus with a queue named 'demo-queue' to connect to.  You can follow this to [setup a Azure Service Bus namespace](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-dotnet-get-started-with-queues?tabs=connection-string#create-a-namespace-in-the-azure-portal) and [setup a queue](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-dotnet-get-started-with-queues?tabs=connection-string#create-a-queue-in-the-azure-portal).  Finally you will need to [get the connection string](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-dotnet-get-started-with-queues?tabs=connection-string#get-the-connection-string) and save it in your `local.settings.json` file.

#### local.settings.json

This file will need to be created and look like this.  You will need to use your Service Bus connection string

``` json
{
    "IsEncrypted": false,
    "Values": {
      "AzureWebJobsStorage": "",
      "FUNCTIONS_WORKER_RUNTIME": "java",
      "MAIN_CLASS":"com.example.DemoApplication",
      "SBConnectionString":"<connection_string>"
    }
  }
  
```

### Installation

- Clone the project: `git clone https://github.com/Azure-Samples/hello-spring-function-azure.git`
- Configure the project to use your own resource group and your own application name (it should be unique across Azure)
  - Open the `build.gradle` file
  - Customize the `resourceGroup` and `appName` properties
- Build the project: `gradle azureFunctionsPackage`

### Quickstart

Once the application is built, you can run it locally using the Azure Function Gradle plug-in:

`gradle azureFunctionsRun`

And you can test it using a cURL command:

`curl http://localhost:7071/api/hello -d "{\"name\":\"Azure\"}"`

There is also a ServiceBusQueueTrigger that you will need to setup.  Please check the Prerequisites section for how to do that.  Then you can send messages into the queue and see them in the logs as the trigger processes them.

## Deploying to Azure Functions

Deploy the application on Azure Functions with the Azure Function Gradle plug-in:

`gradle azureFunctionsDeploy`

You can then test the running application:

`curl https://<YOUR_SPRING_FUNCTION_NAME>.azurewebsites.net/api/hello -d "{\"name\":\"Azure\"}"`

Replace the `<YOUR_SPRING_FUNCTION_NAME>` part by the name of your Spring Function.

## Troubleshooting

If you are running this locally on a Mac you might find after the first run it's still using your configured port (7071 in our case).  You can find that process and kill it manually which will allow you to start it again.

Find process running on port 7071:  `lsof -i tcp:7071`
Manually kill that process:  `kill -9 <PID>`