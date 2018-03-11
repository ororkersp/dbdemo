package jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import jhipster.application.domain.PropertyConsumption;
import jhipster.application.domain.PropertyConsumptionKey;
import jhipster.application.repository.PropertyConsumptionRepository;
import jhipster.application.web.rest.util.HeaderUtil;
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
        PropertyConsumption result = propertyConsumptionRepository.save(propertyConsumption);
        return ResponseEntity.created(new URI("/api/property-consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
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
        return propertyConsumptionRepository.findAll();
        }

    /**
     * GET  /property-consumptions/:id : get the "id" propertyConsumption.
     *
     * @param id the id of the propertyConsumption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyConsumption, or with status 404 (Not Found)
     */
    @GetMapping("/property-consumptions/{propertyType}/{numberOfRooms}/{numberOfPeople}")
    @Timed
    public ResponseEntity<PropertyConsumption> getPropertyConsumption(
        @PathVariable String propertyType,
        @PathVariable Integer numberOfRooms,
        @PathVariable Integer numberOfPeople) {
        final PropertyConsumptionKey propertyConsumptionKey =
            new PropertyConsumptionKey()
                .propertyType(propertyType)
                .numberOfRooms(numberOfRooms)
                .numberOfPeople(numberOfPeople);
        PropertyConsumption propertyConsumption = propertyConsumptionRepository.findOne(propertyConsumptionKey);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(propertyConsumption));
    }

}
