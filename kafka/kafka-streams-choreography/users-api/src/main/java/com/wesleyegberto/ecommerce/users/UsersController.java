package com.wesleyegberto.ecommerce.users;

import java.util.Map;

import com.wesleyegberto.ecommerce.events.UserEvent;
import com.wesleyegberto.ecommerce.events.UserEventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("{id}")
	public ResponseEntity<UserData> findById(@PathVariable("id") long id) {
		return users.findById(id)
			.map(UserData::of)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("search")
	public ResponseEntity<UserData> findByTaxId(@Param("taxId") String taxId) {
		if (taxId == null || taxId.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return users.findByTaxId(taxId)
			.map(UserData::of)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
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

	@GetMapping("{id}/historic")
	public ResponseEntity<UserHistoricInformation> getUserHistory(@PathVariable("id") Long id) {
		var optional = this.users.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var user = optional.get();
		return ResponseEntity.ok(UserHistoricInformation.of(user));
	}
}
