package com.github.wesleyegberto.users.verification;

import java.util.UUID;

public record UserVerificationResponse(UUID verificationId, int userId, boolean approved, String rejectionMessage) { }
