# Spring Boot - Kafka Streaming

## Run

```sh
docker compose up
```

## Applications

- Kafdrop: http://localhost:19000/

### Users API

Create user:

```
POST http://localhost:8080/users

{
	"taxId": "910136",
	"name": "Odair",
	"birthDate": "1980-01-01",
	"email": "odair-9362@email.com",
	"address": {
		"zipcode": "0520244",
		"street": "Stree A",
		"city": "Sao Paulo"
	}
}
```

Update user:

```
PUT http://localhost:8080/users/102030

{
	"name": "Odair",
	"birthDate": "1980-01-01",
	"email": "odair-9362@email.com",
	"address": {
		"zipcode": "0520244",
		"street": "Stree A",
		"city": "Sao Paulo"
	}
}
```
