#!/bin/bash

echo "INICIANDO ETAPA 1"
source ./autentication/setup.sh
source ./eureka/setup.sh
source ./gateway/setup.sh
source ./notification/setup.sh
echo "ETAPA 1 COMPLETA"

echo "INICIANDO ETAPA 2"
docker-compose up
echo "ETAPA 2 COMPLETA"
