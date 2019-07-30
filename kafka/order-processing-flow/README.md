# Spring + Kafka



## Infrastructure

Download Zookeeper and Kafka.

* Run Zookeeper: `$ZOOKEEPER_HOME/bin/zookeeper-server-start.sh config/zookeeper.properties`
* Run Kafka: `$KAFKA_HOME/bin/kafka-server-start.sh config/server.properties`

#### Create topics:

`$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <topic-name>`

Topics to create:

* order-processing
* order-invoice
* order-shipment

#### Alter the topic
`$KAFKA_HOME/bin/kafka-topics.sh --alter --zookeeper localhost:2181 --partitions 4 --topic order-processing`

#### List the topics

`$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper localhost:2181`

#### Consume topics
`$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic order-processing --from-beginning`



## Process

- Order creation (order-creator)
  - the order is validated
  - the order number is generated
  - send order to async processing
- Processing (order-processor)
  - validate the SKUs and its prices
  - send it to invoice
- Invoice (order-invoicer)
  - the client is billed
  - wait the payment confirmation
  - send order to shipping
- Shipping (order-shipment)
  - Notify the logistic operator to pick up the products
  - Set tracking number





## APIs requests

### order-creation

Request:

```Httpie
echo '{
	"clientDocument": "1029301922",
	"items": [
		{
			"sku": "1002030",
			"quantity": 1,
			"unitPrice": 100
		}
	],
	"total": 0
}' | http POST http://localhost:8080/orders
```

### Stress
Run: `ab -n 1000 -c 10 -T 'application/json' -p order-body.json http://127.0.0.1:8080/orders`

Contant order generation run: `while true; do sh ab_fewer_orders.sh; sleep 1; done;`
