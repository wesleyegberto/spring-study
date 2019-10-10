# Pre-propulate the queue with 1Mi of messages to test the OrderProcessor throughput
$RABBITMQ_PERFTEST/bin/runjava com.rabbitmq.perf.PerfTest --uri amqp://localhost:5672 \
	-y 0 -p \
	-s 1000 -C 1000000 \
	-u "orders.received" \
	-f persistent \
	--body order_received.json \
	--id "produce-orders-received"