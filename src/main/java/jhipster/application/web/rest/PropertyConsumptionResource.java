package jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import jhipster.application.domain.PropertyConsumption;

import jhipster.application.repository.PropertyConsumptionRepository;
import jhipster.application.web.rest.errors.BadRequestAlertException;
import jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PropertyConsumption.
 */
@RestController
@RequestMapping("/api")
public class PropertyConsumptionResource {

    private final Logger log = LoggerFactory.getLogger(PropertyConsumptionResource.class);

    private static final String ENTITY_NAME = "propertyConsumption";

    private final PropertyConsumptionRepository propertyConsumptionRepository;

    public PropertyConsumptionResource(PropertyConsumptionRepository propertyConsumptionRepository) {
        this.propertyConsumptionRepository = propertyConsumptionRepository;
    }

    /**
     * POST  /property-consumptions : Create a new propertyConsumption.
     *
     * @param propertyConsumption the propertyConsumption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyConsumption, or with status 400 (Bad Request) if the propertyConsumption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-consumptions")
    @Timed
    public ResponseEntity<PropertyConsumption> createPropertyConsumption(@RequestBody PropertyConsumption propertyConsumption) throws URISyntaxException {
        log.debug("REST request to save PropertyConsumption : {}", propertyConsumption);
        if (propertyConsumption.getId() != null) {
            throw new BadRequestAlertException("A new propertyConsumption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyConsumption result = propertyConsumptionRepository.save(propertyConsumption);
        return ResponseEntity.created(new URI("/api/property-consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-consumptions : Updates an existing propertyConsumption.
     *
     * @param propertyConsumption the propertyConsumption to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyConsumption,
     * or with status 400 (Bad Request) if the propertyConsumption is not valid,
     * or with status 500 (Internal Server Error) if the propertyConsumption couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-consumptions")
    @Timed
    public ResponseEntity<PropertyConsumption> updatePropertyConsumption(@RequestBody PropertyConsumption propertyConsumption) throws URISyntaxException {
        log.debug("REST request to update PropertyConsumption : {}", propertyConsumption);
        if (propertyConsumption.getId() == null) {
            return createPropertyConsumption(propertyConsumption);
        }
        PropertyConsumption result = propertyConsumptionRepository.save(propertyConsumption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyConsumption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-consumptions : get all the propertyConsumptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of propertyConsumptions in body
     */
    @GetMapping("/property-consumptions")
    @Timed
    public List<PropertyConsumption> getAllPropertyConsumptions() {
        log.debug("REST request to get all PropertyConsumptions");
        return propertyConsumptionRepository.findAll();
        }

    /**
     * GET  /property-consumptions/:id : get the "id" propertyConsumption.
     *
     * @param id the id of the propertyConsumption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyConsumption, or with status 404 (Not Found)
     */
    @GetMapping("/property-consumptions/{id}")
    @Timed
    public ResponseEntity<PropertyConsumption> getPropertyConsumption(@PathVariable Long id) {
        log.debug("REST request to get PropertyConsumption : {}", id);
        PropertyConsumption propertyConsumption = propertyConsumptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(propertyConsumption));
    }

    /**
     * DELETE  /property-consumptions/:id : delete the "id" propertyConsumption.
     *
     * @param id the id of the propertyConsumption to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-consumptions/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyConsumption(@PathVariable Long id) {
        log.debug("REST request to delete PropertyConsumption : {}", id);
        propertyConsumptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
