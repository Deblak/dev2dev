package co.simplon.dev2dev_business.services;

import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.simplon.dev2dev_business.configs.JwtProvider;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.dtos.LoginResponseDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.Role;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import co.simplon.dev2dev_business.repositories.RoleRepository;

@Service
public class LoginService {
    private final AccountRepository repository;
    private PasswordEncoder passwordEncoder;
    private final JwtProvider provider;
    private final RoleRepository roleRepository;

    protected LoginService(AccountRepository repository, PasswordEncoder passwordEncoder, JwtProvider provider,
	    RoleRepository roleRepository) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
	this.provider = provider;
	this.roleRepository = roleRepository;
    }

    public LoginResponseDto LoginResponseDto(AccountLoginDto inputs) {
	String username = inputs.username();
	Account entity = repository.findByUsernameIgnoreCase(username)
		.orElseThrow(() -> new BadCredentialsException(username));
	String password = inputs.password();
	String encoded = entity.getPassword();

	Role role = entity.getRole();
	if (role == null) {
	    role = roleRepository.findByName("ROLE_MEMBER")
		    .orElseThrow(() -> new IllegalStateException("Role not found"));
	}
	String roleName = role.getName();

	if (passwordEncoder.matches(password, encoded)) {
	    String token = provider.create(username, Set.of(roleName));

	    return new LoginResponseDto(token, "Authentication successful");
	} else {
	    throw new BadCredentialsException(username);
	}
    }

    public Account accountInfo() {
	return repository.findAll().getLast();
    }

}
