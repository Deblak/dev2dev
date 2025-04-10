package co.simplon.dev2dev_business.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.dev2dev_business.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUsernameIgnoreCase(String username);

	boolean existsByUsernameIgnoreCase(String username);

	Optional<Account> findByUuidToken(String token);

}
