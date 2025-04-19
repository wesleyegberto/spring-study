package com.github.wesleyegberto.usersverifier.verification;

import java.util.UUID;

public record UserVerificationResponse(UUID verificationId, int userId, boolean approved, String rejectionMessage) {
	public static UserVerificationResponse approved(UUID verificationId, int userId) {
		return new UserVerificationResponse(verificationId, userId, true, "");
	}

	public static UserVerificationResponse rejected(UUID verificationId, int userId, String rejectionMessage) {
		return new UserVerificationResponse(verificationId, userId, false, rejectionMessage);
	}
}
