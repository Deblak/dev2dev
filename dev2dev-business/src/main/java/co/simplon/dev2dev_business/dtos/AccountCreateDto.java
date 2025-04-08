package co.simplon.dev2dev_business.dtos;

public record AccountCreateDto(Long id, String username, String password) {

	@Override
	public String toString() {
		return String.format("{username=%s,password=[PROTECTED]}", id, username);
	}
}
