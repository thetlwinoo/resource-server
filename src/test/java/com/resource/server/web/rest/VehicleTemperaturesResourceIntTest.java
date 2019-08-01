package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.VehicleTemperatures;
import com.resource.server.repository.VehicleTemperaturesRepository;
import com.resource.server.service.VehicleTemperaturesService;
import com.resource.server.service.dto.VehicleTemperaturesDTO;
import com.resource.server.service.mapper.VehicleTemperaturesMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VehicleTemperaturesResource REST controller.
 *
 * @see VehicleTemperaturesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class VehicleTemperaturesResourceIntTest {

    private static final Integer DEFAULT_VEHICLE_REGISTRATION = 1;
    private static final Integer UPDATED_VEHICLE_REGISTRATION = 2;

    private static final String DEFAULT_CHILLER_SENSOR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHILLER_SENSOR_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECORDED_WHEN = 1;
    private static final Integer UPDATED_RECORDED_WHEN = 2;

    private static final BigDecimal DEFAULT_TEMPERATURE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TEMPERATURE = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_COMPRESSED = false;
    private static final Boolean UPDATED_IS_COMPRESSED = true;

    private static final String DEFAULT_FULL_SENSOR_DATA = "AAAAAAAAAA";
    private static final String UPDATED_FULL_SENSOR_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_COMPRESSED_SENSOR_DATA = "AAAAAAAAAA";
    private static final String UPDATED_COMPRESSED_SENSOR_DATA = "BBBBBBBBBB";

    @Autowired
    private VehicleTemperaturesRepository vehicleTemperaturesRepository;

    @Autowired
    private VehicleTemperaturesMapper vehicleTemperaturesMapper;

    @Autowired
    private VehicleTemperaturesService vehicleTemperaturesService;

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

    private MockMvc restVehicleTemperaturesMockMvc;

    private VehicleTemperatures vehicleTemperatures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleTemperaturesResource vehicleTemperaturesResource = new VehicleTemperaturesResource(vehicleTemperaturesService);
        this.restVehicleTemperaturesMockMvc = MockMvcBuilders.standaloneSetup(vehicleTemperaturesResource)
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
    public static VehicleTemperatures createEntity(EntityManager em) {
        VehicleTemperatures vehicleTemperatures = new VehicleTemperatures()
            .vehicleRegistration(DEFAULT_VEHICLE_REGISTRATION)
            .chillerSensorNumber(DEFAULT_CHILLER_SENSOR_NUMBER)
            .recordedWhen(DEFAULT_RECORDED_WHEN)
            .temperature(DEFAULT_TEMPERATURE)
            .isCompressed(DEFAULT_IS_COMPRESSED)
            .fullSensorData(DEFAULT_FULL_SENSOR_DATA)
            .compressedSensorData(DEFAULT_COMPRESSED_SENSOR_DATA);
        return vehicleTemperatures;
    }

    @Before
    public void initTest() {
        vehicleTemperatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleTemperatures() throws Exception {
        int databaseSizeBeforeCreate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);
        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleTemperatures testVehicleTemperatures = vehicleTemperaturesList.get(vehicleTemperaturesList.size() - 1);
        assertThat(testVehicleTemperatures.getVehicleRegistration()).isEqualTo(DEFAULT_VEHICLE_REGISTRATION);
        assertThat(testVehicleTemperatures.getChillerSensorNumber()).isEqualTo(DEFAULT_CHILLER_SENSOR_NUMBER);
        assertThat(testVehicleTemperatures.getRecordedWhen()).isEqualTo(DEFAULT_RECORDED_WHEN);
        assertThat(testVehicleTemperatures.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testVehicleTemperatures.isIsCompressed()).isEqualTo(DEFAULT_IS_COMPRESSED);
        assertThat(testVehicleTemperatures.getFullSensorData()).isEqualTo(DEFAULT_FULL_SENSOR_DATA);
        assertThat(testVehicleTemperatures.getCompressedSensorData()).isEqualTo(DEFAULT_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void createVehicleTemperaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures with an existing ID
        vehicleTemperatures.setId(1L);
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVehicleRegistrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setVehicleRegistration(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChillerSensorNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setChillerSensorNumber(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setRecordedWhen(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setTemperature(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCompressedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setIsCompressed(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleRegistration").value(hasItem(DEFAULT_VEHICLE_REGISTRATION)))
            .andExpect(jsonPath("$.[*].chillerSensorNumber").value(hasItem(DEFAULT_CHILLER_SENSOR_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
            .andExpect(jsonPath("$.[*].isCompressed").value(hasItem(DEFAULT_IS_COMPRESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].fullSensorData").value(hasItem(DEFAULT_FULL_SENSOR_DATA.toString())))
            .andExpect(jsonPath("$.[*].compressedSensorData").value(hasItem(DEFAULT_COMPRESSED_SENSOR_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/{id}", vehicleTemperatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleTemperatures.getId().intValue()))
            .andExpect(jsonPath("$.vehicleRegistration").value(DEFAULT_VEHICLE_REGISTRATION))
            .andExpect(jsonPath("$.chillerSensorNumber").value(DEFAULT_CHILLER_SENSOR_NUMBER.toString()))
            .andExpect(jsonPath("$.recordedWhen").value(DEFAULT_RECORDED_WHEN))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.intValue()))
            .andExpect(jsonPath("$.isCompressed").value(DEFAULT_IS_COMPRESSED.booleanValue()))
            .andExpect(jsonPath("$.fullSensorData").value(DEFAULT_FULL_SENSOR_DATA.toString()))
            .andExpect(jsonPath("$.compressedSensorData").value(DEFAULT_COMPRESSED_SENSOR_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleTemperatures() throws Exception {
        // Get the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        int databaseSizeBeforeUpdate = vehicleTemperaturesRepository.findAll().size();

        // Update the vehicleTemperatures
        VehicleTemperatures updatedVehicleTemperatures = vehicleTemperaturesRepository.findById(vehicleTemperatures.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleTemperatures are not directly saved in db
        em.detach(updatedVehicleTemperatures);
        updatedVehicleTemperatures
            .vehicleRegistration(UPDATED_VEHICLE_REGISTRATION)
            .chillerSensorNumber(UPDATED_CHILLER_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .isCompressed(UPDATED_IS_COMPRESSED)
            .fullSensorData(UPDATED_FULL_SENSOR_DATA)
            .compressedSensorData(UPDATED_COMPRESSED_SENSOR_DATA);
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(updatedVehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(put("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeUpdate);
        VehicleTemperatures testVehicleTemperatures = vehicleTemperaturesList.get(vehicleTemperaturesList.size() - 1);
        assertThat(testVehicleTemperatures.getVehicleRegistration()).isEqualTo(UPDATED_VEHICLE_REGISTRATION);
        assertThat(testVehicleTemperatures.getChillerSensorNumber()).isEqualTo(UPDATED_CHILLER_SENSOR_NUMBER);
        assertThat(testVehicleTemperatures.getRecordedWhen()).isEqualTo(UPDATED_RECORDED_WHEN);
        assertThat(testVehicleTemperatures.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testVehicleTemperatures.isIsCompressed()).isEqualTo(UPDATED_IS_COMPRESSED);
        assertThat(testVehicleTemperatures.getFullSensorData()).isEqualTo(UPDATED_FULL_SENSOR_DATA);
        assertThat(testVehicleTemperatures.getCompressedSensorData()).isEqualTo(UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleTemperatures() throws Exception {
        int databaseSizeBeforeUpdate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleTemperaturesMockMvc.perform(put("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        int databaseSizeBeforeDelete = vehicleTemperaturesRepository.findAll().size();

        // Delete the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(delete("/api/vehicle-temperatures/{id}", vehicleTemperatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTemperatures.class);
        VehicleTemperatures vehicleTemperatures1 = new VehicleTemperatures();
        vehicleTemperatures1.setId(1L);
        VehicleTemperatures vehicleTemperatures2 = new VehicleTemperatures();
        vehicleTemperatures2.setId(vehicleTemperatures1.getId());
        assertThat(vehicleTemperatures1).isEqualTo(vehicleTemperatures2);
        vehicleTemperatures2.setId(2L);
        assertThat(vehicleTemperatures1).isNotEqualTo(vehicleTemperatures2);
        vehicleTemperatures1.setId(null);
        assertThat(vehicleTemperatures1).isNotEqualTo(vehicleTemperatures2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTemperaturesDTO.class);
        VehicleTemperaturesDTO vehicleTemperaturesDTO1 = new VehicleTemperaturesDTO();
        vehicleTemperaturesDTO1.setId(1L);
        VehicleTemperaturesDTO vehicleTemperaturesDTO2 = new VehicleTemperaturesDTO();
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO2.setId(vehicleTemperaturesDTO1.getId());
        assertThat(vehicleTemperaturesDTO1).isEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO2.setId(2L);
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO1.setId(null);
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleTemperaturesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleTemperaturesMapper.fromId(null)).isNull();
    }
}
