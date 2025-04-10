package co.simplon.dev2dev_business.services;

import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.dev2dev_business.configs.JwtProvider;
import co.simplon.dev2dev_business.dtos.AccountCreateDto;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.dtos.LoginResponseDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.Role;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import co.simplon.dev2dev_business.repositories.RoleRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountsRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final EmailVerificationService email;

    protected AccountService(AccountRepository accountsRepo, RoleRepository roleRepository, PasswordEncoder encoder,
	    JwtProvider jwtProvider, EmailVerificationService email) {
	this.accountsRepo = accountsRepo;
	this.roleRepository = roleRepository;
	this.encoder = encoder;
	this.jwtProvider = jwtProvider;
	this.email = email;
    }

    @Transactional
    public void create(AccountCreateDto inputs) {

	if (inputs.username() == null || inputs.username().trim().isEmpty()) {
	    throw new IllegalArgumentException("Username cannot be empty.");
	}
	if (accountsRepo.findByUsernameIgnoreCase(inputs.username()).isPresent()) {
	    throw new IllegalArgumentException("The user with that name already exists.");
	}
	Account entity = new Account();
	entity.setUsername(inputs.username());

	String encodedPassword = encoder.encode(inputs.password());
	entity.setPassword(encodedPassword);

	Role role;
	role = roleRepository.findByName("MEMBER")
		.orElseThrow(() -> new RuntimeException("Default MEMBER role not found"));
	entity.setRole(role);

	accountsRepo.save(entity);
    }

    public LoginResponseDto LoginResponseDto(AccountLoginDto inputs) {
	String username = inputs.username();
	Account entity = accountsRepo.findByUsernameIgnoreCase(username)
		.orElseThrow(() -> new BadCredentialsException(username));
	String password = inputs.password();
	String encoded = entity.getPassword();

	Role role = entity.getRole();
	String roleName = role.getName();

	if (encoder.matches(password, encoded)) {
	    String token = jwtProvider.create(username, Set.of(roleName));
	    email.sendVerificationCode(username);

	    return new LoginResponseDto(token, "Authentication successful", roleName);
	} else {
	    throw new BadCredentialsException(username);
	}
    }

}
