#!/usr/bin/env bash
set -e errexit
export KAFKA_URL=127.0.0.1:9092
mvn spring-boot:run
