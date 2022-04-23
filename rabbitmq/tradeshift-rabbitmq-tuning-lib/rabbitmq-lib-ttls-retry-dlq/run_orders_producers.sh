#!/usr/bin/env bash
set -e errexit

# can download here: https://rabbitmq.github.io/rabbitmq-perf-test/stable/htmlsingle/
RABBITMQ_PERFTEST=~/dev-tools/perf-tests/rabbitmq-perf-test-2.9.1

# Pre-propulate the queue with 1Mi of messages to test the OrderProcessor throughput
$RABBITMQ_PERFTEST/bin/runjava com.rabbitmq.perf.PerfTest --uri amqp://localhost:5672 \
	-y 0 -p \
	-s 1000 -C 1000000 \
	-u "orders.received" \
	-f persistent \
	--body order_received.json \
	--id "produce-orders-received"
