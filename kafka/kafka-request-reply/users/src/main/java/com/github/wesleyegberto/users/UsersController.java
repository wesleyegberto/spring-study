package com.github.wesleyegberto.users;

import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import com.github.wesleyegberto.users.verification.UserVerificationRequest;
import com.github.wesleyegberto.users.verification.UserVerificationResponse;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	private final String requestTopic;
	private final ReplyingKafkaTemplate<String, UserVerificationRequest, UserVerificationResponse> replyingKafkaTemplate;

	UsersController(
			@Value("${kafka.request-topic}") String requestTopic,
			ReplyingKafkaTemplate<String, UserVerificationRequest, UserVerificationResponse> replyingKafkaTemplate) {
		this.requestTopic = requestTopic;
		this.replyingKafkaTemplate = replyingKafkaTemplate;
	}

	@PostMapping("/verifications")
	public ResponseEntity<Map<String, Object>> validateUser(@RequestBody User user) {
		LOG.info("User {} - {} - requesting verification", user.getId(), user.getTaxId());
		try {
			var message = new ProducerRecord<String, UserVerificationRequest>(requestTopic,
				UserVerificationRequest.of(user));
			var replyMessage = replyingKafkaTemplate.sendAndReceive(message);

			LOG.info("User {} - waiting reply", user.getId());
			var verificationResponse = replyMessage.get().value();

			LOG.info("User {} - Verification {} with response {} - {}",
					user.getId(), verificationResponse.verificationId(),
					verificationResponse.approved(), verificationResponse.rejectionMessage());

			return ResponseEntity.ok(Map.of(
					"approved", verificationResponse.approved(),
					"rejectionMessage", verificationResponse.rejectionMessage()));

		} catch (CancellationException | ExecutionException | InterruptedException ex) {
			return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
		}
	}
}
