package com.example;

import com.microsoft.azure.functions.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTest {

    @Test
    public void test() {
        Mono<String> result = Mono.just("foo");
        assertThat(result.block()).isEqualTo("foo");
    }
    
}
