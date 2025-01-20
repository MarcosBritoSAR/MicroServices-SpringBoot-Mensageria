#!/bin/bash

mvn clean package -DskipTests
docker build --tag authentication .
docker run -d --name auth -p 8081:8081 --network com.brito authentication
