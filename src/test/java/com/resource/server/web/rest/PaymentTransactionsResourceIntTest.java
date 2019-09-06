package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PaymentTransactions;
import com.resource.server.repository.PaymentTransactionsRepository;
import com.resource.server.service.PaymentTransactionsService;
import com.resource.server.service.dto.PaymentTransactionsDTO;
import com.resource.server.service.mapper.PaymentTransactionsMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentTransactionsResource REST controller.
 *
 * @see PaymentTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PaymentTransactionsResourceIntTest {

    private static final String DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RETURNED_COMPLETED_PAYMENT_DATA = "BBBBBBBBBB";

    @Autowired
    private PaymentTransactionsRepository paymentTransactionsRepository;

    @Autowired
    private PaymentTransactionsMapper paymentTransactionsMapper;

    @Autowired
    private PaymentTransactionsService paymentTransactionsService;

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

    private MockMvc restPaymentTransactionsMockMvc;

    private PaymentTransactions paymentTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentTransactionsResource paymentTransactionsResource = new PaymentTransactionsResource(paymentTransactionsService);
        this.restPaymentTransactionsMockMvc = MockMvcBuilders.standaloneSetup(paymentTransactionsResource)
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
    public static PaymentTransactions createEntity(EntityManager em) {
        PaymentTransactions paymentTransactions = new PaymentTransactions()
            .returnedCompletedPaymentData(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA);
        return paymentTransactions;
    }

    @Before
    public void initTest() {
        paymentTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTransactions() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);
        restPaymentTransactionsMockMvc.perform(post("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTransactions testPaymentTransactions = paymentTransactionsList.get(paymentTransactionsList.size() - 1);
        assertThat(testPaymentTransactions.getReturnedCompletedPaymentData()).isEqualTo(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA);
    }

    @Test
    @Transactional
    public void createPaymentTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions with an existing ID
        paymentTransactions.setId(1L);
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTransactionsMockMvc.perform(post("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].returnedCompletedPaymentData").value(hasItem(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get the paymentTransactions
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/{id}", paymentTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTransactions.getId().intValue()))
            .andExpect(jsonPath("$.returnedCompletedPaymentData").value(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentTransactions() throws Exception {
        // Get the paymentTransactions
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        int databaseSizeBeforeUpdate = paymentTransactionsRepository.findAll().size();

        // Update the paymentTransactions
        PaymentTransactions updatedPaymentTransactions = paymentTransactionsRepository.findById(paymentTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentTransactions are not directly saved in db
        em.detach(updatedPaymentTransactions);
        updatedPaymentTransactions
            .returnedCompletedPaymentData(UPDATED_RETURNED_COMPLETED_PAYMENT_DATA);
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(updatedPaymentTransactions);

        restPaymentTransactionsMockMvc.perform(put("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransactions testPaymentTransactions = paymentTransactionsList.get(paymentTransactionsList.size() - 1);
        assertThat(testPaymentTransactions.getReturnedCompletedPaymentData()).isEqualTo(UPDATED_RETURNED_COMPLETED_PAYMENT_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTransactions() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTransactionsMockMvc.perform(put("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        int databaseSizeBeforeDelete = paymentTransactionsRepository.findAll().size();

        // Delete the paymentTransactions
        restPaymentTransactionsMockMvc.perform(delete("/api/payment-transactions/{id}", paymentTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransactions.class);
        PaymentTransactions paymentTransactions1 = new PaymentTransactions();
        paymentTransactions1.setId(1L);
        PaymentTransactions paymentTransactions2 = new PaymentTransactions();
        paymentTransactions2.setId(paymentTransactions1.getId());
        assertThat(paymentTransactions1).isEqualTo(paymentTransactions2);
        paymentTransactions2.setId(2L);
        assertThat(paymentTransactions1).isNotEqualTo(paymentTransactions2);
        paymentTransactions1.setId(null);
        assertThat(paymentTransactions1).isNotEqualTo(paymentTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransactionsDTO.class);
        PaymentTransactionsDTO paymentTransactionsDTO1 = new PaymentTransactionsDTO();
        paymentTransactionsDTO1.setId(1L);
        PaymentTransactionsDTO paymentTransactionsDTO2 = new PaymentTransactionsDTO();
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO2.setId(paymentTransactionsDTO1.getId());
        assertThat(paymentTransactionsDTO1).isEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO2.setId(2L);
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO1.setId(null);
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentTransactionsMapper.fromId(null)).isNull();
    }
}
