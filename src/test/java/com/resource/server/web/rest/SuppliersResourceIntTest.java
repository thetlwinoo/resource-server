package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Suppliers;
import com.resource.server.repository.SuppliersRepository;
import com.resource.server.service.SuppliersService;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.mapper.SuppliersMapper;
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
 * Test class for the SuppliersResource REST controller.
 *
 * @see SuppliersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class SuppliersResourceIntTest {

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_INTERNATIONAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_INTERNATIONAL_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DAYS = 1;
    private static final Integer UPDATED_PAYMENT_DAYS = 2;

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private SuppliersService suppliersService;

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

    private MockMvc restSuppliersMockMvc;

    private Suppliers suppliers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuppliersResource suppliersResource = new SuppliersResource(suppliersService);
        this.restSuppliersMockMvc = MockMvcBuilders.standaloneSetup(suppliersResource)
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
    public static Suppliers createEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .supplierReference(DEFAULT_SUPPLIER_REFERENCE)
            .bankAccountName(DEFAULT_BANK_ACCOUNT_NAME)
            .bankAccountBranch(DEFAULT_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(DEFAULT_BANK_ACCOUNT_CODE)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(DEFAULT_BANK_INTERNATIONAL_CODE)
            .paymentDays(DEFAULT_PAYMENT_DAYS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .websiteURL(DEFAULT_WEBSITE_URL)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return suppliers;
    }

    @Before
    public void initTest() {
        suppliers = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuppliers() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);
        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isCreated());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate + 1);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(DEFAULT_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(DEFAULT_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(DEFAULT_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(DEFAULT_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(DEFAULT_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(DEFAULT_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteURL()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSuppliersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // Create the Suppliers with an existing ID
        suppliers.setId(1L);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSupplierNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setSupplierName(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setPaymentDays(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setPhoneNumber(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setValidFrom(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setValidTo(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH)))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }

    @Test
    @Transactional
    public void getSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", suppliers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suppliers.getId().intValue()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME))
            .andExpect(jsonPath("$.bankAccountBranch").value(DEFAULT_BANK_ACCOUNT_BRANCH))
            .andExpect(jsonPath("$.bankAccountCode").value(DEFAULT_BANK_ACCOUNT_CODE))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankInternationalCode").value(DEFAULT_BANK_INTERNATIONAL_CODE))
            .andExpect(jsonPath("$.paymentDays").value(DEFAULT_PAYMENT_DAYS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.websiteURL").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuppliers() throws Exception {
        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers
        Suppliers updatedSuppliers = suppliersRepository.findById(suppliers.getId()).get();
        // Disconnect from session so that the updates on updatedSuppliers are not directly saved in db
        em.detach(updatedSuppliers);
        updatedSuppliers
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountBranch(UPDATED_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(UPDATED_BANK_ACCOUNT_CODE)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(UPDATED_BANK_INTERNATIONAL_CODE)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .websiteURL(UPDATED_WEBSITE_URL)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(updatedSuppliers);

        restSuppliersMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(UPDATED_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(UPDATED_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(UPDATED_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(UPDATED_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(UPDATED_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(UPDATED_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteURL()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuppliersMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeDelete = suppliersRepository.findAll().size();

        // Delete the suppliers
        restSuppliersMockMvc.perform(delete("/api/suppliers/{id}", suppliers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suppliers.class);
        Suppliers suppliers1 = new Suppliers();
        suppliers1.setId(1L);
        Suppliers suppliers2 = new Suppliers();
        suppliers2.setId(suppliers1.getId());
        assertThat(suppliers1).isEqualTo(suppliers2);
        suppliers2.setId(2L);
        assertThat(suppliers1).isNotEqualTo(suppliers2);
        suppliers1.setId(null);
        assertThat(suppliers1).isNotEqualTo(suppliers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuppliersDTO.class);
        SuppliersDTO suppliersDTO1 = new SuppliersDTO();
        suppliersDTO1.setId(1L);
        SuppliersDTO suppliersDTO2 = new SuppliersDTO();
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO2.setId(suppliersDTO1.getId());
        assertThat(suppliersDTO1).isEqualTo(suppliersDTO2);
        suppliersDTO2.setId(2L);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO1.setId(null);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(suppliersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(suppliersMapper.fromId(null)).isNull();
    }
}
