package com.github.wesleyegberto.reactiveweb;

import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoDbConfigurer extends AbstractMongoConfiguration {
	@Value("${spring.data.mongodb.uri}")
	private String uri;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	private CodecRegistry createCodecRegistries() {
		// save UUID using standard 4 bytes representation
		return CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)), MongoClient.getDefaultCodecRegistry());
	}

	@Override
	public MongoClient mongoClient() {
	    MongoClientOptions.Builder mcoBuilder = MongoClientOptions
    		.builder()
			.applicationName("spring-mongodb")
			.connectTimeout(3000)
			.maxWaitTime(10000)
			.maxConnectionLifeTime(360_000)
			.maxConnectionIdleTime(30_000)
    		.codecRegistry(createCodecRegistries());
		return new MongoClient(new MongoClientURI(uri, mcoBuilder));
	}
	
	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
		// remove _class
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
		return mongoTemplate;
	}
}
