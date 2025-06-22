package com.wesleyegberto.assistantbuyer.configurations;

import com.wesleyegberto.assistantbuyer.domain.ProductRepository;

import java.util.List;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class RagConfigurator {
	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore, JdbcClient db,
			ProductRepository productRepository) {
		var count = db.sql("select count(*) from vector_store").query(Integer.class).single();
		// initializa the vector store
		if (count == 0) {
			// if you have a really powerful machine =)
			// productRepository.findAll()
			productRepository.findByCategory("video games")
					.forEach(product -> {
						var dogument = new Document("id: %s, name: %s, category: %s, price: %s".formatted(
								product.getId(), product.getName(), product.getCategory(), product.getPrice()));
						vectorStore.add(List.of(dogument));
					});
		}

		return QuestionAnswerAdvisor.builder(vectorStore)
				.promptTemplate(createPromptTemplate())
				.build();
	}

	PromptTemplate createPromptTemplate() {
		return PromptTemplate.builder()
				.renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
				.template("""
						<query>

						Context information is below.

						---------------------
						<question_answer_context>
						---------------------

						Given the context information and no prior knowledge, answer the query.

						Follow these rules:

						1. The store sells the following categories of products:
						- video games: Playstation 5, X-box One, Nintendo Switch
						- PC gamer
						- smartphones: iPhone, Samsung, Xiaomi
						- PC accessories: keyboard, mechanical keyboard, mouse, mousepad, headset, webcam
						2. You should ask for the customer name and what are the kind of platforms, videos games and type of games that he or she likes.
						3. If the customer is looking for a product outside of the categories of products selled by the store, you should say that the store do not sell that kind of product.
						4. You should ask about the customer relationship or expectation about the product, try to understand what are the intentions with the product and how important it is for the customer, try to be succinct when asking to not wast much time.
						5. You should try to engage the customer with information and felling about the product before trying to selling it.
						6. You should try to engage the customer relationship and expectation only once, if there is no related response then just try to offer the products that the customer is interested.
						7. Once the customer decided which product wants to buy, you should just redirect the customer to the product page to start the purchase, the link has the following format: http://springai-tech-store.com.br/products/PRODUCT_ID/details, you should replace the PRODUCT_ID part with the ID of the selected product.
						""")
				.build();
	}
}
