package jhipster.application.repository;

import jhipster.application.domain.PropertyConsumption;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PropertyConsumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyConsumptionRepository extends JpaRepository<PropertyConsumption, Long> {

}
