package com.wesleyegberto.ecommerce.users;

import java.util.Map;

import com.wesleyegberto.ecommerce.events.UserEvent;
import com.wesleyegberto.ecommerce.events.UserEventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UsersController {
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	private final UserRepository users;
	private final UserEventPublisher eventPublisher;

	public UsersController(UserRepository users, UserEventPublisher eventPublisher) {
		this.users = users;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationRequest userCreation) {
		LOG.info("Creating user {}", userCreation.taxId());

		if (users.existsByTaxId(userCreation.taxId())) {
			return ResponseEntity.badRequest()
					.body(Map.of("error", "Tax ID is already registered"));
		}

		var user = userCreation.toEntity();
		users.save(user);

		eventPublisher.publish(UserEvent.ofCreated(user));

		return ResponseEntity.ok().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest updateRequest) {
		LOG.info("Updating user ID {}", id);
		var optional = this.users.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var user = optional.get();
		user.update(updateRequest);
		this.users.save(user);

		this.eventPublisher.publish(UserEvent.ofUpdated(user));

		return ResponseEntity.ok().build();
	}
}
