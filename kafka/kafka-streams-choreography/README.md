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
	"taxId": "10042",
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
PUT http://localhost:8080/users/42

{
	"name": "Odair",
	"birthDate": "1980-01-01",
	"email": "odair-9362@email.com",
	"address": {
		"zipcode": "0520244",
		"street": "Stree A, 42",
		"city": "Sao Paulo"
	}
}
```

Find user by tax ID:

```
PUT http://localhost:8080/users/search?taxId=10042
```

### Orders API

Place order:

```
POST http://localhost:9090/orders

{
	"clientTaxId": "10042",
	"items": [
		{
			"skuCode": "XPTO-1",
			"quantity": 1,
			"unitCost": 90099
		}
	],
	"shippingAddress": {
		"zipcode": "0520244",
		"street": "Stree A, 42",
		"city": "Sao Paulo"
	}
}
```

## TODO

Orders API:

- [ ] Implement user validation between Orders API and Users API via REST
- [ ] Implement order processing using Streams API
- [ ] Implement Stream processor to listen to user changes and register last updates to use in validation
- [ ] Change user validation to use events registration updates

