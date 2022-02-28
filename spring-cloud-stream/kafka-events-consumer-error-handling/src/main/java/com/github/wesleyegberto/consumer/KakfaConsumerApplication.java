package com.github.wesleyegberto.consumer;

import java.util.function.Consumer;

import com.github.wesleyegberto.consumer.model.BuyOrder;
import com.github.wesleyegberto.consumer.model.SellOrder;
import com.github.wesleyegberto.consumer.service.EventConsumer;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KakfaConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(KakfaConsumerApplication.class, args);
	}

	@Bean
	public Consumer<BuyOrder> buyOrderErrorThrower(EventConsumer eventConsumer) {
		return eventConsumer::processBuy;
	}

	@Bean
	public Consumer<KStream<Long, SellOrder>> sellOrderErrorThrower(EventConsumer eventConsumer) {
		return sellOrders -> {
			sellOrders.foreach((k, v) -> eventConsumer.processSell(v));
		};
	}

	@Bean
	public Consumer<BuyOrder> buyOrderDlqHandler(EventConsumer eventConsumer) {
		return eventConsumer::handleError;
	}
}

