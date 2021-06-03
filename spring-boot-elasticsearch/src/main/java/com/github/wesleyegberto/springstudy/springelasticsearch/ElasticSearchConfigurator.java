package com.github.wesleyegberto.springstudy.springelasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ElasticSearchConfigurator {
	@Value("${elasticsearch.clusterName}")
	private String clusterName;
	@Value("${elasticsearch.host}")
	private String host;
	@Value("${elasticsearch.port}")
	private int port;
	
	// @Bean
	@SuppressWarnings("resource")
	public Client client() throws UnknownHostException {
		Settings settings = Settings.builder()
			.put("cluster.name", clusterName)
			.build();
		return new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
	}
	
	//@Bean
	public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}
}
