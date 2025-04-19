# Kafka Request-Reply Synchronous

Request to verify an user:

```bash
curl --request POST \
  --url http://localhost:8080/users/verifications \
  --header 'Content-Type: application/json' \
  --data '{
	"id": "102030",
	"taxId": "1020301",
}'
```

Any tax ID ending with odd number will be rejected.
