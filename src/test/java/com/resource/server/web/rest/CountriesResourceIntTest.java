package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Countries;
import com.resource.server.repository.CountriesRepository;
import com.resource.server.service.CountriesService;
import com.resource.server.service.dto.CountriesDTO;
import com.resource.server.service.mapper.CountriesMapper;
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
 * Test class for the CountriesResource REST controller.
 *
 * @see CountriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CountriesResourceIntTest {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO_APLHA_3_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISO_APLHA_3_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISO_NUMERIC_CODE = 1;
    private static final Integer UPDATED_ISO_NUMERIC_CODE = 2;

    private static final String DEFAULT_COUNTRY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;

    private static final String DEFAULT_CONTINENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBREGION = "AAAAAAAAAA";
    private static final String UPDATED_SUBREGION = "BBBBBBBBBB";

    private static final String DEFAULT_BORDER = "AAAAAAAAAA";
    private static final String UPDATED_BORDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CountriesMapper countriesMapper;

    @Autowired
    private CountriesService countriesService;

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

    private MockMvc restCountriesMockMvc;

    private Countries countries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountriesResource countriesResource = new CountriesResource(countriesService);
        this.restCountriesMockMvc = MockMvcBuilders.standaloneSetup(countriesResource)
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
    public static Countries createEntity(EntityManager em) {
        Countries countries = new Countries()
            .countryName(DEFAULT_COUNTRY_NAME)
            .formalName(DEFAULT_FORMAL_NAME)
            .isoAplha3Code(DEFAULT_ISO_APLHA_3_CODE)
            .isoNumericCode(DEFAULT_ISO_NUMERIC_CODE)
            .countryType(DEFAULT_COUNTRY_TYPE)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .continent(DEFAULT_CONTINENT)
            .region(DEFAULT_REGION)
            .subregion(DEFAULT_SUBREGION)
            .border(DEFAULT_BORDER)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return countries;
    }

    @Before
    public void initTest() {
        countries = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountries() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);
        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate + 1);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountries.getFormalName()).isEqualTo(DEFAULT_FORMAL_NAME);
        assertThat(testCountries.getIsoAplha3Code()).isEqualTo(DEFAULT_ISO_APLHA_3_CODE);
        assertThat(testCountries.getIsoNumericCode()).isEqualTo(DEFAULT_ISO_NUMERIC_CODE);
        assertThat(testCountries.getCountryType()).isEqualTo(DEFAULT_COUNTRY_TYPE);
        assertThat(testCountries.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testCountries.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testCountries.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCountries.getSubregion()).isEqualTo(DEFAULT_SUBREGION);
        assertThat(testCountries.getBorder()).isEqualTo(DEFAULT_BORDER);
        assertThat(testCountries.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCountries.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCountriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // Create the Countries with an existing ID
        countries.setId(1L);
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setCountryName(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setFormalName(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContinentIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setContinent(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setRegion(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubregionIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setSubregion(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setValidFrom(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setValidTo(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList
        restCountriesMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].formalName").value(hasItem(DEFAULT_FORMAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].isoAplha3Code").value(hasItem(DEFAULT_ISO_APLHA_3_CODE.toString())))
            .andExpect(jsonPath("$.[*].isoNumericCode").value(hasItem(DEFAULT_ISO_NUMERIC_CODE)))
            .andExpect(jsonPath("$.[*].countryType").value(hasItem(DEFAULT_COUNTRY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].subregion").value(hasItem(DEFAULT_SUBREGION.toString())))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get the countries
        restCountriesMockMvc.perform(get("/api/countries/{id}", countries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countries.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.formalName").value(DEFAULT_FORMAL_NAME.toString()))
            .andExpect(jsonPath("$.isoAplha3Code").value(DEFAULT_ISO_APLHA_3_CODE.toString()))
            .andExpect(jsonPath("$.isoNumericCode").value(DEFAULT_ISO_NUMERIC_CODE))
            .andExpect(jsonPath("$.countryType").value(DEFAULT_COUNTRY_TYPE.toString()))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.subregion").value(DEFAULT_SUBREGION.toString()))
            .andExpect(jsonPath("$.border").value(DEFAULT_BORDER.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountries() throws Exception {
        // Get the countries
        restCountriesMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries
        Countries updatedCountries = countriesRepository.findById(countries.getId()).get();
        // Disconnect from session so that the updates on updatedCountries are not directly saved in db
        em.detach(updatedCountries);
        updatedCountries
            .countryName(UPDATED_COUNTRY_NAME)
            .formalName(UPDATED_FORMAL_NAME)
            .isoAplha3Code(UPDATED_ISO_APLHA_3_CODE)
            .isoNumericCode(UPDATED_ISO_NUMERIC_CODE)
            .countryType(UPDATED_COUNTRY_TYPE)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .continent(UPDATED_CONTINENT)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .border(UPDATED_BORDER)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CountriesDTO countriesDTO = countriesMapper.toDto(updatedCountries);

        restCountriesMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountries.getFormalName()).isEqualTo(UPDATED_FORMAL_NAME);
        assertThat(testCountries.getIsoAplha3Code()).isEqualTo(UPDATED_ISO_APLHA_3_CODE);
        assertThat(testCountries.getIsoNumericCode()).isEqualTo(UPDATED_ISO_NUMERIC_CODE);
        assertThat(testCountries.getCountryType()).isEqualTo(UPDATED_COUNTRY_TYPE);
        assertThat(testCountries.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testCountries.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testCountries.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCountries.getSubregion()).isEqualTo(UPDATED_SUBREGION);
        assertThat(testCountries.getBorder()).isEqualTo(UPDATED_BORDER);
        assertThat(testCountries.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCountries.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeDelete = countriesRepository.findAll().size();

        // Delete the countries
        restCountriesMockMvc.perform(delete("/api/countries/{id}", countries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Countries.class);
        Countries countries1 = new Countries();
        countries1.setId(1L);
        Countries countries2 = new Countries();
        countries2.setId(countries1.getId());
        assertThat(countries1).isEqualTo(countries2);
        countries2.setId(2L);
        assertThat(countries1).isNotEqualTo(countries2);
        countries1.setId(null);
        assertThat(countries1).isNotEqualTo(countries2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountriesDTO.class);
        CountriesDTO countriesDTO1 = new CountriesDTO();
        countriesDTO1.setId(1L);
        CountriesDTO countriesDTO2 = new CountriesDTO();
        assertThat(countriesDTO1).isNotEqualTo(countriesDTO2);
        countriesDTO2.setId(countriesDTO1.getId());
        assertThat(countriesDTO1).isEqualTo(countriesDTO2);
        countriesDTO2.setId(2L);
        assertThat(countriesDTO1).isNotEqualTo(countriesDTO2);
        countriesDTO1.setId(null);
        assertThat(countriesDTO1).isNotEqualTo(countriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(countriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(countriesMapper.fromId(null)).isNull();
    }
}
