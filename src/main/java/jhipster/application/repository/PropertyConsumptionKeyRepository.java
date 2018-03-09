package jhipster.application.repository;

import jhipster.application.domain.PropertyConsumptionKey;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PropertyConsumptionKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyConsumptionKeyRepository extends JpaRepository<PropertyConsumptionKey, Long> {

}
