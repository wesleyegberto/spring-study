package com.github.wesleyegberto.springdocsswagger;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Springfox2Configurator {
	// Disable in these environments
	@Bean
	@Profile("production,staging")
	public Docket emptyApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.enable(false)
			.select()
			.build();
	}

	@Bean
	@Profile("default,development")
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("public-api")
			.tags(new Tag("Pets API", "Sample API to test Swagger"))
			// when using `groupName` we must provide it on `/v3/api-docs?group=Pets API`
			.apiInfo(
				new ApiInfoBuilder()
				.title("Pets API")
				.description("Sample API")
				.version("1.0.0")
				.build()
			)
			.select()
			// We can limit to document only our classes
			.apis(RequestHandlerSelectors.basePackage(SpringDocsSwaggerApplication.class.getPackageName()))
			.paths(PathSelectors.any())
			.build()
			// API path prefix
			.pathMapping("/")
			// configure the API Key, if any
			.securitySchemes(List.of(apikey()))
			.securityContexts(List.of(securityContext()))
			;
	}

	private ApiKey apikey() {
		return new ApiKey("API Key", "api-key", "header");
	}

	public SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.any())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		var authorizationScope = new AuthorizationScope("global", "access-everything");
		return List.of(new SecurityReference("api-key", new AuthorizationScope[] { authorizationScope }));
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
			.deepLinking(true)
			.displayOperationId(false)
			.defaultModelsExpandDepth(1)
			.defaultModelExpandDepth(1)
			.defaultModelRendering(ModelRendering.EXAMPLE)
			.displayRequestDuration(true)
			.docExpansion(DocExpansion.NONE)
			.filter(true)
			.maxDisplayedTags(null)
			.operationsSorter(OperationsSorter.ALPHA)
			.showExtensions(false)
			.showCommonExtensions(false)
			.tagsSorter(TagsSorter.ALPHA)
			.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
			.validatorUrl(null)
			.build();
	}
}