#!/bin/bash

source ./autentication/setup.sh
source ./eureka/setup.sh
source ./gateway/setup.sh
source ./notification/setup.sh

docker-compose up