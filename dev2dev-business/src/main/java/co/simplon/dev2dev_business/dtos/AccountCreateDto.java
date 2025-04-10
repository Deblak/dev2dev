package co.simplon.dev2dev_business.dtos;

import jakarta.validation.constraints.NotEmpty;

public record AccountCreateDto(Long id, @NotEmpty(message = "Username cannot be empty") String username,
		@NotEmpty(message = "Password cannot be empty") String password, String uuidToken) {

	@Override
	public String toString() {
		return String.format("{username=%s,password=[PROTECTED]}", id, username);
	}
}
