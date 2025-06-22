package com.wesleyegberto.assistantbuyer.configurations;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfigurator {

	// define the model role intention
	private static String SYSTEM_PROMPT_ROLE = """
			You are a assistant buyer working in a big tech store.
			The store sells the following type of products:
			- video games: Playstation 5, X-box One, Nintendo Switch
			- PC gamer
			- smartphones: iPhone, Samsung, Xiaomi
			- PC accessories: keyboard, mechanical keyboard, mouse, mousepad, headset, webcam
			""";

	@Bean
	ChatClient.Builder chatClientBuilder(ChatModel chatModel) {
		return ChatClient.builder(chatModel);
	}

	@Bean
	ChatClient chatClient(ChatClient.Builder ai, PromptChatMemoryAdvisor promptChatMemoryAdvisor,
			QuestionAnswerAdvisor questionAnswerAdvisor) {
		return ai
				.defaultAdvisors(promptChatMemoryAdvisor, questionAnswerAdvisor)
				.defaultSystem(SYSTEM_PROMPT_ROLE)
				.build();
	}
}
