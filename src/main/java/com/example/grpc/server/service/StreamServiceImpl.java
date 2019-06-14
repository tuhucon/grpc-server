package com.example.grpc.server.service;

import com.example.grpc.server.message.ServerStreamRequest;
import com.example.grpc.server.message.ServerStreamResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class StreamServiceImpl extends StreamServiceGrpc.StreamServiceImplBase {
    @Override
    public void serverStream(ServerStreamRequest request, StreamObserver<ServerStreamResponse> responseObserver) {
        int startIndex = request.getStart();
        try {
            for (int i = 0; i < 100; i++) {
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
}
