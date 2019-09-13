package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ColdRoomTemperatures;
import com.resource.server.repository.ColdRoomTemperaturesRepository;
import com.resource.server.service.ColdRoomTemperaturesService;
import com.resource.server.service.dto.ColdRoomTemperaturesDTO;
import com.resource.server.service.mapper.ColdRoomTemperaturesMapper;
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
 * Test class for the ColdRoomTemperaturesResource REST controller.
 *
 * @see ColdRoomTemperaturesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ColdRoomTemperaturesResourceIntTest {

    private static final Integer DEFAULT_COLD_ROOM_SENSOR_NUMBER = 1;
    private static final Integer UPDATED_COLD_ROOM_SENSOR_NUMBER = 2;

    private static final LocalDate DEFAULT_RECORDED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECORDED_WHEN = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TEMPERATURE = 1F;
    private static final Float UPDATED_TEMPERATURE = 2F;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ColdRoomTemperaturesRepository coldRoomTemperaturesRepository;

    @Autowired
    private ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    @Autowired
    private ColdRoomTemperaturesService coldRoomTemperaturesService;

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

    private MockMvc restColdRoomTemperaturesMockMvc;

    private ColdRoomTemperatures coldRoomTemperatures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColdRoomTemperaturesResource coldRoomTemperaturesResource = new ColdRoomTemperaturesResource(coldRoomTemperaturesService);
        this.restColdRoomTemperaturesMockMvc = MockMvcBuilders.standaloneSetup(coldRoomTemperaturesResource)
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
    public static ColdRoomTemperatures createEntity(EntityManager em) {
        ColdRoomTemperatures coldRoomTemperatures = new ColdRoomTemperatures()
            .coldRoomSensorNumber(DEFAULT_COLD_ROOM_SENSOR_NUMBER)
            .recordedWhen(DEFAULT_RECORDED_WHEN)
            .temperature(DEFAULT_TEMPERATURE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return coldRoomTemperatures;
    }

    @Before
    public void initTest() {
        coldRoomTemperatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createColdRoomTemperatures() throws Exception {
        int databaseSizeBeforeCreate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);
        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isCreated());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeCreate + 1);
        ColdRoomTemperatures testColdRoomTemperatures = coldRoomTemperaturesList.get(coldRoomTemperaturesList.size() - 1);
        assertThat(testColdRoomTemperatures.getColdRoomSensorNumber()).isEqualTo(DEFAULT_COLD_ROOM_SENSOR_NUMBER);
        assertThat(testColdRoomTemperatures.getRecordedWhen()).isEqualTo(DEFAULT_RECORDED_WHEN);
        assertThat(testColdRoomTemperatures.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testColdRoomTemperatures.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testColdRoomTemperatures.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createColdRoomTemperaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures with an existing ID
        coldRoomTemperatures.setId(1L);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkColdRoomSensorNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setColdRoomSensorNumber(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setRecordedWhen(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setTemperature(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setValidFrom(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setValidTo(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coldRoomTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].coldRoomSensorNumber").value(hasItem(DEFAULT_COLD_ROOM_SENSOR_NUMBER)))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/{id}", coldRoomTemperatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coldRoomTemperatures.getId().intValue()))
            .andExpect(jsonPath("$.coldRoomSensorNumber").value(DEFAULT_COLD_ROOM_SENSOR_NUMBER))
            .andExpect(jsonPath("$.recordedWhen").value(DEFAULT_RECORDED_WHEN.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingColdRoomTemperatures() throws Exception {
        // Get the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        int databaseSizeBeforeUpdate = coldRoomTemperaturesRepository.findAll().size();

        // Update the coldRoomTemperatures
        ColdRoomTemperatures updatedColdRoomTemperatures = coldRoomTemperaturesRepository.findById(coldRoomTemperatures.getId()).get();
        // Disconnect from session so that the updates on updatedColdRoomTemperatures are not directly saved in db
        em.detach(updatedColdRoomTemperatures);
        updatedColdRoomTemperatures
            .coldRoomSensorNumber(UPDATED_COLD_ROOM_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(updatedColdRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(put("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isOk());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeUpdate);
        ColdRoomTemperatures testColdRoomTemperatures = coldRoomTemperaturesList.get(coldRoomTemperaturesList.size() - 1);
        assertThat(testColdRoomTemperatures.getColdRoomSensorNumber()).isEqualTo(UPDATED_COLD_ROOM_SENSOR_NUMBER);
        assertThat(testColdRoomTemperatures.getRecordedWhen()).isEqualTo(UPDATED_RECORDED_WHEN);
        assertThat(testColdRoomTemperatures.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testColdRoomTemperatures.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testColdRoomTemperatures.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingColdRoomTemperatures() throws Exception {
        int databaseSizeBeforeUpdate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColdRoomTemperaturesMockMvc.perform(put("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        int databaseSizeBeforeDelete = coldRoomTemperaturesRepository.findAll().size();

        // Delete the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(delete("/api/cold-room-temperatures/{id}", coldRoomTemperatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColdRoomTemperatures.class);
        ColdRoomTemperatures coldRoomTemperatures1 = new ColdRoomTemperatures();
        coldRoomTemperatures1.setId(1L);
        ColdRoomTemperatures coldRoomTemperatures2 = new ColdRoomTemperatures();
        coldRoomTemperatures2.setId(coldRoomTemperatures1.getId());
        assertThat(coldRoomTemperatures1).isEqualTo(coldRoomTemperatures2);
        coldRoomTemperatures2.setId(2L);
        assertThat(coldRoomTemperatures1).isNotEqualTo(coldRoomTemperatures2);
        coldRoomTemperatures1.setId(null);
        assertThat(coldRoomTemperatures1).isNotEqualTo(coldRoomTemperatures2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColdRoomTemperaturesDTO.class);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO1 = new ColdRoomTemperaturesDTO();
        coldRoomTemperaturesDTO1.setId(1L);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO2 = new ColdRoomTemperaturesDTO();
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO2.setId(coldRoomTemperaturesDTO1.getId());
        assertThat(coldRoomTemperaturesDTO1).isEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO2.setId(2L);
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO1.setId(null);
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coldRoomTemperaturesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coldRoomTemperaturesMapper.fromId(null)).isNull();
    }
}
