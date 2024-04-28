package com.wesleyegberto.usersauditor.auditing;

import java.time.LocalDateTime;
import java.util.UUID;

import com.wesleyegberto.usersauditor.users.User;

public record UserEvent(
		UUID eventId,
		LocalDateTime eventDate,
		UserActionType userAction,
		User user) {
}
