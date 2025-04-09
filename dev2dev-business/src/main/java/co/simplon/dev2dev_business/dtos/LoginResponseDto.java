package co.simplon.dev2dev_business.dtos;

public record LoginResponseDto(String token, String message) {
    public LoginResponseDto(String token, String message) {
	this.token = token;
	this.message = message;
    }

    public String token() {
	return token;
    }

    public String message() {
	return message;
    }

}
