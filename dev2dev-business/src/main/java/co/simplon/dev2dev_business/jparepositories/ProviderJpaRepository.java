package co.simplon.dev2dev_business.jparepositories;

import co.simplon.dev2dev_business.entities.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderJpaRepository  extends JpaRepository<ProviderEntity, Integer> {

    Optional<ProviderEntity> findByTitle(String title);

    Optional<ProviderEntity> findByLink(String url);
}
