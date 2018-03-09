package jhipster.application.web.rest;

import jhipster.application.DbdemoApp;

import jhipster.application.domain.PropertyConsumptionKey;
import jhipster.application.repository.PropertyConsumptionKeyRepository;
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
 * Test class for the PropertyConsumptionKeyResource REST controller.
 *
 * @see PropertyConsumptionKeyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbdemoApp.class)
public class PropertyConsumptionKeyResourceIntTest {

    private static final String DEFAULT_PROPERTY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_ROOMS = 1;
    private static final Integer UPDATED_NUMBER_OF_ROOMS = 2;

    private static final Integer DEFAULT_NUMBER_OF_PEOLE = 1;
    private static final Integer UPDATED_NUMBER_OF_PEOLE = 2;

    private static final Integer DEFAULT_ELECTRICITY_AVERAGE = 1;
    private static final Integer UPDATED_ELECTRICITY_AVERAGE = 2;

    private static final Integer DEFAULT_GET_AVERAGE = 1;
    private static final Integer UPDATED_GET_AVERAGE = 2;

    @Autowired
    private PropertyConsumptionKeyRepository propertyConsumptionKeyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyConsumptionKeyMockMvc;

    private PropertyConsumptionKey propertyConsumptionKey;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyConsumptionKeyResource propertyConsumptionKeyResource = new PropertyConsumptionKeyResource(propertyConsumptionKeyRepository);
        this.restPropertyConsumptionKeyMockMvc = MockMvcBuilders.standaloneSetup(propertyConsumptionKeyResource)
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
    public static PropertyConsumptionKey createEntity(EntityManager em) {
        PropertyConsumptionKey propertyConsumptionKey = new PropertyConsumptionKey()
            .propertyType(DEFAULT_PROPERTY_TYPE)
            .numberOfRooms(DEFAULT_NUMBER_OF_ROOMS)
            .numberOfPeole(DEFAULT_NUMBER_OF_PEOLE)
            .electricityAverage(DEFAULT_ELECTRICITY_AVERAGE)
            .getAverage(DEFAULT_GET_AVERAGE);
        return propertyConsumptionKey;
    }

