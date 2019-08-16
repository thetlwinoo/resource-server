package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PaymentMethods;
import com.resource.server.repository.PaymentMethodsRepository;
import com.resource.server.service.PaymentMethodsService;
import com.resource.server.service.dto.PaymentMethodsDTO;
import com.resource.server.service.mapper.PaymentMethodsMapper;
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
 * Test class for the PaymentMethodsResource REST controller.
 *
 * @see PaymentMethodsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PaymentMethodsResourceIntTest {

    private static final String DEFAULT_PAYMENT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    private PaymentMethodsMapper paymentMethodsMapper;

    @Autowired
    private PaymentMethodsService paymentMethodsService;

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

    private MockMvc restPaymentMethodsMockMvc;

    private PaymentMethods paymentMethods;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentMethodsResource paymentMethodsResource = new PaymentMethodsResource(paymentMethodsService);
        this.restPaymentMethodsMockMvc = MockMvcBuilders.standaloneSetup(paymentMethodsResource)
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
    public static PaymentMethods createEntity(EntityManager em) {
        PaymentMethods paymentMethods = new PaymentMethods()
            .paymentMethodName(DEFAULT_PAYMENT_METHOD_NAME)
            .activeInd(DEFAULT_ACTIVE_IND)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return paymentMethods;
    }

    @Before
    public void initTest() {
        paymentMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentMethods() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getPaymentMethodName()).isEqualTo(DEFAULT_PAYMENT_METHOD_NAME);
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPaymentMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods with an existing ID
        paymentMethods.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaymentMethodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setPaymentMethodName(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setActiveInd(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setValidFrom(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setValidTo(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethodName").value(hasItem(DEFAULT_PAYMENT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }

    @Test
    @Transactional
    public void getPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", paymentMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethods.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethodName").value(DEFAULT_PAYMENT_METHOD_NAME))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentMethods() throws Exception {
        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Update the paymentMethods
        PaymentMethods updatedPaymentMethods = paymentMethodsRepository.findById(paymentMethods.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethods are not directly saved in db
        em.detach(updatedPaymentMethods);
        updatedPaymentMethods
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(updatedPaymentMethods);

        restPaymentMethodsMockMvc.perform(put("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getPaymentMethodName()).isEqualTo(UPDATED_PAYMENT_METHOD_NAME);
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentMethods() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodsMockMvc.perform(put("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeDelete = paymentMethodsRepository.findAll().size();

        // Delete the paymentMethods
        restPaymentMethodsMockMvc.perform(delete("/api/payment-methods/{id}", paymentMethods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethods.class);
        PaymentMethods paymentMethods1 = new PaymentMethods();
        paymentMethods1.setId(1L);
        PaymentMethods paymentMethods2 = new PaymentMethods();
        paymentMethods2.setId(paymentMethods1.getId());
        assertThat(paymentMethods1).isEqualTo(paymentMethods2);
        paymentMethods2.setId(2L);
        assertThat(paymentMethods1).isNotEqualTo(paymentMethods2);
        paymentMethods1.setId(null);
        assertThat(paymentMethods1).isNotEqualTo(paymentMethods2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethodsDTO.class);
        PaymentMethodsDTO paymentMethodsDTO1 = new PaymentMethodsDTO();
        paymentMethodsDTO1.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO2 = new PaymentMethodsDTO();
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(paymentMethodsDTO1.getId());
        assertThat(paymentMethodsDTO1).isEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(2L);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO1.setId(null);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentMethodsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentMethodsMapper.fromId(null)).isNull();
    }
}
