package jhipster.application.web.rest;

import jhipster.application.DbdemoApp;

import jhipster.application.domain.PropertyConsumption;
import jhipster.application.repository.PropertyConsumptionRepository;
import jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PropertyConsumptionResource REST controller.
 *
 * @see PropertyConsumptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbdemoApp.class)
public class PropertyConsumptionResourceIntTest {

    private static final Integer DEFAULT_ELECTRICITY_AVERAGE = 1;
    private static final Integer UPDATED_ELECTRICITY_AVERAGE = 2;

    private static final Integer DEFAULT_GET_AVERAGE = 1;
    private static final Integer UPDATED_GET_AVERAGE = 2;

    @Autowired
    private PropertyConsumptionRepository propertyConsumptionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyConsumptionMockMvc;

    private PropertyConsumption propertyConsumption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyConsumptionResource propertyConsumptionResource = new PropertyConsumptionResource(propertyConsumptionRepository);
        this.restPropertyConsumptionMockMvc = MockMvcBuilders.standaloneSetup(propertyConsumptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropertyConsumption createEntity(EntityManager em) {
        PropertyConsumption propertyConsumption = new PropertyConsumption()
            .electricityAverage(DEFAULT_ELECTRICITY_AVERAGE)
            .getAverage(DEFAULT_GET_AVERAGE);
        return propertyConsumption;
    }

    @Before
    public void initTest() {
        propertyConsumption = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropertyConsumption() throws Exception {
        int databaseSizeBeforeCreate = propertyConsumptionRepository.findAll().size();

        // Create the PropertyConsumption
        restPropertyConsumptionMockMvc.perform(post("/api/property-consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumption)))
            .andExpect(status().isCreated());

        // Validate the PropertyConsumption in the database
        List<PropertyConsumption> propertyConsumptionList = propertyConsumptionRepository.findAll();
        assertThat(propertyConsumptionList).hasSize(databaseSizeBeforeCreate + 1);
        PropertyConsumption testPropertyConsumption = propertyConsumptionList.get(propertyConsumptionList.size() - 1);
        assertThat(testPropertyConsumption.getElectricityAverage()).isEqualTo(DEFAULT_ELECTRICITY_AVERAGE);
        assertThat(testPropertyConsumption.getGetAverage()).isEqualTo(DEFAULT_GET_AVERAGE);
    }

    @Test
    @Transactional
    public void createPropertyConsumptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyConsumptionRepository.findAll().size();

        // Create the PropertyConsumption with an existing ID
        propertyConsumption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyConsumptionMockMvc.perform(post("/api/property-consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumption)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyConsumption in the database
        List<PropertyConsumption> propertyConsumptionList = propertyConsumptionRepository.findAll();
        assertThat(propertyConsumptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPropertyConsumptions() throws Exception {
        // Initialize the database
        propertyConsumptionRepository.saveAndFlush(propertyConsumption);

        // Get all the propertyConsumptionList
        restPropertyConsumptionMockMvc.perform(get("/api/property-consumptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyConsumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].electricityAverage").value(hasItem(DEFAULT_ELECTRICITY_AVERAGE)))
            .andExpect(jsonPath("$.[*].getAverage").value(hasItem(DEFAULT_GET_AVERAGE)));
    }

    @Test
    @Transactional
    public void getPropertyConsumption() throws Exception {
        // Initialize the database
        propertyConsumptionRepository.saveAndFlush(propertyConsumption);

        // Get the propertyConsumption
        restPropertyConsumptionMockMvc.perform(get("/api/property-consumptions/{id}", propertyConsumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propertyConsumption.getId().intValue()))
            .andExpect(jsonPath("$.electricityAverage").value(DEFAULT_ELECTRICITY_AVERAGE))
            .andExpect(jsonPath("$.getAverage").value(DEFAULT_GET_AVERAGE));
    }

    @Test
    @Transactional
    public void getNonExistingPropertyConsumption() throws Exception {
        // Get the propertyConsumption
        restPropertyConsumptionMockMvc.perform(get("/api/property-consumptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropertyConsumption() throws Exception {
        // Initialize the database
        propertyConsumptionRepository.saveAndFlush(propertyConsumption);
        int databaseSizeBeforeUpdate = propertyConsumptionRepository.findAll().size();

        // Update the propertyConsumption
        PropertyConsumption updatedPropertyConsumption = propertyConsumptionRepository.findOne(propertyConsumption.getId());
        // Disconnect from session so that the updates on updatedPropertyConsumption are not directly saved in db
        em.detach(updatedPropertyConsumption);
        updatedPropertyConsumption
            .electricityAverage(UPDATED_ELECTRICITY_AVERAGE)
            .getAverage(UPDATED_GET_AVERAGE);

        restPropertyConsumptionMockMvc.perform(put("/api/property-consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropertyConsumption)))
            .andExpect(status().isOk());

        // Validate the PropertyConsumption in the database
        List<PropertyConsumption> propertyConsumptionList = propertyConsumptionRepository.findAll();
        assertThat(propertyConsumptionList).hasSize(databaseSizeBeforeUpdate);
        PropertyConsumption testPropertyConsumption = propertyConsumptionList.get(propertyConsumptionList.size() - 1);
        assertThat(testPropertyConsumption.getElectricityAverage()).isEqualTo(UPDATED_ELECTRICITY_AVERAGE);
        assertThat(testPropertyConsumption.getGetAverage()).isEqualTo(UPDATED_GET_AVERAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropertyConsumption() throws Exception {
        int databaseSizeBeforeUpdate = propertyConsumptionRepository.findAll().size();

        // Create the PropertyConsumption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertyConsumptionMockMvc.perform(put("/api/property-consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumption)))
            .andExpect(status().isCreated());

        // Validate the PropertyConsumption in the database
        List<PropertyConsumption> propertyConsumptionList = propertyConsumptionRepository.findAll();
        assertThat(propertyConsumptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePropertyConsumption() throws Exception {
        // Initialize the database
        propertyConsumptionRepository.saveAndFlush(propertyConsumption);
        int databaseSizeBeforeDelete = propertyConsumptionRepository.findAll().size();

        // Get the propertyConsumption
        restPropertyConsumptionMockMvc.perform(delete("/api/property-consumptions/{id}", propertyConsumption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PropertyConsumption> propertyConsumptionList = propertyConsumptionRepository.findAll();
        assertThat(propertyConsumptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyConsumption.class);
        PropertyConsumption propertyConsumption1 = new PropertyConsumption();
        propertyConsumption1.setId(1L);
        PropertyConsumption propertyConsumption2 = new PropertyConsumption();
        propertyConsumption2.setId(propertyConsumption1.getId());
        assertThat(propertyConsumption1).isEqualTo(propertyConsumption2);
        propertyConsumption2.setId(2L);
        assertThat(propertyConsumption1).isNotEqualTo(propertyConsumption2);
        propertyConsumption1.setId(null);
        assertThat(propertyConsumption1).isNotEqualTo(propertyConsumption2);
    }
}
