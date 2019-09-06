package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.SupplierTransactions;
import com.resource.server.repository.SupplierTransactionsRepository;
import com.resource.server.service.SupplierTransactionsService;
import com.resource.server.service.dto.SupplierTransactionsDTO;
import com.resource.server.service.mapper.SupplierTransactionsMapper;
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
 * Test class for the SupplierTransactionsResource REST controller.
 *
 * @see SupplierTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class SupplierTransactionsResourceIntTest {

    private static final String DEFAULT_SUPPLIER_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_INVOICE_NUMBER = "BBBBBBBBBB";

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
    private SupplierTransactionsRepository supplierTransactionsRepository;

    @Autowired
    private SupplierTransactionsMapper supplierTransactionsMapper;

    @Autowired
    private SupplierTransactionsService supplierTransactionsService;

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

    private MockMvc restSupplierTransactionsMockMvc;

    private SupplierTransactions supplierTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierTransactionsResource supplierTransactionsResource = new SupplierTransactionsResource(supplierTransactionsService);
        this.restSupplierTransactionsMockMvc = MockMvcBuilders.standaloneSetup(supplierTransactionsResource)
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
    public static SupplierTransactions createEntity(EntityManager em) {
        SupplierTransactions supplierTransactions = new SupplierTransactions()
            .supplierInvoiceNumber(DEFAULT_SUPPLIER_INVOICE_NUMBER)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amountExcludingTax(DEFAULT_AMOUNT_EXCLUDING_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .outstandingBalance(DEFAULT_OUTSTANDING_BALANCE)
            .finalizationDate(DEFAULT_FINALIZATION_DATE)
            .isFinalized(DEFAULT_IS_FINALIZED);
        return supplierTransactions;
    }

    @Before
    public void initTest() {
        supplierTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierTransactions() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);
        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierTransactions testSupplierTransactions = supplierTransactionsList.get(supplierTransactionsList.size() - 1);
        assertThat(testSupplierTransactions.getSupplierInvoiceNumber()).isEqualTo(DEFAULT_SUPPLIER_INVOICE_NUMBER);
        assertThat(testSupplierTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testSupplierTransactions.getAmountExcludingTax()).isEqualTo(DEFAULT_AMOUNT_EXCLUDING_TAX);
        assertThat(testSupplierTransactions.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testSupplierTransactions.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testSupplierTransactions.getOutstandingBalance()).isEqualTo(DEFAULT_OUTSTANDING_BALANCE);
        assertThat(testSupplierTransactions.getFinalizationDate()).isEqualTo(DEFAULT_FINALIZATION_DATE);
        assertThat(testSupplierTransactions.isIsFinalized()).isEqualTo(DEFAULT_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void createSupplierTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions with an existing ID
        supplierTransactions.setId(1L);
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTransactionDate(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountExcludingTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setAmountExcludingTax(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTaxAmount(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTransactionAmount(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutstandingBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setOutstandingBalance(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierInvoiceNumber").value(hasItem(DEFAULT_SUPPLIER_INVOICE_NUMBER.toString())))
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
    public void getSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get the supplierTransactions
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/{id}", supplierTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierTransactions.getId().intValue()))
            .andExpect(jsonPath("$.supplierInvoiceNumber").value(DEFAULT_SUPPLIER_INVOICE_NUMBER.toString()))
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
    public void getNonExistingSupplierTransactions() throws Exception {
        // Get the supplierTransactions
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        int databaseSizeBeforeUpdate = supplierTransactionsRepository.findAll().size();

        // Update the supplierTransactions
        SupplierTransactions updatedSupplierTransactions = supplierTransactionsRepository.findById(supplierTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierTransactions are not directly saved in db
        em.detach(updatedSupplierTransactions);
        updatedSupplierTransactions
            .supplierInvoiceNumber(UPDATED_SUPPLIER_INVOICE_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED);
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(updatedSupplierTransactions);

        restSupplierTransactionsMockMvc.perform(put("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeUpdate);
        SupplierTransactions testSupplierTransactions = supplierTransactionsList.get(supplierTransactionsList.size() - 1);
        assertThat(testSupplierTransactions.getSupplierInvoiceNumber()).isEqualTo(UPDATED_SUPPLIER_INVOICE_NUMBER);
        assertThat(testSupplierTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testSupplierTransactions.getAmountExcludingTax()).isEqualTo(UPDATED_AMOUNT_EXCLUDING_TAX);
        assertThat(testSupplierTransactions.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testSupplierTransactions.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testSupplierTransactions.getOutstandingBalance()).isEqualTo(UPDATED_OUTSTANDING_BALANCE);
        assertThat(testSupplierTransactions.getFinalizationDate()).isEqualTo(UPDATED_FINALIZATION_DATE);
        assertThat(testSupplierTransactions.isIsFinalized()).isEqualTo(UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierTransactions() throws Exception {
        int databaseSizeBeforeUpdate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierTransactionsMockMvc.perform(put("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        int databaseSizeBeforeDelete = supplierTransactionsRepository.findAll().size();

        // Delete the supplierTransactions
        restSupplierTransactionsMockMvc.perform(delete("/api/supplier-transactions/{id}", supplierTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactions.class);
        SupplierTransactions supplierTransactions1 = new SupplierTransactions();
        supplierTransactions1.setId(1L);
        SupplierTransactions supplierTransactions2 = new SupplierTransactions();
        supplierTransactions2.setId(supplierTransactions1.getId());
        assertThat(supplierTransactions1).isEqualTo(supplierTransactions2);
        supplierTransactions2.setId(2L);
        assertThat(supplierTransactions1).isNotEqualTo(supplierTransactions2);
        supplierTransactions1.setId(null);
        assertThat(supplierTransactions1).isNotEqualTo(supplierTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactionsDTO.class);
        SupplierTransactionsDTO supplierTransactionsDTO1 = new SupplierTransactionsDTO();
        supplierTransactionsDTO1.setId(1L);
        SupplierTransactionsDTO supplierTransactionsDTO2 = new SupplierTransactionsDTO();
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO2.setId(supplierTransactionsDTO1.getId());
        assertThat(supplierTransactionsDTO1).isEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO2.setId(2L);
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO1.setId(null);
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplierTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplierTransactionsMapper.fromId(null)).isNull();
    }
}
