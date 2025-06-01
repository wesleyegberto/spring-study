#!/usr/bin/env bash
set -e errexit

export JAVA_OPTIONS="-Xms512m -Xmx1g -Djava.security.egd=file:/dev/./urandom" && mvn spring-boot:run