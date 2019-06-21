package com.example.grpc.server;

import com.example.grpc.server.message.HelloRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Minh")
                .setLastName("Tuan")
                .build();
        System.out.println(request);
    }
}
