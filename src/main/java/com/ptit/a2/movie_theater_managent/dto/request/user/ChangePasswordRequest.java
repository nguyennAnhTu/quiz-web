package com.ptit.a2.movie_theater_managent.dto.request.user;

public record ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
}
