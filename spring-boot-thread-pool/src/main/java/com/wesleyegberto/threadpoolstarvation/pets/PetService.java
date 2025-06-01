package com.wesleyegberto.threadpoolstarvation.pets;

import com.wesleyegberto.threadpoolstarvation.mock.HttpBinRestClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PetService {
	private static final Map<Long, Pet> DB = new HashMap<>();
	private static final AtomicLong ID = new AtomicLong(0);

	private final HttpBinRestClient restClient;
	private final int firstDelay;
	private final int secondDelay;

	PetService(HttpBinRestClient restClient, @Value("${first-delay}") int firstDelay,
			@Value("${second-delay}") int secondDelay) {
		this.restClient = restClient;
		this.firstDelay = firstDelay;
		this.secondDelay = secondDelay;
	}

	public Collection<Pet> getAll() {
		return DB.values();
	}

	public long save(Pet pet) {
		var id = ID.incrementAndGet();
		pet.setId(id);
		// lock http-nio worker thread
		restClient.delay(firstDelay);
		try {
			// lock fork-join-pool worker thread
			return CompletableFuture.supplyAsync(() -> {
				restClient.delay(secondDelay);
				DB.put(id, pet);
				return pet.getId();
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			return -1;
		}
	}
}
