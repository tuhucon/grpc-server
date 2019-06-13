#!/bin/bash
protoc -I=src/main/proto/ --java_out=src/main/java/ src/main/proto/message/hello/*.*
protoc -I=src/main/proto/ --java_out=src/main/java/ src/main/proto/service/*.*
