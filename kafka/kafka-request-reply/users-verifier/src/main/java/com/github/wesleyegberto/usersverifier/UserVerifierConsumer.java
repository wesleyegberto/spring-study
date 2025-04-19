package com.github.wesleyegberto.usersverifier;

import java.util.UUID;

import com.github.wesleyegberto.usersverifier.verification.UserVerificationRequest;
import com.github.wesleyegberto.usersverifier.verification.UserVerificationResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class UserVerifierConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(UserVerificationResponse.class);

	@SendTo // responsible to extract correlation ID and reply-topic
	@KafkaListener(topics = "${kafka.request-topic}")
	UserVerificationResponse listen(UserVerificationRequest userVerificationRequest) {
		UUID verificationId = UUID.randomUUID();
		LOG.info("{} - Verifing user {} - {}", verificationId, userVerificationRequest.userId(), userVerificationRequest.taxId());

		boolean approved = true;
		String rejectionMessage = null;

		if (userVerificationRequest.taxId() == null || userVerificationRequest.taxId().isBlank()) {
			approved = false;
			rejectionMessage = "Tax ID is invalid";
		} else if (userVerificationRequest.taxId().matches("\\d+[13579]")) {
			approved = false;
			rejectionMessage = "Tax ID was blocked";
		}
		LOG.info("{} - Result {} - {}", verificationId, approved, rejectionMessage);

		if (approved)
			return UserVerificationResponse.approved(verificationId, userVerificationRequest.userId());
		return UserVerificationResponse.rejected(verificationId, userVerificationRequest.userId(), rejectionMessage);
	}
}
