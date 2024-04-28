package com.github.wesleyegberto.kafkastreams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WordCounterLogger {
	private static final Serde<String> STRING_SERDE = Serdes.String();

	@Value("${spring.kafka.streams.words-count.topic.name}")
	private String wordsCountsStreamTopicName;

	/**
	 * Print each pair of word-count each time it is updated (could do an update to an external DB).
	 * And publish word and its last counter value to an topic.
	 */
	@Autowired
	public void wordCounterLoggerTopology(StreamsBuilderFactoryBean factoryBean, StreamsBuilder streamsBuilder) {
		// listen to words-counts updates (each time the materialized table flushes the changelog)
		KStream<String, String> wordsCountsStream = streamsBuilder.stream(wordsCountsStreamTopicName, Consumed.with(STRING_SERDE, STRING_SERDE));

		wordsCountsStream
				.mapValues((key, value) -> {
					// retrives the local store materialized by the WordCounterProcessor
					KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
					ReadOnlyKeyValueStore<String, Long> localStore = kafkaStreams.store(
						StoreQueryParameters.fromNameAndType("words-counts", QueryableStoreTypes.keyValueStore())
					);
					return localStore.get(key).toString();
				})
				.peek((key, value) -> System.out.printf("[DB] word=%s; counter=%s%n", key, value))
				.to("words-counts-updates", Produced.with(STRING_SERDE, STRING_SERDE));
	}
}
