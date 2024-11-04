package com.sean.flowabledemo;

import com.sean.flowabledemo.service.MockUsersService;
import com.sean.flowabledemo.service.TaskSkipService;
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
    public CommandLineRunner init(final MockUsersService mockUsersService) {
        return strings -> mockUsersService.createDemoUsers();
    }

    @Bean
    public TaskSkipService taskSkipService() {
        return new TaskSkipService();
    }
}
