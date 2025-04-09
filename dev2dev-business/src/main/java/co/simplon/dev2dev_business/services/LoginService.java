package co.simplon.dev2dev_business.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.simplon.dev2dev_business.configs.JwtProvider;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.dtos.LoginResponseDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.repositories.AccountRepository;

@Service
public class LoginService {
    private final AccountRepository repository;
    private PasswordEncoder passwordEncoder;
    private final JwtProvider provider;

    protected LoginService(AccountRepository repository, PasswordEncoder passwordEncoder, JwtProvider provider) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
	this.provider = provider;
    }

    public LoginResponseDto LoginResponseDto(AccountLoginDto inputs) {
	String username = inputs.username();
	Account entity = repository.findByUsernameIgnoreCase(username)
		.orElseThrow(() -> new BadCredentialsException(username));
	System.out.println(entity);
	String password = inputs.password();
	String encoded = entity.getPassword();

	if (passwordEncoder.matches(password, encoded)) {
	    String token = provider.create(username);
	    return new LoginResponseDto(token, "Authentication successful");
	} else {
	    throw new BadCredentialsException(username);
	}
    }

    public Account accountInfo() {
	return repository.findAll().getLast();
    }

}
