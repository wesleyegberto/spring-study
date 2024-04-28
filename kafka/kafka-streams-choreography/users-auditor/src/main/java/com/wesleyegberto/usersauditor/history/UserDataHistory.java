package com.wesleyegberto.usersauditor.history;

import java.time.LocalDateTime;
import java.util.UUID;

import com.wesleyegberto.usersauditor.auditing.UserActionType;
import com.wesleyegberto.usersauditor.auditing.UserEvent;
import com.wesleyegberto.usersauditor.users.User;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_history")
public class UserDataHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private UUID eventId;
	private LocalDateTime eventDate;
	@Enumerated(EnumType.STRING)
	private UserActionType action;

	@Embedded
	private User user;

	public UserDataHistory() {
	}

	public UserDataHistory(UUID eventId, LocalDateTime eventDate, UserActionType action, User user) {
		this.eventId = eventId;
		this.eventDate = eventDate;
		this.action = action;
		this.user = user;
	}

	public static UserDataHistory fromEvent(UserEvent event) {
		return new UserDataHistory(event.eventId(), event.eventDate(), event.userAction(), event.user());
	}

	public long getId() {
		return id;
	}

	public UUID getEventId() {
		return eventId;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public UserActionType getAction() {
		return action;
	}

	public User getUser() {
		return user;
	}
}
