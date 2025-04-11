package co.simplon.dev2dev_business.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.simplon.dev2dev_business.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUsernameIgnoreCase(String username);

	boolean existsByUsernameIgnoreCase(String username);

	Optional<Account> findByUuidToken(String token);

	String FIND_ACCOUNT_BY_NOTIFICATION_TYPE = """
			SELECT username  FROM t_accounts ta
			WHERE id IN ( SELECT account_id  FROM t_account_notification_type tant
			WHERE notification_type_id = :notificationTypeId );
			""";
	@Query(value = FIND_ACCOUNT_BY_NOTIFICATION_TYPE, nativeQuery = true)
	List<String> findAccountByNotificationType(@Param("notificationTypeId") Long notificationTypeId);

}
