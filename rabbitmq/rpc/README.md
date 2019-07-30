# RabbitMQ - Request-Reply Pattern


## Queues

* mq.request.queue
* mq.reply.queue


## Call
Request: `curl http://localhost:8081/messages/5`

Stress using ApachBench: `ab -n 10000 -c 100 http://localhost:8081/messages/5`

