package com.wesleyegberto.assistantbuyer.configurations;

import javax.sql.DataSource;

import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ChatMemoryConfigurator {
	@Bean
	PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
		// var inMemoryChat = new InMemoryChatMemoryRepository();
		var jdbcChat = JdbcChatMemoryRepository
				.builder()
				.dataSource(dataSource)
				.build();

		var chatMessageWindow = MessageWindowChatMemory
				.builder()
				.chatMemoryRepository(jdbcChat)
				.build();

		return PromptChatMemoryAdvisor
				.builder(chatMessageWindow)
				.build();
	}
}
