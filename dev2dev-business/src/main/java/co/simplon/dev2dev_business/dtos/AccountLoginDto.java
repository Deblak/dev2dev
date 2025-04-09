package co.simplon.dev2dev_business.dtos;

public record AccountLoginDto(String username, String password, boolean flag) {
    @Override
    public String toString() {
	return String.format("{username=%s, password=[PROTECTED]}", username);
    }
}
