grpcurl -d '{"firstName":"tu hu", "lastName":"con"}' -plaintext  localhost:6565 com.example.grpc.server.service.HelloService/hello
grpc_cli ls localhost:6565
grpc_cli ls localhost:6565 grpc.health.v1.Health
