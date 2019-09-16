package com.github.wesleyegberto.springstudy.queueproducer.messaging.creation;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class OrderNumberGenerator {
	private SecureRandom RANDOM;

	public OrderNumberGenerator() {
		try {
			RANDOM = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
		}
		if (RANDOM == null) {
			try {
				RANDOM = SecureRandom.getInstanceStrong();
			} catch (NoSuchAlgorithmException e1) {
			}
		}
	}

	public int generateOrderNumber() {
		return Math.abs(RANDOM.nextInt());
	}
}
