package co.simplon.dev2dev_business.dtos.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueAccountValidator implements ConstraintValidator<UniqueAccount, String> {

	private final AccountRepository accountRepo;

	@Autowired
	public UniqueAccountValidator(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (username == null || username.trim().isEmpty()) {
			return true;
		}
		return accountRepo.findByUsernameIgnoreCase(username).isEmpty();
	}

	public void validateUsername(String username) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Username is required.");
		}
		if (!username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Username must be a valid email.");
		}
		Optional<Account> existing = accountRepo.findByUsernameIgnoreCase(username);
		if (existing.isPresent()) {
			throw new IllegalArgumentException("This email is already taken.");
		}
	}

	public void validatePassword(String password) {
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("Password is required.");
		}
		if (password.length() < 8) {
			throw new IllegalArgumentException("Password must be at least 8 characters.");
		}
		if (!password.matches(".*[a-z].*")) {
			throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
		}
		if (!password.matches(".*[A-Z].*")) {
			throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
		}
		if (!password.matches(".*\\d.*")) {
			throw new IllegalArgumentException("Password must contain at least one digit.");
		}
	}
}
