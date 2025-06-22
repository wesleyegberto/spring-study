#!/usr/bin/env bash
set -e errexit
# docker compose up
docker run -it --rm --name postgresml \
    --platform linux/arm64 \
    -p 5433:5432 \
    -p 8000:8000 \
    -v postgresml_data:/var/lib/postgresql \
    ghcr.io/postgresml/postgresml:2.9.3 \
    sudo -u postgresml psql -d postgresml