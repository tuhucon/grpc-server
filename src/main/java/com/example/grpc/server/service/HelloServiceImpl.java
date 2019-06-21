package com.example.grpc.server.service;

import com.example.grpc.server.message.HelloRequest;
import com.example.grpc.server.message.HelloResponse;
import com.example.grpc.server.message.Person;
import com.example.grpc.server.service.HelloServiceGrpc.HelloServiceImplBase;
import com.google.protobuf.Empty;
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

    @Override
    public void hellox(Empty request, StreamObserver<Person> responseObserver) {
        Person p = Person.newBuilder()
                .setFirstName("tu")
                .setLastName("hu")
                .setAge(32)
                .setAddress("ha noi viet nam")
                .setCity("ha noi")
                .setCountry("viet name")
                .setWifeFirstName("chich")
                .setWifeLastName("choe")
                .setWifeAge(28)
                .setWifeAddress("ha noi viet nam")
                .setWifeCity("ha noi")
                .setWifeCountry("viet name")
                .build();
        responseObserver.onNext(p);
        responseObserver.onCompleted();
    }

    @Override
    public void helloy(Person request, StreamObserver<Person> responseObserver) {
        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }
}
