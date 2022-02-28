package com.github.wesleyegberto.consumer.controller;

import com.github.wesleyegberto.consumer.model.TransactionTotal;

import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
	private InteractiveQueryService queryService;

	public TransactionsController(InteractiveQueryService queryService) {
		this.queryService = queryService;
	}

	@GetMapping("/product/{product}")
	public TransactionTotal getSummaryByProductId(@PathVariable("product") String product) {
		ReadOnlyKeyValueStore<String, TransactionTotal> keyValueStore = queryService.getQueryableStore(
				"all-transactions-store",
				QueryableStoreTypes.keyValueStore()
			);
		return keyValueStore.get(product);
	}
}
