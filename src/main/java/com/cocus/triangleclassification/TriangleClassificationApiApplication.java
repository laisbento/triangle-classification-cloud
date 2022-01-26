package com.cocus.triangleclassification;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDynamoDBRepositories
public class TriangleClassificationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriangleClassificationApiApplication.class, args);
    }

}
