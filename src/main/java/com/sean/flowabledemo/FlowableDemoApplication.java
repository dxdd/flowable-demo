package com.sean.flowabledemo;

import com.sean.flowabledemo.service.MyService;
import com.sean.flowabledemo.service.TaskSkipService;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowableDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final MyService myService) {
        return strings -> myService.createDemoUsers();
    }

    @Bean
    public TaskSkipService taskSkipService(){
        return new TaskSkipService();
    }
}
