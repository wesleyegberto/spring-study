package com.github.wesleyegberto.springstream.samples;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.kafka.utils.DlqPartitionFunction;
import org.springframework.context.annotation.Bean;

import com.github.wesleyegberto.springstream.samples.models.RegionWithClicks;
import com.github.wesleyegberto.springstream.samples.models.WordCount;

@SpringBootApplication
public class SamplesApplication {
	private static final Logger log = LoggerFactory.getLogger(SamplesApplication.class);
	
	private static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		SpringApplication.run(SamplesApplication.class, args);
	}
	
	/*
	 * By default it assumes that the DQL has the same number
	 * of partitions as the input topic.
	 * Here we can customize that.
	 */
	@Bean
	public DlqPartitionFunction partitionFunction() {
	    return (group, record, ex) -> 0;
	}
	
	private void logEntry(String key, String value) {
		log.info("Key: {}, Value: {}", key, value);
	}

	/*
	 * One -> none
	 * Only consumes a topic.
	 */
	// @Bean
	public Consumer<KStream<String, String>> process() {
		return input -> input.foreach(this::logEntry);
	}
	
	/*
	 * One -> One
	 * Receives KStream as input and produces a KStream as output.
	 * Basically it is consuming from one topic and producing to another one.
	 */
	// @Bean
	public Function<KStream<Object, String>, KStream<String, WordCount>> wordCountSingleOutput() {
	  return input -> input
	        .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
	        .map((key, value) -> new KeyValue<>(value, value))
	        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
	        .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
	        .count(Materialized.as("wordcount-store"))
	        .toStream()
	        .map((key, value) -> new KeyValue<>(key.key(), createMessage(key, value)));
	}
	
	/*
	 * One -> One of Many
	 * Consumes from one topic and produces to another depending of the predicate.
	 */
	// @Bean
	@SuppressWarnings("unchecked")
	public Function<KStream<Object, String>, KStream<?, WordCount>[]> wordCountConditionedOutput() {
		Predicate<Object, WordCount> isEnglish = (k, v) -> v.getWord().contains("english");
		Predicate<Object, WordCount> isFrench = (k, v) -> v.getWord().contains("french");
		Predicate<Object, WordCount> isSpanish = (k, v) -> v.getWord().contains("spanish");
		Predicate<Object, WordCount> isInvalid = (k, v) -> !v.getWord().contains("english")
					&& !v.getWord().contains("french") && !v.getWord().contains("spanish");

		return input -> input
				.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
				// .groupBy((key, value) -> value) error class cast exception
				.map((key, value) -> new KeyValue<>(value, value))
				.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
				.windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
				.count(Materialized.as("wordcount-branch"))
				.toStream()
				.map((key, value) -> new KeyValue<>(null, createMessage(key, value)))
				.branch(isEnglish, isFrench, isSpanish, isInvalid);
	}
	
	/*
	 * Two -> One
	 * Consumes from two topics and produces to another one.
	 */
	// @Bean
	public BiFunction<KStream<String, String>, KTable<String, String>, KStream<String, String>> consumingFromTwoTopics() {
		return (userClicksStream, userRegionsTable) -> userClicksStream
			  .leftJoin(userRegionsTable,
					  (clicks, region) -> new RegionWithClicks(region, clicks),
					  Joined.with(Serdes.String(), Serdes.String(), null)
			  )
			  .map((user, regionWithClicks) -> new KeyValue<>(regionWithClicks.getRegion(), regionWithClicks.getClicks()))
			  .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
			  .reduce(Long::sum)
			  .toStream()
			  .map((region, clicks) -> new KeyValue<>(region, clicks.toString()));
	}
	
	/*
	 * One -> none
	 * Consumes from one topic and don't produce anything.
	 */
	@Bean
	public BiConsumer<KStream<String, String>, KTable<String, String>> consumingFromTwoTopicsAndNotProducing() {
		return (userClicksStream, userRegionsTable) -> {
			userClicksStream.foreach((key, value) -> {
				this.logEntry(key, value);
				latch.countDown();
			});
			userRegionsTable.toStream().foreach((key, value) -> {
				this.logEntry(key, value);
				latch.countDown();
			});
		};
	}

	private WordCount createMessage(Windowed<String> key, Long value) {
		return new WordCount(key.key(), value, new Date(key.window().start()), new Date(key.window().end()));
	}
}
