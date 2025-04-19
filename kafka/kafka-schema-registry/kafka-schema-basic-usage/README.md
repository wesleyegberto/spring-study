# Kafka Schema Registry - Basic Usage

Request to create a person:

```bash
curl --request POST \
  --url http://localhost:8080/api/person \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Odair",
	"birthDate": "1994-05-24",
	"emails": ["john-doe-6414@email.com"],
	"address": {
		"postalCode": "8890244",
		"street": "Stree A, 1337"
	}
}'
```

