package com.github.wesleyegberto.kafkastreams;

import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/words")
public class WordsProducerController {
	@Value("${spring.kafka.streams.words.topic.name}")
	private String wordsStreamTopicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private StreamsBuilderFactoryBean factoryBean; // provides KafkaStreams instance managed by application

	@PostMapping
	public ResponseEntity<?> publishWord(@RequestBody Map<String, String> payload) {
		var text = payload.getOrDefault("text", null);
		if (text == null || text.isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		kafkaTemplate.send(wordsStreamTopicName, text);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("{word}/count")
	public Long getWordCount(@PathVariable String word) {
		KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
		ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams.store(
			StoreQueryParameters.fromNameAndType("words-counts", QueryableStoreTypes.keyValueStore())
		);
		return counts.get(word);
	}
}
