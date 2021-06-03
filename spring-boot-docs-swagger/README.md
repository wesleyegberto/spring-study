# Spring Boot + Swagger

## Dependency

### Springfox 2

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>3.0.0</version>
</dependency>
<!-- to add Swagger UI -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>3.0.0</version>
</dependency>
```

### Springfox 3

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-boot-starter</artifactId>
	<version>3.0.0</version>
</dependency>
```

## Configuration

Using Springfox 2 we must declare a configurator, on Springfox 3 it isn't required but we still can use it to customize.

```java
@Configuration
@EnableSwagger2
public class SpringFoxConfigurator {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
```

## Swagger and UI

### Springfox 2

* UI: `http://localhost:8080/swagger-ui.html`
* JSON: `http://localhost:8080/v2/api-docs`

### Springfox 3

* UI: `http://localhost:8080/swagger-ui/index.html`
* JSON: `http://localhost:8080/v3/api-docs`

## Links

* [Springfox Docs](https://springfox.github.io/springfox/docs/current/)
