package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Suppliers;
import com.resource.server.domain.People;
import com.resource.server.domain.SupplierCategories;
import com.resource.server.domain.DeliveryMethods;
import com.resource.server.domain.Cities;
import com.resource.server.repository.SuppliersRepository;
import com.resource.server.service.SuppliersService;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.mapper.SuppliersMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.SuppliersCriteria;
import com.resource.server.service.SuppliersQueryService;

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

    private static final String DEFAULT_WEB_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SERVICE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_RATING = 1;
    private static final Integer UPDATED_CREDIT_RATING = 2;

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

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
    private SuppliersQueryService suppliersQueryService;

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
        final SuppliersResource suppliersResource = new SuppliersResource(suppliersService, suppliersQueryService);
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
            .webServiceUrl(DEFAULT_WEB_SERVICE_URL)
            .creditRating(DEFAULT_CREDIT_RATING)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE)
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
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(DEFAULT_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(DEFAULT_CREDIT_RATING);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSuppliers.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testSuppliers.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
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
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE.toString())))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL.toString())))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL.toString())))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
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
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE.toString()))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.bankAccountBranch").value(DEFAULT_BANK_ACCOUNT_BRANCH.toString()))
            .andExpect(jsonPath("$.bankAccountCode").value(DEFAULT_BANK_ACCOUNT_CODE.toString()))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.bankInternationalCode").value(DEFAULT_BANK_INTERNATIONAL_CODE.toString()))
            .andExpect(jsonPath("$.paymentDays").value(DEFAULT_PAYMENT_DAYS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.websiteURL").value(DEFAULT_WEBSITE_URL.toString()))
            .andExpect(jsonPath("$.webServiceUrl").value(DEFAULT_WEB_SERVICE_URL.toString()))
            .andExpect(jsonPath("$.creditRating").value(DEFAULT_CREDIT_RATING))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName equals to DEFAULT_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.equals=" + DEFAULT_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.equals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName in DEFAULT_SUPPLIER_NAME or UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.in=" + DEFAULT_SUPPLIER_NAME + "," + UPDATED_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.in=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName is not null
        defaultSuppliersShouldBeFound("supplierName.specified=true");

        // Get all the suppliersList where supplierName is null
        defaultSuppliersShouldNotBeFound("supplierName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference equals to DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.equals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.equals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference in DEFAULT_SUPPLIER_REFERENCE or UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.in=" + DEFAULT_SUPPLIER_REFERENCE + "," + UPDATED_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.in=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference is not null
        defaultSuppliersShouldBeFound("supplierReference.specified=true");

        // Get all the suppliersList where supplierReference is null
        defaultSuppliersShouldNotBeFound("supplierReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.equals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.equals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName in DEFAULT_BANK_ACCOUNT_NAME or UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.in=" + DEFAULT_BANK_ACCOUNT_NAME + "," + UPDATED_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.in=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName is not null
        defaultSuppliersShouldBeFound("bankAccountName.specified=true");

        // Get all the suppliersList where bankAccountName is null
        defaultSuppliersShouldNotBeFound("bankAccountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch equals to DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.equals=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.equals=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch in DEFAULT_BANK_ACCOUNT_BRANCH or UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.in=" + DEFAULT_BANK_ACCOUNT_BRANCH + "," + UPDATED_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.in=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch is not null
        defaultSuppliersShouldBeFound("bankAccountBranch.specified=true");

        // Get all the suppliersList where bankAccountBranch is null
        defaultSuppliersShouldNotBeFound("bankAccountBranch.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode equals to DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.equals=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.equals=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode in DEFAULT_BANK_ACCOUNT_CODE or UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.in=" + DEFAULT_BANK_ACCOUNT_CODE + "," + UPDATED_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.in=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode is not null
        defaultSuppliersShouldBeFound("bankAccountCode.specified=true");

        // Get all the suppliersList where bankAccountCode is null
        defaultSuppliersShouldNotBeFound("bankAccountCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.equals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.equals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber in DEFAULT_BANK_ACCOUNT_NUMBER or UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.in=" + DEFAULT_BANK_ACCOUNT_NUMBER + "," + UPDATED_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.in=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber is not null
        defaultSuppliersShouldBeFound("bankAccountNumber.specified=true");

        // Get all the suppliersList where bankAccountNumber is null
        defaultSuppliersShouldNotBeFound("bankAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode equals to DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.equals=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.equals=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode in DEFAULT_BANK_INTERNATIONAL_CODE or UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.in=" + DEFAULT_BANK_INTERNATIONAL_CODE + "," + UPDATED_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.in=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode is not null
        defaultSuppliersShouldBeFound("bankInternationalCode.specified=true");

        // Get all the suppliersList where bankInternationalCode is null
        defaultSuppliersShouldNotBeFound("bankInternationalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.equals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.equals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays in DEFAULT_PAYMENT_DAYS or UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.in=" + DEFAULT_PAYMENT_DAYS + "," + UPDATED_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.in=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is not null
        defaultSuppliersShouldBeFound("paymentDays.specified=true");

        // Get all the suppliersList where paymentDays is null
        defaultSuppliersShouldNotBeFound("paymentDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays greater than or equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.greaterOrEqualThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays greater than or equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.greaterOrEqualThan=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays less than or equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.lessThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays less than or equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.lessThan=" + UPDATED_PAYMENT_DAYS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments is not null
        defaultSuppliersShouldBeFound("internalComments.specified=true");

        // Get all the suppliersList where internalComments is null
        defaultSuppliersShouldNotBeFound("internalComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber is not null
        defaultSuppliersShouldBeFound("phoneNumber.specified=true");

        // Get all the suppliersList where phoneNumber is null
        defaultSuppliersShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber is not null
        defaultSuppliersShouldBeFound("faxNumber.specified=true");

        // Get all the suppliersList where faxNumber is null
        defaultSuppliersShouldNotBeFound("faxNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL equals to DEFAULT_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.equals=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.equals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL in DEFAULT_WEBSITE_URL or UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.in=" + DEFAULT_WEBSITE_URL + "," + UPDATED_WEBSITE_URL);

        // Get all the suppliersList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.in=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL is not null
        defaultSuppliersShouldBeFound("websiteURL.specified=true");

        // Get all the suppliersList where websiteURL is null
        defaultSuppliersShouldNotBeFound("websiteURL.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl equals to DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.equals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.equals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl in DEFAULT_WEB_SERVICE_URL or UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.in=" + DEFAULT_WEB_SERVICE_URL + "," + UPDATED_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.in=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl is not null
        defaultSuppliersShouldBeFound("webServiceUrl.specified=true");

        // Get all the suppliersList where webServiceUrl is null
        defaultSuppliersShouldNotBeFound("webServiceUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.equals=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.equals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating in DEFAULT_CREDIT_RATING or UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.in=" + DEFAULT_CREDIT_RATING + "," + UPDATED_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.in=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is not null
        defaultSuppliersShouldBeFound("creditRating.specified=true");

        // Get all the suppliersList where creditRating is null
        defaultSuppliersShouldNotBeFound("creditRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating greater than or equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.greaterOrEqualThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating greater than or equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.greaterOrEqualThan=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating less than or equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.lessThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating less than or equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.lessThan=" + UPDATED_CREDIT_RATING);
    }


    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag is not null
        defaultSuppliersShouldBeFound("activeFlag.specified=true");

        // Get all the suppliersList where activeFlag is null
        defaultSuppliersShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom is not null
        defaultSuppliersShouldBeFound("validFrom.specified=true");

        // Get all the suppliersList where validFrom is null
        defaultSuppliersShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom greater than or equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.greaterOrEqualThan=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom greater than or equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.greaterOrEqualThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom less than or equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom less than or equals to UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllSuppliersByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo equals to DEFAULT_VALID_TO
        defaultSuppliersShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo is not null
        defaultSuppliersShouldBeFound("validTo.specified=true");

        // Get all the suppliersList where validTo is null
        defaultSuppliersShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo greater than or equals to DEFAULT_VALID_TO
        defaultSuppliersShouldBeFound("validTo.greaterOrEqualThan=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo greater than or equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.greaterOrEqualThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo less than or equals to DEFAULT_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo less than or equals to UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByPrimaryContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        People primaryContactPerson = PeopleResourceIntTest.createEntity(em);
        em.persist(primaryContactPerson);
        em.flush();
        suppliers.setPrimaryContactPerson(primaryContactPerson);
        suppliersRepository.saveAndFlush(suppliers);
        Long primaryContactPersonId = primaryContactPerson.getId();

        // Get all the suppliersList where primaryContactPerson equals to primaryContactPersonId
        defaultSuppliersShouldBeFound("primaryContactPersonId.equals=" + primaryContactPersonId);

        // Get all the suppliersList where primaryContactPerson equals to primaryContactPersonId + 1
        defaultSuppliersShouldNotBeFound("primaryContactPersonId.equals=" + (primaryContactPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByAlternateContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        People alternateContactPerson = PeopleResourceIntTest.createEntity(em);
        em.persist(alternateContactPerson);
        em.flush();
        suppliers.setAlternateContactPerson(alternateContactPerson);
        suppliersRepository.saveAndFlush(suppliers);
        Long alternateContactPersonId = alternateContactPerson.getId();

        // Get all the suppliersList where alternateContactPerson equals to alternateContactPersonId
        defaultSuppliersShouldBeFound("alternateContactPersonId.equals=" + alternateContactPersonId);

        // Get all the suppliersList where alternateContactPerson equals to alternateContactPersonId + 1
        defaultSuppliersShouldNotBeFound("alternateContactPersonId.equals=" + (alternateContactPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersBySupplierCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplierCategories supplierCategory = SupplierCategoriesResourceIntTest.createEntity(em);
        em.persist(supplierCategory);
        em.flush();
        suppliers.setSupplierCategory(supplierCategory);
        suppliersRepository.saveAndFlush(suppliers);
        Long supplierCategoryId = supplierCategory.getId();

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId
        defaultSuppliersShouldBeFound("supplierCategoryId.equals=" + supplierCategoryId);

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId + 1
        defaultSuppliersShouldNotBeFound("supplierCategoryId.equals=" + (supplierCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIntTest.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        suppliers.setDeliveryMethod(deliveryMethod);
        suppliersRepository.saveAndFlush(suppliers);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId
        defaultSuppliersShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId + 1
        defaultSuppliersShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDeliveryCityIsEqualToSomething() throws Exception {
        // Initialize the database
        Cities deliveryCity = CitiesResourceIntTest.createEntity(em);
        em.persist(deliveryCity);
        em.flush();
        suppliers.setDeliveryCity(deliveryCity);
        suppliersRepository.saveAndFlush(suppliers);
        Long deliveryCityId = deliveryCity.getId();

        // Get all the suppliersList where deliveryCity equals to deliveryCityId
        defaultSuppliersShouldBeFound("deliveryCityId.equals=" + deliveryCityId);

        // Get all the suppliersList where deliveryCity equals to deliveryCityId + 1
        defaultSuppliersShouldNotBeFound("deliveryCityId.equals=" + (deliveryCityId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByPostalCityIsEqualToSomething() throws Exception {
        // Initialize the database
        Cities postalCity = CitiesResourceIntTest.createEntity(em);
        em.persist(postalCity);
        em.flush();
        suppliers.setPostalCity(postalCity);
        suppliersRepository.saveAndFlush(suppliers);
        Long postalCityId = postalCity.getId();

        // Get all the suppliersList where postalCity equals to postalCityId
        defaultSuppliersShouldBeFound("postalCityId.equals=" + postalCityId);

        // Get all the suppliersList where postalCity equals to postalCityId + 1
        defaultSuppliersShouldNotBeFound("postalCityId.equals=" + (postalCityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSuppliersShouldBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
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
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSuppliersShouldNotBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .creditRating(UPDATED_CREDIT_RATING)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
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
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(UPDATED_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(UPDATED_CREDIT_RATING);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSuppliers.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testSuppliers.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
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
