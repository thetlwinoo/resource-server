package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.DeliveryMethods;
import com.resource.server.repository.DeliveryMethodsRepository;
import com.resource.server.service.DeliveryMethodsService;
import com.resource.server.service.dto.DeliveryMethodsDTO;
import com.resource.server.service.mapper.DeliveryMethodsMapper;
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
 * Test class for the DeliveryMethodsResource REST controller.
 *
 * @see DeliveryMethodsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class DeliveryMethodsResourceIntTest {

    private static final String DEFAULT_DELIVERY_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_METHOD_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeliveryMethodsRepository deliveryMethodsRepository;

    @Autowired
    private DeliveryMethodsMapper deliveryMethodsMapper;

    @Autowired
    private DeliveryMethodsService deliveryMethodsService;

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

    private MockMvc restDeliveryMethodsMockMvc;

    private DeliveryMethods deliveryMethods;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryMethodsResource deliveryMethodsResource = new DeliveryMethodsResource(deliveryMethodsService);
        this.restDeliveryMethodsMockMvc = MockMvcBuilders.standaloneSetup(deliveryMethodsResource)
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
    public static DeliveryMethods createEntity(EntityManager em) {
        DeliveryMethods deliveryMethods = new DeliveryMethods()
            .deliveryMethodName(DEFAULT_DELIVERY_METHOD_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return deliveryMethods;
    }

    @Before
    public void initTest() {
        deliveryMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryMethods() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getDeliveryMethodName()).isEqualTo(DEFAULT_DELIVERY_METHOD_NAME);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDeliveryMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods with an existing ID
        deliveryMethods.setId(1L);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeliveryMethodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setDeliveryMethodName(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidFrom(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidTo(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryMethodName").value(hasItem(DEFAULT_DELIVERY_METHOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", deliveryMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryMethods.getId().intValue()))
            .andExpect(jsonPath("$.deliveryMethodName").value(DEFAULT_DELIVERY_METHOD_NAME.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryMethods() throws Exception {
        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Update the deliveryMethods
        DeliveryMethods updatedDeliveryMethods = deliveryMethodsRepository.findById(deliveryMethods.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryMethods are not directly saved in db
        em.detach(updatedDeliveryMethods);
        updatedDeliveryMethods
            .deliveryMethodName(UPDATED_DELIVERY_METHOD_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(updatedDeliveryMethods);

        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getDeliveryMethodName()).isEqualTo(UPDATED_DELIVERY_METHOD_NAME);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryMethods() throws Exception {
        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeDelete = deliveryMethodsRepository.findAll().size();

        // Delete the deliveryMethods
        restDeliveryMethodsMockMvc.perform(delete("/api/delivery-methods/{id}", deliveryMethods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryMethods.class);
        DeliveryMethods deliveryMethods1 = new DeliveryMethods();
        deliveryMethods1.setId(1L);
        DeliveryMethods deliveryMethods2 = new DeliveryMethods();
        deliveryMethods2.setId(deliveryMethods1.getId());
        assertThat(deliveryMethods1).isEqualTo(deliveryMethods2);
        deliveryMethods2.setId(2L);
        assertThat(deliveryMethods1).isNotEqualTo(deliveryMethods2);
        deliveryMethods1.setId(null);
        assertThat(deliveryMethods1).isNotEqualTo(deliveryMethods2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryMethodsDTO.class);
        DeliveryMethodsDTO deliveryMethodsDTO1 = new DeliveryMethodsDTO();
        deliveryMethodsDTO1.setId(1L);
        DeliveryMethodsDTO deliveryMethodsDTO2 = new DeliveryMethodsDTO();
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO2.setId(deliveryMethodsDTO1.getId());
        assertThat(deliveryMethodsDTO1).isEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO2.setId(2L);
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO1.setId(null);
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryMethodsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryMethodsMapper.fromId(null)).isNull();
    }
}
