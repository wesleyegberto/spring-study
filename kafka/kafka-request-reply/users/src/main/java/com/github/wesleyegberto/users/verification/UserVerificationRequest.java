package com.github.wesleyegberto.users.verification;

import com.github.wesleyegberto.users.User;

public record UserVerificationRequest(int userId, String taxId) {
	public static UserVerificationRequest of(User user) {
		return new UserVerificationRequest(user.getId(), user.getTaxId());
	}
}
