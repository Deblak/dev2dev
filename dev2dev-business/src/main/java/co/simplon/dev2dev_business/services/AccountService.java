package co.simplon.dev2dev_business.services;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.dev2dev_business.configs.JwtProvider;
import co.simplon.dev2dev_business.dtos.AccountCreateDto;
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

	public AccountService(AccountRepository accounts, PasswordEncoder encoder, JwtProvider jwtProvider,
			RoleRepository roleRepository) {
		this.accountsRepo = accounts;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
		this.roleRepository = roleRepository;

	}

	@Transactional
	public void create(AccountCreateDto inputs, Set<String> roleNames) {

		if (inputs.username() == null || inputs.username().trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide.");
		}
		if (accountsRepo.findByUsernameIgnoreCase(inputs.username()).isPresent()) {
			throw new IllegalArgumentException("L'utilisateur avec ce nom existe déjà.");
		}
		Account entity = new Account();
		entity.setUsername(inputs.username());

		String encodedPassword = encoder.encode(inputs.password());
		entity.setPassword(encodedPassword);

		Role role;
		if (roleNames == null || roleNames.isEmpty()) {
			role = roleRepository.findByName("MEMBER")
					.orElseThrow(() -> new RuntimeException("Rôle MEMBER par défaut non trouvé"));
			entity.setRole(role);
		} else {

			String firstRoleName = roleNames.iterator().next();
			String cleanRoleName = firstRoleName.startsWith("ROLE_") ? firstRoleName.substring(5) : firstRoleName;

			role = roleRepository.findByName(cleanRoleName)
					.orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé: " + cleanRoleName));

		}
		accountsRepo.save(entity);
	}

}
