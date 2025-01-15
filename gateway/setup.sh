#!/bin/bash

cd "$(dirname "$0")"
echo "Executando setup.sh em $(pwd)"

mvn clean package -DskipTests
docker build --tag gateway .

cd ..