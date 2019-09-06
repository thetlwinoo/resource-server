package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Cities;
import com.resource.server.repository.CitiesRepository;
import com.resource.server.service.CitiesService;
import com.resource.server.service.dto.CitiesDTO;
import com.resource.server.service.mapper.CitiesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CitiesResource REST controller.
 *
 * @see CitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CitiesResourceIntTest {

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCitiesMockMvc;

    private Cities cities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CitiesResource citiesResource = new CitiesResource(citiesService);
        this.restCitiesMockMvc = MockMvcBuilders.standaloneSetup(citiesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createEntity(EntityManager em) {
        Cities cities = new Cities()
            .cityName(DEFAULT_CITY_NAME)
            .location(DEFAULT_LOCATION)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return cities;
    }

    @Before
    public void initTest() {
        cities = createEntity(em);
    }

    @Test
    @Transactional
    public void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);
        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCities.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCities.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testCities.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCities.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // Create the Cities with an existing ID
        cities.setId(1L);
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setCityName(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setValidFrom(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setValidTo(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList
        restCitiesMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc.perform(get("/api/cities/{id}", cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        // Disconnect from session so that the updates on updatedCities are not directly saved in db
        em.detach(updatedCities);
        updatedCities
            .cityName(UPDATED_CITY_NAME)
            .location(UPDATED_LOCATION)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CitiesDTO citiesDTO = citiesMapper.toDto(updatedCities);

        restCitiesMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCities.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCities.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testCities.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCities.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc.perform(delete("/api/cities/{id}", cities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cities.class);
        Cities cities1 = new Cities();
        cities1.setId(1L);
        Cities cities2 = new Cities();
        cities2.setId(cities1.getId());
        assertThat(cities1).isEqualTo(cities2);
        cities2.setId(2L);
        assertThat(cities1).isNotEqualTo(cities2);
        cities1.setId(null);
        assertThat(cities1).isNotEqualTo(cities2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesDTO.class);
        CitiesDTO citiesDTO1 = new CitiesDTO();
        citiesDTO1.setId(1L);
        CitiesDTO citiesDTO2 = new CitiesDTO();
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
        citiesDTO2.setId(citiesDTO1.getId());
        assertThat(citiesDTO1).isEqualTo(citiesDTO2);
        citiesDTO2.setId(2L);
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
        citiesDTO1.setId(null);
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(citiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(citiesMapper.fromId(null)).isNull();
    }
}
