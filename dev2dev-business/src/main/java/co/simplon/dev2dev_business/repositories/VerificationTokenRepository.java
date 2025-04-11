package co.simplon.dev2dev_business.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByAccount(Account account);

    void deleteByAccount(Account account);
}
