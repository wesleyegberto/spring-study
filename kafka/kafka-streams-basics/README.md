# Spring Boot - Kafka Streams - Basic

## Setup

Create the following topics in Kafka:

- `words`
- `words-counts`
- `words-counts-updates`

## Generate Words

Post a text to the API:

```bash
curl -X POST http://localhost:8081/words --json "{\"text\":\"Hello\"}"
```

Run the script to extract each line from The Sonnets (Shakespeare) and publish each line to topic `words`.

```bash
sh send-words.sh
```

