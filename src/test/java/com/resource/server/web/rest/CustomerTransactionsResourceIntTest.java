package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.CustomerTransactions;
import com.resource.server.repository.CustomerTransactionsRepository;
import com.resource.server.service.CustomerTransactionsService;
import com.resource.server.service.dto.CustomerTransactionsDTO;
import com.resource.server.service.mapper.CustomerTransactionsMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerTransactionsResource REST controller.
 *
 * @see CustomerTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CustomerTransactionsResourceIntTest {

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT_EXCLUDING_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EXCLUDING_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OUTSTANDING_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OUTSTANDING_BALANCE = new BigDecimal(2);

    private static final LocalDate DEFAULT_FINALIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINALIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_FINALIZED = false;
    private static final Boolean UPDATED_IS_FINALIZED = true;

    @Autowired
    private CustomerTransactionsRepository customerTransactionsRepository;

    @Autowired
    private CustomerTransactionsMapper customerTransactionsMapper;

    @Autowired
    private CustomerTransactionsService customerTransactionsService;

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

    private MockMvc restCustomerTransactionsMockMvc;

    private CustomerTransactions customerTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTransactionsResource customerTransactionsResource = new CustomerTransactionsResource(customerTransactionsService);
        this.restCustomerTransactionsMockMvc = MockMvcBuilders.standaloneSetup(customerTransactionsResource)
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
    public static CustomerTransactions createEntity(EntityManager em) {
        CustomerTransactions customerTransactions = new CustomerTransactions()
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amountExcludingTax(DEFAULT_AMOUNT_EXCLUDING_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .outstandingBalance(DEFAULT_OUTSTANDING_BALANCE)
            .finalizationDate(DEFAULT_FINALIZATION_DATE)
            .isFinalized(DEFAULT_IS_FINALIZED);
        return customerTransactions;
    }

    @Before
    public void initTest() {
        customerTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerTransactions() throws Exception {
        int databaseSizeBeforeCreate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);
        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerTransactions testCustomerTransactions = customerTransactionsList.get(customerTransactionsList.size() - 1);
        assertThat(testCustomerTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testCustomerTransactions.getAmountExcludingTax()).isEqualTo(DEFAULT_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerTransactions.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testCustomerTransactions.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testCustomerTransactions.getOutstandingBalance()).isEqualTo(DEFAULT_OUTSTANDING_BALANCE);
        assertThat(testCustomerTransactions.getFinalizationDate()).isEqualTo(DEFAULT_FINALIZATION_DATE);
        assertThat(testCustomerTransactions.isIsFinalized()).isEqualTo(DEFAULT_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void createCustomerTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions with an existing ID
        customerTransactions.setId(1L);
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTransactionDate(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountExcludingTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setAmountExcludingTax(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTaxAmount(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTransactionAmount(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutstandingBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setOutstandingBalance(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountExcludingTax").value(hasItem(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outstandingBalance").value(hasItem(DEFAULT_OUTSTANDING_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].finalizationDate").value(hasItem(DEFAULT_FINALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isFinalized").value(hasItem(DEFAULT_IS_FINALIZED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get the customerTransactions
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/{id}", customerTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerTransactions.getId().intValue()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.amountExcludingTax").value(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.outstandingBalance").value(DEFAULT_OUTSTANDING_BALANCE.intValue()))
            .andExpect(jsonPath("$.finalizationDate").value(DEFAULT_FINALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.isFinalized").value(DEFAULT_IS_FINALIZED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerTransactions() throws Exception {
        // Get the customerTransactions
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        int databaseSizeBeforeUpdate = customerTransactionsRepository.findAll().size();

        // Update the customerTransactions
        CustomerTransactions updatedCustomerTransactions = customerTransactionsRepository.findById(customerTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerTransactions are not directly saved in db
        em.detach(updatedCustomerTransactions);
        updatedCustomerTransactions
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED);
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(updatedCustomerTransactions);

        restCustomerTransactionsMockMvc.perform(put("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeUpdate);
        CustomerTransactions testCustomerTransactions = customerTransactionsList.get(customerTransactionsList.size() - 1);
        assertThat(testCustomerTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testCustomerTransactions.getAmountExcludingTax()).isEqualTo(UPDATED_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerTransactions.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testCustomerTransactions.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testCustomerTransactions.getOutstandingBalance()).isEqualTo(UPDATED_OUTSTANDING_BALANCE);
        assertThat(testCustomerTransactions.getFinalizationDate()).isEqualTo(UPDATED_FINALIZATION_DATE);
        assertThat(testCustomerTransactions.isIsFinalized()).isEqualTo(UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerTransactions() throws Exception {
        int databaseSizeBeforeUpdate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTransactionsMockMvc.perform(put("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        int databaseSizeBeforeDelete = customerTransactionsRepository.findAll().size();

        // Delete the customerTransactions
        restCustomerTransactionsMockMvc.perform(delete("/api/customer-transactions/{id}", customerTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTransactions.class);
        CustomerTransactions customerTransactions1 = new CustomerTransactions();
        customerTransactions1.setId(1L);
        CustomerTransactions customerTransactions2 = new CustomerTransactions();
        customerTransactions2.setId(customerTransactions1.getId());
        assertThat(customerTransactions1).isEqualTo(customerTransactions2);
        customerTransactions2.setId(2L);
        assertThat(customerTransactions1).isNotEqualTo(customerTransactions2);
        customerTransactions1.setId(null);
        assertThat(customerTransactions1).isNotEqualTo(customerTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTransactionsDTO.class);
        CustomerTransactionsDTO customerTransactionsDTO1 = new CustomerTransactionsDTO();
        customerTransactionsDTO1.setId(1L);
        CustomerTransactionsDTO customerTransactionsDTO2 = new CustomerTransactionsDTO();
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO2.setId(customerTransactionsDTO1.getId());
        assertThat(customerTransactionsDTO1).isEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO2.setId(2L);
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO1.setId(null);
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerTransactionsMapper.fromId(null)).isNull();
    }
}
