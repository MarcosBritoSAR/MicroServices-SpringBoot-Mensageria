#!/bin/bash

echo "INICIANDO ETAPA 1"
source ./autentication/setup.sh
source ./eureka/setup.sh
source ./gateway/setup.sh
source ./notification/setup.sh
echo "ETAPA 1 COMPLETA"

echo "INICIANDO ETAPA 2"
docker-compose up auth -d
docker-compose up gateway -d
docker-compose up postgres -d
#docker-compose up rabbitmq -d
docker-compose up eureka -d
docker-compose up notification -d
echo "ETAPA 2 COMPLETA"
