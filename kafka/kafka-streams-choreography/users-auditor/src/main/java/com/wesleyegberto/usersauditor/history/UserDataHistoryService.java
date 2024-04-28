package com.wesleyegberto.usersauditor.history;

import com.wesleyegberto.usersauditor.auditing.UserEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserDataHistoryService {
	private static final Logger LOG = LoggerFactory.getLogger(UserDataHistoryService.class);

	private final UserHistoryRepository history;

	public UserDataHistoryService(UserHistoryRepository history) {
		this.history = history;
	}

	public void save(UserEvent userEvent) {
		LOG.info("Event ID {} - Action {} - User ID {}",
				userEvent.eventId(), userEvent.userAction(), userEvent.user().getId());
		var userData = UserDataHistory.fromEvent(userEvent);
		this.history.save(userData);
	}
}