    @Before
    public void initTest() {
        propertyConsumptionKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropertyConsumptionKey() throws Exception {
        int databaseSizeBeforeCreate = propertyConsumptionKeyRepository.findAll().size();

        // Create the PropertyConsumptionKey
        restPropertyConsumptionKeyMockMvc.perform(post("/api/property-consumption-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumptionKey)))
            .andExpect(status().isCreated());

        // Validate the PropertyConsumptionKey in the database
        List<PropertyConsumptionKey> propertyConsumptionKeyList = propertyConsumptionKeyRepository.findAll();
        assertThat(propertyConsumptionKeyList).hasSize(databaseSizeBeforeCreate + 1);
        PropertyConsumptionKey testPropertyConsumptionKey = propertyConsumptionKeyList.get(propertyConsumptionKeyList.size() - 1);
        assertThat(testPropertyConsumptionKey.getPropertyType()).isEqualTo(DEFAULT_PROPERTY_TYPE);
        assertThat(testPropertyConsumptionKey.getNumberOfRooms()).isEqualTo(DEFAULT_NUMBER_OF_ROOMS);
        assertThat(testPropertyConsumptionKey.getNumberOfPeole()).isEqualTo(DEFAULT_NUMBER_OF_PEOLE);
        assertThat(testPropertyConsumptionKey.getElectricityAverage()).isEqualTo(DEFAULT_ELECTRICITY_AVERAGE);
        assertThat(testPropertyConsumptionKey.getGetAverage()).isEqualTo(DEFAULT_GET_AVERAGE);
    }

    @Test
    @Transactional
    public void createPropertyConsumptionKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyConsumptionKeyRepository.findAll().size();

        // Create the PropertyConsumptionKey with an existing ID
        propertyConsumptionKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyConsumptionKeyMockMvc.perform(post("/api/property-consumption-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumptionKey)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyConsumptionKey in the database
        List<PropertyConsumptionKey> propertyConsumptionKeyList = propertyConsumptionKeyRepository.findAll();
        assertThat(propertyConsumptionKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPropertyConsumptionKeys() throws Exception {
        // Initialize the database
        propertyConsumptionKeyRepository.saveAndFlush(propertyConsumptionKey);

        // Get all the propertyConsumptionKeyList
        restPropertyConsumptionKeyMockMvc.perform(get("/api/property-consumption-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyConsumptionKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numberOfRooms").value(hasItem(DEFAULT_NUMBER_OF_ROOMS)))
            .andExpect(jsonPath("$.[*].numberOfPeole").value(hasItem(DEFAULT_NUMBER_OF_PEOLE)))
            .andExpect(jsonPath("$.[*].electricityAverage").value(hasItem(DEFAULT_ELECTRICITY_AVERAGE)))
            .andExpect(jsonPath("$.[*].getAverage").value(hasItem(DEFAULT_GET_AVERAGE)));
    }

    @Test
    @Transactional
    public void getPropertyConsumptionKey() throws Exception {
        // Initialize the database
        propertyConsumptionKeyRepository.saveAndFlush(propertyConsumptionKey);

        // Get the propertyConsumptionKey
        restPropertyConsumptionKeyMockMvc.perform(get("/api/property-consumption-keys/{id}", propertyConsumptionKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propertyConsumptionKey.getId().intValue()))
            .andExpect(jsonPath("$.propertyType").value(DEFAULT_PROPERTY_TYPE.toString()))
            .andExpect(jsonPath("$.numberOfRooms").value(DEFAULT_NUMBER_OF_ROOMS))
            .andExpect(jsonPath("$.numberOfPeole").value(DEFAULT_NUMBER_OF_PEOLE))
            .andExpect(jsonPath("$.electricityAverage").value(DEFAULT_ELECTRICITY_AVERAGE))
            .andExpect(jsonPath("$.getAverage").value(DEFAULT_GET_AVERAGE));
    }

    @Test
    @Transactional
    public void getNonExistingPropertyConsumptionKey() throws Exception {
        // Get the propertyConsumptionKey
        restPropertyConsumptionKeyMockMvc.perform(get("/api/property-consumption-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropertyConsumptionKey() throws Exception {
        // Initialize the database
        propertyConsumptionKeyRepository.saveAndFlush(propertyConsumptionKey);
        int databaseSizeBeforeUpdate = propertyConsumptionKeyRepository.findAll().size();

        // Update the propertyConsumptionKey
        PropertyConsumptionKey updatedPropertyConsumptionKey = propertyConsumptionKeyRepository.findOne(propertyConsumptionKey.getId());
        // Disconnect from session so that the updates on updatedPropertyConsumptionKey are not directly saved in db
        em.detach(updatedPropertyConsumptionKey);
        updatedPropertyConsumptionKey
            .propertyType(UPDATED_PROPERTY_TYPE)
            .numberOfRooms(UPDATED_NUMBER_OF_ROOMS)
            .numberOfPeole(UPDATED_NUMBER_OF_PEOLE)
            .electricityAverage(UPDATED_ELECTRICITY_AVERAGE)
            .getAverage(UPDATED_GET_AVERAGE);

        restPropertyConsumptionKeyMockMvc.perform(put("/api/property-consumption-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropertyConsumptionKey)))
            .andExpect(status().isOk());

        // Validate the PropertyConsumptionKey in the database
        List<PropertyConsumptionKey> propertyConsumptionKeyList = propertyConsumptionKeyRepository.findAll();
        assertThat(propertyConsumptionKeyList).hasSize(databaseSizeBeforeUpdate);
        PropertyConsumptionKey testPropertyConsumptionKey = propertyConsumptionKeyList.get(propertyConsumptionKeyList.size() - 1);
        assertThat(testPropertyConsumptionKey.getPropertyType()).isEqualTo(UPDATED_PROPERTY_TYPE);
        assertThat(testPropertyConsumptionKey.getNumberOfRooms()).isEqualTo(UPDATED_NUMBER_OF_ROOMS);
        assertThat(testPropertyConsumptionKey.getNumberOfPeole()).isEqualTo(UPDATED_NUMBER_OF_PEOLE);
        assertThat(testPropertyConsumptionKey.getElectricityAverage()).isEqualTo(UPDATED_ELECTRICITY_AVERAGE);
        assertThat(testPropertyConsumptionKey.getGetAverage()).isEqualTo(UPDATED_GET_AVERAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropertyConsumptionKey() throws Exception {
        int databaseSizeBeforeUpdate = propertyConsumptionKeyRepository.findAll().size();

        // Create the PropertyConsumptionKey

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertyConsumptionKeyMockMvc.perform(put("/api/property-consumption-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyConsumptionKey)))
            .andExpect(status().isCreated());

        // Validate the PropertyConsumptionKey in the database
        List<PropertyConsumptionKey> propertyConsumptionKeyList = propertyConsumptionKeyRepository.findAll();
        assertThat(propertyConsumptionKeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePropertyConsumptionKey() throws Exception {
        // Initialize the database
        propertyConsumptionKeyRepository.saveAndFlush(propertyConsumptionKey);
        int databaseSizeBeforeDelete = propertyConsumptionKeyRepository.findAll().size();

        // Get the propertyConsumptionKey
        restPropertyConsumptionKeyMockMvc.perform(delete("/api/property-consumption-keys/{id}", propertyConsumptionKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PropertyConsumptionKey> propertyConsumptionKeyList = propertyConsumptionKeyRepository.findAll();
        assertThat(propertyConsumptionKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyConsumptionKey.class);
        PropertyConsumptionKey propertyConsumptionKey1 = new PropertyConsumptionKey();
        propertyConsumptionKey1.setId(1L);
        PropertyConsumptionKey propertyConsumptionKey2 = new PropertyConsumptionKey();
        propertyConsumptionKey2.setId(propertyConsumptionKey1.getId());
        assertThat(propertyConsumptionKey1).isEqualTo(propertyConsumptionKey2);
        propertyConsumptionKey2.setId(2L);
        assertThat(propertyConsumptionKey1).isNotEqualTo(propertyConsumptionKey2);
        propertyConsumptionKey1.setId(null);
        assertThat(propertyConsumptionKey1).isNotEqualTo(propertyConsumptionKey2);
    }
}
