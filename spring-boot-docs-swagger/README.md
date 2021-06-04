# Spring Boot + Swagger

## Swagger

### Dependency

#### Springfox 2

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

#### Springfox 3

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-boot-starter</artifactId>
	<version>3.0.0</version>
</dependency>
```

### Configuration

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

### Swagger and UI

#### Springfox 2

* UI: `http://localhost:8080/swagger-ui.html`
* JSON: `http://localhost:8080/v2/api-docs`

#### Springfox 3

* UI: `http://localhost:8080/swagger-ui/index.html`
* JSON: `http://localhost:8080/v3/api-docs`

## ASCIIDOC

### Dependency

Dependency:

```xml
<properties>
	<asciidoctor-maven-plugin.version>1.5.6</asciidoctor-maven-plugin.version>
	<spring-restdocs.version>2.0.5.RELEASE</spring-restdocs.version>
</properties>

<dependencies>
	<dependency>
		<groupId>org.springframework.restdocs</groupId>
		<artifactId>spring-restdocs-mockmvc</artifactId>
		<version>${spring-restdocs.version}</version>
		<scope>test</scope>
	</dependency>
</dependencies>
```

We need to setup the plugin:

```xml
<build>
	<pluginManagement>
		<plugins>
			<plugin>
				<groupId>org.asciidoctor</groupId>
				<artifactId>asciidoctor-maven-plugin</artifactId>
				<version>${asciidoctor-maven-plugin.version}</version>
				<dependencies>
					<dependency>
						<groupId>org.asciidoctor</groupId>
						<artifactId>asciidoctorj-pdf</artifactId>
						<version>1.5.0-alpha.16</version>
					</dependency>
					<dependency>
						<groupId>org.asciidoctor</groupId>
						<artifactId>asciidoctorj</artifactId>
						<version>1.5.7</version>
					</dependency>
					<dependency>
						<groupId>org.springframework.restdocs</groupId>
						<artifactId>spring-restdocs-asciidoctor</artifactId>
						<version>${spring-restdocs.version}</version>
					</dependency>
					<dependency>
						<groupId>org.jruby</groupId>
						<artifactId>jruby-complete</artifactId>
						<version>9.1.17.0</version>
					</dependency>
				</dependencies>
				<executions>
					<execution>
						<id>generate-docs</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>process-asciidoc</goal>
						</goals>
						<configuration>
							<backend>html</backend>
							<sourceDirectory>src/docs/asciidocs</sourceDirectory>
							<outputDirectory>target/generated-docs</outputDirectory>
						</configuration>
					</execution>
					<execution>
						<id>generate-docs-pdf</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>process-asciidoc</goal>
						</goals>
						<configuration>
							<backend>pdf</backend>
							<sourceDirectory>src/docs/asciidocs</sourceDirectory>
							<outputDirectory>target/generated-docs</outputDirectory>
						</configuration>
					</execution>
				</executions>
				<configuration>
					<backend>html</backend>
					<doctype>book</doctype>
					<attributes>
						<project-version>${project.version}</project-version>
					</attributes>
				</configuration>
			</plugin>
		</plugins>
	</pluginManagement>
</build>
```

And a separated profile to run it, in CI for example:

```xml
<profiles>
	<profile>
		<id>ci</id>
		<build>
			<plugins>
				<plugin>
					<groupId>org.asciidoctor</groupId>
					<artifactId>asciidoctor-maven-plugin</artifactId>
					<version>${asciidoctor-maven-plugin.version}</version>
					<executions>
						<execution>
							<id>generate-docs</id>
							<phase>prepare-package</phase>
							<goals>
								<goal>process-asciidoc</goal>
							</goals>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</build>
	</profile>
</profiles>
```

### Configuration

Junit 4:

```java
@WebMvcTest(PetsController.class)
public class PetsControllerDocumentationTest {
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;
	private RestDocumentationResultHandler resultHandler;

	@BeforeEach
	public void setUp() {
		this.resultHandler = MockMvcRestDocumentation.document("{method-name}",
			Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
			Preprocessors.preprocessResponse(
				Preprocessors.prettyPrint(),
				Preprocessors.removeMatchingHeaders("X.*", "Pragma", "Expires")
			)
		);
		this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.apply(documentationConfiguration(this.restDocumentation))
				.build();
	}
}
```

JUnit 5:

```java
@WebMvcTest(PetsController.class)
@ExtendWith(RestDocumentationExtension.class)
public class PetsControllerDocumentationTest {
	private MockMvc mvc;
	private RestDocumentationResultHandler resultHandler;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.resultHandler = MockMvcRestDocumentation.document("{method-name}",
			Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
			Preprocessors.preprocessResponse(
				Preprocessors.prettyPrint(),
				Preprocessors.removeMatchingHeaders("X.*", "Pragma", "Expires")
			)
		);
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
				.alwaysDo(this.resultHandler)
				.build();
	}
}
```

**Important:** In the tests we use `RestDocumentationRequestBuilders` instead of `MockMvcRequestBuilders`.

### Run

To generate the docs with ASCIIDOC: `mvn package -Pci`

It uses the template at `src/docs/asciidocs/*.adoc` and inserts the JSONs from tests.

The plugin will generate the docs in HTML and PDF at `target/generated-docs/` folder.

## Links

* [Springfox Docs](https://springfox.github.io/springfox/docs/current/)
