package com.github.wesleyegberto.kafkastreams;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WordCounterProcessor {
	private static final Serde<String> STRING_SERDE = Serdes.String();

	@Value("${spring.kafka.streams.words.topic.name}")
	private String wordsStreamTopicName;
	@Value("${spring.kafka.streams.words-count.topic.name}")
	private String wordsCountsStreamTopicName;

	/**
	 * Builds the processor topology using DSL API.
	 * Count each word in the stream and persist it in a local store
	 */
	@Autowired
	public void wordCounterTopology(StreamsBuilder streamsBuilder) {
		KStream<String, String> wordsStream = streamsBuilder.stream(wordsStreamTopicName, Consumed.with(STRING_SERDE, STRING_SERDE));

		KTable<String, Long> wordsCounts = wordsStream
				.peek((key, value) -> System.out.printf("[Processor] processing: %s%n", value))
				.mapValues((ValueMapper<String, String>) String::toLowerCase)
				.flatMapValues(value -> Arrays.asList(value.split("\\W+")))
				.groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
				.count(Materialized.as("words-counts")); // materialize the KTable in a local store

		// each time a key is updated it will publish in this topic
		wordsCounts.toStream().to(wordsCountsStreamTopicName);
	}

}
