package co.simplon.dev2dev_business.dtos;

import java.util.Set;

public record AccountCreateDto(Long id, String username, String password, Set<String> roles) {

	@Override
	public String toString() {
		return String.format("{username=%s,password=[PROTECTED]}", id, username, roles);
	}
}
