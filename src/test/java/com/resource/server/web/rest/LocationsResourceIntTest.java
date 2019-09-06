package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Locations;
import com.resource.server.repository.LocationsRepository;
import com.resource.server.service.LocationsService;
import com.resource.server.service.dto.LocationsDTO;
import com.resource.server.service.mapper.LocationsMapper;
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
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LocationsResource REST controller.
 *
 * @see LocationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class LocationsResourceIntTest {

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_COST_RATE = 1F;
    private static final Float UPDATED_COST_RATE = 2F;

    private static final Float DEFAULT_AVAILABILITY = 1F;
    private static final Float UPDATED_AVAILABILITY = 2F;

    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private LocationsMapper locationsMapper;

    @Autowired
    private LocationsService locationsService;

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

    private MockMvc restLocationsMockMvc;

    private Locations locations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationsResource locationsResource = new LocationsResource(locationsService);
        this.restLocationsMockMvc = MockMvcBuilders.standaloneSetup(locationsResource)
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
    public static Locations createEntity(EntityManager em) {
        Locations locations = new Locations()
            .locationName(DEFAULT_LOCATION_NAME)
            .costRate(DEFAULT_COST_RATE)
            .availability(DEFAULT_AVAILABILITY);
        return locations;
    }

    @Before
    public void initTest() {
        locations = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocations() throws Exception {
        int databaseSizeBeforeCreate = locationsRepository.findAll().size();

        // Create the Locations
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);
        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate + 1);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testLocations.getCostRate()).isEqualTo(DEFAULT_COST_RATE);
        assertThat(testLocations.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
    }

    @Test
    @Transactional
    public void createLocationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationsRepository.findAll().size();

        // Create the Locations with an existing ID
        locations.setId(1L);
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLocationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setLocationName(null);

        // Create the Locations, which fails.
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);

        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setCostRate(null);

        // Create the Locations, which fails.
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);

        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setAvailability(null);

        // Create the Locations, which fails.
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);

        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList
        restLocationsMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locations.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].costRate").value(hasItem(DEFAULT_COST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get the locations
        restLocationsMockMvc.perform(get("/api/locations/{id}", locations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locations.getId().intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.costRate").value(DEFAULT_COST_RATE.doubleValue()))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocations() throws Exception {
        // Get the locations
        restLocationsMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Update the locations
        Locations updatedLocations = locationsRepository.findById(locations.getId()).get();
        // Disconnect from session so that the updates on updatedLocations are not directly saved in db
        em.detach(updatedLocations);
        updatedLocations
            .locationName(UPDATED_LOCATION_NAME)
            .costRate(UPDATED_COST_RATE)
            .availability(UPDATED_AVAILABILITY);
        LocationsDTO locationsDTO = locationsMapper.toDto(updatedLocations);

        restLocationsMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isOk());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testLocations.getCostRate()).isEqualTo(UPDATED_COST_RATE);
        assertThat(testLocations.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Create the Locations
        LocationsDTO locationsDTO = locationsMapper.toDto(locations);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationsMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeDelete = locationsRepository.findAll().size();

        // Delete the locations
        restLocationsMockMvc.perform(delete("/api/locations/{id}", locations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locations.class);
        Locations locations1 = new Locations();
        locations1.setId(1L);
        Locations locations2 = new Locations();
        locations2.setId(locations1.getId());
        assertThat(locations1).isEqualTo(locations2);
        locations2.setId(2L);
        assertThat(locations1).isNotEqualTo(locations2);
        locations1.setId(null);
        assertThat(locations1).isNotEqualTo(locations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationsDTO.class);
        LocationsDTO locationsDTO1 = new LocationsDTO();
        locationsDTO1.setId(1L);
        LocationsDTO locationsDTO2 = new LocationsDTO();
        assertThat(locationsDTO1).isNotEqualTo(locationsDTO2);
        locationsDTO2.setId(locationsDTO1.getId());
        assertThat(locationsDTO1).isEqualTo(locationsDTO2);
        locationsDTO2.setId(2L);
        assertThat(locationsDTO1).isNotEqualTo(locationsDTO2);
        locationsDTO1.setId(null);
        assertThat(locationsDTO1).isNotEqualTo(locationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationsMapper.fromId(null)).isNull();
    }
}
