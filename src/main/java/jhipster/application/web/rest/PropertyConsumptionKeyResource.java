package jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import jhipster.application.domain.PropertyConsumptionKey;

import jhipster.application.repository.PropertyConsumptionKeyRepository;
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
 * REST controller for managing PropertyConsumptionKey.
 */
@RestController
@RequestMapping("/api")
public class PropertyConsumptionKeyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyConsumptionKeyResource.class);

    private static final String ENTITY_NAME = "propertyConsumptionKey";

    private final PropertyConsumptionKeyRepository propertyConsumptionKeyRepository;

    public PropertyConsumptionKeyResource(PropertyConsumptionKeyRepository propertyConsumptionKeyRepository) {
        this.propertyConsumptionKeyRepository = propertyConsumptionKeyRepository;
    }

    /**
     * POST  /property-consumption-keys : Create a new propertyConsumptionKey.
     *
     * @param propertyConsumptionKey the propertyConsumptionKey to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyConsumptionKey, or with status 400 (Bad Request) if the propertyConsumptionKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-consumption-keys")
    @Timed
    public ResponseEntity<PropertyConsumptionKey> createPropertyConsumptionKey(@RequestBody PropertyConsumptionKey propertyConsumptionKey) throws URISyntaxException {
        log.debug("REST request to save PropertyConsumptionKey : {}", propertyConsumptionKey);
        if (propertyConsumptionKey.getId() != null) {
            throw new BadRequestAlertException("A new propertyConsumptionKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyConsumptionKey result = propertyConsumptionKeyRepository.save(propertyConsumptionKey);
        return ResponseEntity.created(new URI("/api/property-consumption-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-consumption-keys : Updates an existing propertyConsumptionKey.
     *
     * @param propertyConsumptionKey the propertyConsumptionKey to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyConsumptionKey,
     * or with status 400 (Bad Request) if the propertyConsumptionKey is not valid,
     * or with status 500 (Internal Server Error) if the propertyConsumptionKey couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-consumption-keys")
    @Timed
    public ResponseEntity<PropertyConsumptionKey> updatePropertyConsumptionKey(@RequestBody PropertyConsumptionKey propertyConsumptionKey) throws URISyntaxException {
        log.debug("REST request to update PropertyConsumptionKey : {}", propertyConsumptionKey);
        if (propertyConsumptionKey.getId() == null) {
            return createPropertyConsumptionKey(propertyConsumptionKey);
        }
        PropertyConsumptionKey result = propertyConsumptionKeyRepository.save(propertyConsumptionKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyConsumptionKey.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-consumption-keys : get all the propertyConsumptionKeys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of propertyConsumptionKeys in body
     */
    @GetMapping("/property-consumption-keys")
    @Timed
    public List<PropertyConsumptionKey> getAllPropertyConsumptionKeys() {
        log.debug("REST request to get all PropertyConsumptionKeys");
        return propertyConsumptionKeyRepository.findAll();
        }

    /**
     * GET  /property-consumption-keys/:id : get the "id" propertyConsumptionKey.
     *
     * @param id the id of the propertyConsumptionKey to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyConsumptionKey, or with status 404 (Not Found)
     */
    @GetMapping("/property-consumption-keys/{id}")
    @Timed
    public ResponseEntity<PropertyConsumptionKey> getPropertyConsumptionKey(@PathVariable Long id) {
        log.debug("REST request to get PropertyConsumptionKey : {}", id);
        PropertyConsumptionKey propertyConsumptionKey = propertyConsumptionKeyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(propertyConsumptionKey));
    }

    /**
     * DELETE  /property-consumption-keys/:id : delete the "id" propertyConsumptionKey.
     *
     * @param id the id of the propertyConsumptionKey to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-consumption-keys/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyConsumptionKey(@PathVariable Long id) {
        log.debug("REST request to delete PropertyConsumptionKey : {}", id);
        propertyConsumptionKeyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
