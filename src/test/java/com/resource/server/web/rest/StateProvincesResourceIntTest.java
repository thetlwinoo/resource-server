package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StateProvinces;
import com.resource.server.repository.StateProvincesRepository;
import com.resource.server.service.StateProvincesService;
import com.resource.server.service.dto.StateProvincesDTO;
import com.resource.server.service.mapper.StateProvincesMapper;
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
 * Test class for the StateProvincesResource REST controller.
 *
 * @see StateProvincesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StateProvincesResourceIntTest {

    private static final String DEFAULT_STATE_PROVINCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_TERRITORY = "AAAAAAAAAA";
    private static final String UPDATED_SALES_TERRITORY = "BBBBBBBBBB";

    private static final String DEFAULT_BORDER = "AAAAAAAAAA";
    private static final String UPDATED_BORDER = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StateProvincesRepository stateProvincesRepository;

    @Autowired
    private StateProvincesMapper stateProvincesMapper;

    @Autowired
    private StateProvincesService stateProvincesService;

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

    private MockMvc restStateProvincesMockMvc;

    private StateProvinces stateProvinces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateProvincesResource stateProvincesResource = new StateProvincesResource(stateProvincesService);
        this.restStateProvincesMockMvc = MockMvcBuilders.standaloneSetup(stateProvincesResource)
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
    public static StateProvinces createEntity(EntityManager em) {
        StateProvinces stateProvinces = new StateProvinces()
            .stateProvinceCode(DEFAULT_STATE_PROVINCE_CODE)
            .stateProvinceName(DEFAULT_STATE_PROVINCE_NAME)
            .salesTerritory(DEFAULT_SALES_TERRITORY)
            .border(DEFAULT_BORDER)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return stateProvinces;
    }

    @Before
    public void initTest() {
        stateProvinces = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateProvinces() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);
        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isCreated());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateProvinceCode()).isEqualTo(DEFAULT_STATE_PROVINCE_CODE);
        assertThat(testStateProvinces.getStateProvinceName()).isEqualTo(DEFAULT_STATE_PROVINCE_NAME);
        assertThat(testStateProvinces.getSalesTerritory()).isEqualTo(DEFAULT_SALES_TERRITORY);
        assertThat(testStateProvinces.getBorder()).isEqualTo(DEFAULT_BORDER);
        assertThat(testStateProvinces.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testStateProvinces.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testStateProvinces.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createStateProvincesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces with an existing ID
        stateProvinces.setId(1L);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStateProvinceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setStateProvinceCode(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateProvinceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setStateProvinceName(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesTerritoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setSalesTerritory(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setValidFrom(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setValidTo(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList
        restStateProvincesMockMvc.perform(get("/api/state-provinces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvinces.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateProvinceCode").value(hasItem(DEFAULT_STATE_PROVINCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateProvinceName").value(hasItem(DEFAULT_STATE_PROVINCE_NAME.toString())))
            .andExpect(jsonPath("$.[*].salesTerritory").value(hasItem(DEFAULT_SALES_TERRITORY.toString())))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER.toString())))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get the stateProvinces
        restStateProvincesMockMvc.perform(get("/api/state-provinces/{id}", stateProvinces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvinces.getId().intValue()))
            .andExpect(jsonPath("$.stateProvinceCode").value(DEFAULT_STATE_PROVINCE_CODE.toString()))
            .andExpect(jsonPath("$.stateProvinceName").value(DEFAULT_STATE_PROVINCE_NAME.toString()))
            .andExpect(jsonPath("$.salesTerritory").value(DEFAULT_SALES_TERRITORY.toString()))
            .andExpect(jsonPath("$.border").value(DEFAULT_BORDER.toString()))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStateProvinces() throws Exception {
        // Get the stateProvinces
        restStateProvincesMockMvc.perform(get("/api/state-provinces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Update the stateProvinces
        StateProvinces updatedStateProvinces = stateProvincesRepository.findById(stateProvinces.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvinces are not directly saved in db
        em.detach(updatedStateProvinces);
        updatedStateProvinces
            .stateProvinceCode(UPDATED_STATE_PROVINCE_CODE)
            .stateProvinceName(UPDATED_STATE_PROVINCE_NAME)
            .salesTerritory(UPDATED_SALES_TERRITORY)
            .border(UPDATED_BORDER)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(updatedStateProvinces);

        restStateProvincesMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isOk());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateProvinceCode()).isEqualTo(UPDATED_STATE_PROVINCE_CODE);
        assertThat(testStateProvinces.getStateProvinceName()).isEqualTo(UPDATED_STATE_PROVINCE_NAME);
        assertThat(testStateProvinces.getSalesTerritory()).isEqualTo(UPDATED_SALES_TERRITORY);
        assertThat(testStateProvinces.getBorder()).isEqualTo(UPDATED_BORDER);
        assertThat(testStateProvinces.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testStateProvinces.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testStateProvinces.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeDelete = stateProvincesRepository.findAll().size();

        // Delete the stateProvinces
        restStateProvincesMockMvc.perform(delete("/api/state-provinces/{id}", stateProvinces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvinces.class);
        StateProvinces stateProvinces1 = new StateProvinces();
        stateProvinces1.setId(1L);
        StateProvinces stateProvinces2 = new StateProvinces();
        stateProvinces2.setId(stateProvinces1.getId());
        assertThat(stateProvinces1).isEqualTo(stateProvinces2);
        stateProvinces2.setId(2L);
        assertThat(stateProvinces1).isNotEqualTo(stateProvinces2);
        stateProvinces1.setId(null);
        assertThat(stateProvinces1).isNotEqualTo(stateProvinces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesDTO.class);
        StateProvincesDTO stateProvincesDTO1 = new StateProvincesDTO();
        stateProvincesDTO1.setId(1L);
        StateProvincesDTO stateProvincesDTO2 = new StateProvincesDTO();
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
        stateProvincesDTO2.setId(stateProvincesDTO1.getId());
        assertThat(stateProvincesDTO1).isEqualTo(stateProvincesDTO2);
        stateProvincesDTO2.setId(2L);
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
        stateProvincesDTO1.setId(null);
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stateProvincesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stateProvincesMapper.fromId(null)).isNull();
    }
}
