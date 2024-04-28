package com.wesleyegberto.ecommerce.events;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.wesleyegberto.ecommerce.users.User;

public record UserEvent(
		UUID eventId,
		LocalDateTime eventDate,
		UserActionType userAction,
		User user) {

	private static UserEvent of(UserActionType actionType, User user) {
		return new UserEvent(UUID.randomUUID(), LocalDateTime.now(ZoneOffset.UTC), actionType, user);
	}

	public static UserEvent ofCreated(User user) {
		return of(UserActionType.CREATED, user);
	}

	public static UserEvent ofUpdated(User user) {
		return of(UserActionType.UPDATED, user);
	}
}

enum UserActionType {
	CREATED,
	UPDATED
}
