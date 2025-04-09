package co.simplon.dev2dev_business.dtos;

public record AuthInfo(String token) {

    @Override
    public String toString() {
	return String.format("{token=[PROTECTED]}, accountInfo=%s}");
    }
}
