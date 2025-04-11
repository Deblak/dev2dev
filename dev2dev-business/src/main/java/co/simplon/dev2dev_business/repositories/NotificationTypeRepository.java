package co.simplon.dev2dev_business.repositories;

import co.simplon.dev2dev_business.entities.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    Optional<NotificationType> findByTypeName(String notificationType);

}
