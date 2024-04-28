#!/usr/bin/env bash

cat shakespeare-the-sonnets.txt | while read -r line;
do
    if [ -z "$line" ]; then
        continue
    fi

    echo "{\"text\":\"$line\"}" | curl --json @- "http://localhost:8080/words"
done

