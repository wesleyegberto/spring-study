#!/usr/bin/env bash
set -e errexit

export MAVEN_OPTS="--add-exports=java.base/jdk.internal.module=ALL-UNNAMED --enable-preview --source 19"

mvn package

java "$MAVEN_OPTS" -jar target/rest-api-virtual-threads.jar
