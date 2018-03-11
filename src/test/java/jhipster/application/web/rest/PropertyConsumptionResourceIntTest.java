package jhipster.application.web.rest;

import jhipster.application.DbdemoApp;

import jhipster.application.domain.PropertyConsumption;
import jhipster.application.domain.PropertyConsumptionKey;
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
        final PropertyConsumptionKey propertyConsumptionKey =
            PropertyConsumptionKey.builder()
                .propertyType("FLAT")
                .numberOfRooms(2)
                .numberOfPeople(4)
                .build();

        PropertyConsumption propertyConsumption =
            PropertyConsumption.builder()
            .electricityAverage(DEFAULT_ELECTRICITY_AVERAGE)
            .gasAverage(DEFAULT_GET_AVERAGE)
                .build();
        propertyConsumption.setId(propertyConsumptionKey);

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
        assertThat(testPropertyConsumption.getGasAverage()).isEqualTo(DEFAULT_GET_AVERAGE);
    }


    @Test
    @Transactional
    public void getPropertyConsumption() throws Exception {
        // Initialize the database
        propertyConsumptionRepository.saveAndFlush(propertyConsumption);

        // Get the propertyConsumption
        restPropertyConsumptionMockMvc.perform(get("/api/property-consumptions/FLAT/2/4", propertyConsumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.propertyType").value(propertyConsumption.getId().getPropertyType()))
//            .andExpect(jsonPath("$.numberOfRooms").value(propertyConsumption.getId().getNumberOfRooms()))
//            .andExpect(jsonPath("$.numberOfPeople").value(propertyConsumption.getId().getNumberOfPeople()))
            .andExpect(jsonPath("$.electricityAverage").value(DEFAULT_ELECTRICITY_AVERAGE))
            .andExpect(jsonPath("$.gasAverage").value(DEFAULT_GET_AVERAGE));
    }

}
