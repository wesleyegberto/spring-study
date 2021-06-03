# Spring Boot Startup Time Tracker

On Spring Boot 2.4 was created a new API to track the startup time.

In the main class:

```java
var app = new SpringApplication(StartupTimeApplication.class);
app.setApplicationStartup(new BufferingApplicationStartup(1000));
```

On `application.properties` add:

```
management.endpoints.web.exposure.include=startup
```

After running the application, open a terminal and run:

```sh
curl -X POST http://localhost:8080/actuator/startup | jq '.timeline.events | sort_by(.duration)'
```
