package com.example.grpc.server.service;

import com.example.grpc.server.message.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class StreamServiceImpl extends StreamServiceGrpc.StreamServiceImplBase {
    @Override
    public void serverStream(ServerStreamRequest request, StreamObserver<ServerStreamResponse> responseObserver) {
        int startIndex = request.getStart();
        try {
            for (int i = 0; i < 10; i++) {
                ServerStreamResponse response = ServerStreamResponse.newBuilder()
                        .setNext(i + startIndex)
                        .build();
                responseObserver.onNext(response);
                Thread.sleep(1_000L);
            }
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL.augmentDescription(ex.getMessage()).asRuntimeException());
        }
    }

    @Override
    public StreamObserver<ClientStreamRequest> clientStream(StreamObserver<ClientStreamResponse> responseObserver) {
        return new StreamObserver<ClientStreamRequest>() {
            int count = 0;
            @Override
            public void onNext(ClientStreamRequest value) {
                System.out.println(value.getNext());
                count++;
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL
                                                        .withDescription("description here")
                                                        // status description = with description + "/n" + augmentDescription
                                                        .augmentDescription("detail: " + t.getMessage())
                                                        .asRuntimeException());
            }

            @Override
            public void onCompleted() {
                ClientStreamResponse response = ClientStreamResponse.newBuilder().setCount(count).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BidirectionStreamRequest> bidirectionStream(StreamObserver<BidirectionStreamResponse> responseObserver) {
        return new StreamObserver<BidirectionStreamRequest>() {

            @Override
            public void onNext(BidirectionStreamRequest value) {
                System.out.println(value.getMsg());
                BidirectionStreamResponse response = BidirectionStreamResponse.newBuilder()
                        .setMsg("server pong")
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("client completed");
                responseObserver.onCompleted();
            }
        };
    }
}
