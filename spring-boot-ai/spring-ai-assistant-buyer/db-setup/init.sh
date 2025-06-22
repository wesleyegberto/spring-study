#!/usr/bin/env bash
cat users.sql |  psql -U postgresml  -h localhost -p 5433 postgresml