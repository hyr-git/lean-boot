package com.boot.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.boot.elasticsearch.dao")
public class BootESApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootESApplication.class, args);
    }

}
