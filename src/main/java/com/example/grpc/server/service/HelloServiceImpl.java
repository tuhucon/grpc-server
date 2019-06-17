package com.example.grpc.server.service;

import com.example.grpc.server.message.HelloRequest;
import com.example.grpc.server.message.HelloResponse;
import com.example.grpc.server.service.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloServiceImpl extends HelloServiceImplBase {
    @Override
    public void hello(HelloRequest helloRequest, StreamObserver<com.example.grpc.server.message.HelloResponse> responseObserver) {
        try {
            String greeting = new StringBuilder()
                    .append("hello, ")
                    .append(helloRequest.getFirstName())
                    .append(" ")
                    .append(helloRequest.getLastName())
                    .toString();
            Thread.sleep(61_000L);
            HelloResponse response = HelloResponse.newBuilder()
                    .setGreeting(greeting)
                    .setAge(32)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            System.out.println("hello request is completed");
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL.augmentDescription("Server error").asRuntimeException());
        }
    }
}
