package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Merchants;
import com.resource.server.domain.People;
import com.resource.server.domain.Products;
import com.resource.server.repository.MerchantsRepository;
import com.resource.server.service.MerchantsService;
import com.resource.server.service.dto.MerchantsDTO;
import com.resource.server.service.mapper.MerchantsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.MerchantsCriteria;
import com.resource.server.service.MerchantsQueryService;

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
 * Test class for the MerchantsResource REST controller.
 *
 * @see MerchantsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class MerchantsResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_RATING = 1;
    private static final Integer UPDATED_CREDIT_RATING = 2;

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String DEFAULT_WEB_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SERVICE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    @Autowired
    private MerchantsRepository merchantsRepository;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private MerchantsQueryService merchantsQueryService;

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

    private MockMvc restMerchantsMockMvc;

    private Merchants merchants;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantsResource merchantsResource = new MerchantsResource(merchantsService, merchantsQueryService);
        this.restMerchantsMockMvc = MockMvcBuilders.standaloneSetup(merchantsResource)
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
    public static Merchants createEntity(EntityManager em) {
        Merchants merchants = new Merchants()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .merchantName(DEFAULT_MERCHANT_NAME)
            .creditRating(DEFAULT_CREDIT_RATING)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .webServiceUrl(DEFAULT_WEB_SERVICE_URL)
            .webSiteUrl(DEFAULT_WEB_SITE_URL)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE);
        return merchants;
    }

    @Before
    public void initTest() {
        merchants = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchants() throws Exception {
        int databaseSizeBeforeCreate = merchantsRepository.findAll().size();

        // Create the Merchants
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);
        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isCreated());

        // Validate the Merchants in the database
        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeCreate + 1);
        Merchants testMerchants = merchantsList.get(merchantsList.size() - 1);
        assertThat(testMerchants.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testMerchants.getMerchantName()).isEqualTo(DEFAULT_MERCHANT_NAME);
        assertThat(testMerchants.getCreditRating()).isEqualTo(DEFAULT_CREDIT_RATING);
        assertThat(testMerchants.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testMerchants.getWebServiceUrl()).isEqualTo(DEFAULT_WEB_SERVICE_URL);
        assertThat(testMerchants.getWebSiteUrl()).isEqualTo(DEFAULT_WEB_SITE_URL);
        assertThat(testMerchants.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testMerchants.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMerchantsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantsRepository.findAll().size();

        // Create the Merchants with an existing ID
        merchants.setId(1L);
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Merchants in the database
        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantsRepository.findAll().size();
        // set the field null
        merchants.setAccountNumber(null);

        // Create the Merchants, which fails.
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMerchantNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantsRepository.findAll().size();
        // set the field null
        merchants.setMerchantName(null);

        // Create the Merchants, which fails.
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantsRepository.findAll().size();
        // set the field null
        merchants.setCreditRating(null);

        // Create the Merchants, which fails.
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantsRepository.findAll().size();
        // set the field null
        merchants.setActiveFlag(null);

        // Create the Merchants, which fails.
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        restMerchantsMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchants() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList
        restMerchantsMockMvc.perform(get("/api/merchants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchants.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].merchantName").value(hasItem(DEFAULT_MERCHANT_NAME.toString())))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL.toString())))
            .andExpect(jsonPath("$.[*].webSiteUrl").value(hasItem(DEFAULT_WEB_SITE_URL.toString())))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))));
    }
    
    @Test
    @Transactional
    public void getMerchants() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get the merchants
        restMerchantsMockMvc.perform(get("/api/merchants/{id}", merchants.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchants.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.merchantName").value(DEFAULT_MERCHANT_NAME.toString()))
            .andExpect(jsonPath("$.creditRating").value(DEFAULT_CREDIT_RATING))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.webServiceUrl").value(DEFAULT_WEB_SERVICE_URL.toString()))
            .andExpect(jsonPath("$.webSiteUrl").value(DEFAULT_WEB_SITE_URL.toString()))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)));
    }

    @Test
    @Transactional
    public void getAllMerchantsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultMerchantsShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the merchantsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMerchantsShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMerchantsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultMerchantsShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the merchantsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMerchantsShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMerchantsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where accountNumber is not null
        defaultMerchantsShouldBeFound("accountNumber.specified=true");

        // Get all the merchantsList where accountNumber is null
        defaultMerchantsShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByMerchantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where merchantName equals to DEFAULT_MERCHANT_NAME
        defaultMerchantsShouldBeFound("merchantName.equals=" + DEFAULT_MERCHANT_NAME);

        // Get all the merchantsList where merchantName equals to UPDATED_MERCHANT_NAME
        defaultMerchantsShouldNotBeFound("merchantName.equals=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMerchantsByMerchantNameIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where merchantName in DEFAULT_MERCHANT_NAME or UPDATED_MERCHANT_NAME
        defaultMerchantsShouldBeFound("merchantName.in=" + DEFAULT_MERCHANT_NAME + "," + UPDATED_MERCHANT_NAME);

        // Get all the merchantsList where merchantName equals to UPDATED_MERCHANT_NAME
        defaultMerchantsShouldNotBeFound("merchantName.in=" + UPDATED_MERCHANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMerchantsByMerchantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where merchantName is not null
        defaultMerchantsShouldBeFound("merchantName.specified=true");

        // Get all the merchantsList where merchantName is null
        defaultMerchantsShouldNotBeFound("merchantName.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByCreditRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where creditRating equals to DEFAULT_CREDIT_RATING
        defaultMerchantsShouldBeFound("creditRating.equals=" + DEFAULT_CREDIT_RATING);

        // Get all the merchantsList where creditRating equals to UPDATED_CREDIT_RATING
        defaultMerchantsShouldNotBeFound("creditRating.equals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCreditRatingIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where creditRating in DEFAULT_CREDIT_RATING or UPDATED_CREDIT_RATING
        defaultMerchantsShouldBeFound("creditRating.in=" + DEFAULT_CREDIT_RATING + "," + UPDATED_CREDIT_RATING);

        // Get all the merchantsList where creditRating equals to UPDATED_CREDIT_RATING
        defaultMerchantsShouldNotBeFound("creditRating.in=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCreditRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where creditRating is not null
        defaultMerchantsShouldBeFound("creditRating.specified=true");

        // Get all the merchantsList where creditRating is null
        defaultMerchantsShouldNotBeFound("creditRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByCreditRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where creditRating greater than or equals to DEFAULT_CREDIT_RATING
        defaultMerchantsShouldBeFound("creditRating.greaterOrEqualThan=" + DEFAULT_CREDIT_RATING);

        // Get all the merchantsList where creditRating greater than or equals to UPDATED_CREDIT_RATING
        defaultMerchantsShouldNotBeFound("creditRating.greaterOrEqualThan=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCreditRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where creditRating less than or equals to DEFAULT_CREDIT_RATING
        defaultMerchantsShouldNotBeFound("creditRating.lessThan=" + DEFAULT_CREDIT_RATING);

        // Get all the merchantsList where creditRating less than or equals to UPDATED_CREDIT_RATING
        defaultMerchantsShouldBeFound("creditRating.lessThan=" + UPDATED_CREDIT_RATING);
    }


    @Test
    @Transactional
    public void getAllMerchantsByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultMerchantsShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the merchantsList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultMerchantsShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllMerchantsByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultMerchantsShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the merchantsList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultMerchantsShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllMerchantsByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where activeFlag is not null
        defaultMerchantsShouldBeFound("activeFlag.specified=true");

        // Get all the merchantsList where activeFlag is null
        defaultMerchantsShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebServiceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webServiceUrl equals to DEFAULT_WEB_SERVICE_URL
        defaultMerchantsShouldBeFound("webServiceUrl.equals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the merchantsList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultMerchantsShouldNotBeFound("webServiceUrl.equals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebServiceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webServiceUrl in DEFAULT_WEB_SERVICE_URL or UPDATED_WEB_SERVICE_URL
        defaultMerchantsShouldBeFound("webServiceUrl.in=" + DEFAULT_WEB_SERVICE_URL + "," + UPDATED_WEB_SERVICE_URL);

        // Get all the merchantsList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultMerchantsShouldNotBeFound("webServiceUrl.in=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebServiceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webServiceUrl is not null
        defaultMerchantsShouldBeFound("webServiceUrl.specified=true");

        // Get all the merchantsList where webServiceUrl is null
        defaultMerchantsShouldNotBeFound("webServiceUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebSiteUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webSiteUrl equals to DEFAULT_WEB_SITE_URL
        defaultMerchantsShouldBeFound("webSiteUrl.equals=" + DEFAULT_WEB_SITE_URL);

        // Get all the merchantsList where webSiteUrl equals to UPDATED_WEB_SITE_URL
        defaultMerchantsShouldNotBeFound("webSiteUrl.equals=" + UPDATED_WEB_SITE_URL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebSiteUrlIsInShouldWork() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webSiteUrl in DEFAULT_WEB_SITE_URL or UPDATED_WEB_SITE_URL
        defaultMerchantsShouldBeFound("webSiteUrl.in=" + DEFAULT_WEB_SITE_URL + "," + UPDATED_WEB_SITE_URL);

        // Get all the merchantsList where webSiteUrl equals to UPDATED_WEB_SITE_URL
        defaultMerchantsShouldNotBeFound("webSiteUrl.in=" + UPDATED_WEB_SITE_URL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebSiteUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        // Get all the merchantsList where webSiteUrl is not null
        defaultMerchantsShouldBeFound("webSiteUrl.specified=true");

        // Get all the merchantsList where webSiteUrl is null
        defaultMerchantsShouldNotBeFound("webSiteUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        People person = PeopleResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        merchants.setPerson(person);
        merchantsRepository.saveAndFlush(merchants);
        Long personId = person.getId();

        // Get all the merchantsList where person equals to personId
        defaultMerchantsShouldBeFound("personId.equals=" + personId);

        // Get all the merchantsList where person equals to personId + 1
        defaultMerchantsShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllMerchantsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Products product = ProductsResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        merchants.setProduct(product);
        product.setMerchant(merchants);
        merchantsRepository.saveAndFlush(merchants);
        Long productId = product.getId();

        // Get all the merchantsList where product equals to productId
        defaultMerchantsShouldBeFound("productId.equals=" + productId);

        // Get all the merchantsList where product equals to productId + 1
        defaultMerchantsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMerchantsShouldBeFound(String filter) throws Exception {
        restMerchantsMockMvc.perform(get("/api/merchants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchants.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].merchantName").value(hasItem(DEFAULT_MERCHANT_NAME)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].webSiteUrl").value(hasItem(DEFAULT_WEB_SITE_URL)))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))));

        // Check, that the count call also returns 1
        restMerchantsMockMvc.perform(get("/api/merchants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMerchantsShouldNotBeFound(String filter) throws Exception {
        restMerchantsMockMvc.perform(get("/api/merchants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMerchantsMockMvc.perform(get("/api/merchants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMerchants() throws Exception {
        // Get the merchants
        restMerchantsMockMvc.perform(get("/api/merchants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchants() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        int databaseSizeBeforeUpdate = merchantsRepository.findAll().size();

        // Update the merchants
        Merchants updatedMerchants = merchantsRepository.findById(merchants.getId()).get();
        // Disconnect from session so that the updates on updatedMerchants are not directly saved in db
        em.detach(updatedMerchants);
        updatedMerchants
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .merchantName(UPDATED_MERCHANT_NAME)
            .creditRating(UPDATED_CREDIT_RATING)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .webSiteUrl(UPDATED_WEB_SITE_URL)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(updatedMerchants);

        restMerchantsMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isOk());

        // Validate the Merchants in the database
        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeUpdate);
        Merchants testMerchants = merchantsList.get(merchantsList.size() - 1);
        assertThat(testMerchants.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMerchants.getMerchantName()).isEqualTo(UPDATED_MERCHANT_NAME);
        assertThat(testMerchants.getCreditRating()).isEqualTo(UPDATED_CREDIT_RATING);
        assertThat(testMerchants.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testMerchants.getWebServiceUrl()).isEqualTo(UPDATED_WEB_SERVICE_URL);
        assertThat(testMerchants.getWebSiteUrl()).isEqualTo(UPDATED_WEB_SITE_URL);
        assertThat(testMerchants.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testMerchants.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchants() throws Exception {
        int databaseSizeBeforeUpdate = merchantsRepository.findAll().size();

        // Create the Merchants
        MerchantsDTO merchantsDTO = merchantsMapper.toDto(merchants);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantsMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Merchants in the database
        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMerchants() throws Exception {
        // Initialize the database
        merchantsRepository.saveAndFlush(merchants);

        int databaseSizeBeforeDelete = merchantsRepository.findAll().size();

        // Delete the merchants
        restMerchantsMockMvc.perform(delete("/api/merchants/{id}", merchants.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Merchants> merchantsList = merchantsRepository.findAll();
        assertThat(merchantsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merchants.class);
        Merchants merchants1 = new Merchants();
        merchants1.setId(1L);
        Merchants merchants2 = new Merchants();
        merchants2.setId(merchants1.getId());
        assertThat(merchants1).isEqualTo(merchants2);
        merchants2.setId(2L);
        assertThat(merchants1).isNotEqualTo(merchants2);
        merchants1.setId(null);
        assertThat(merchants1).isNotEqualTo(merchants2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantsDTO.class);
        MerchantsDTO merchantsDTO1 = new MerchantsDTO();
        merchantsDTO1.setId(1L);
        MerchantsDTO merchantsDTO2 = new MerchantsDTO();
        assertThat(merchantsDTO1).isNotEqualTo(merchantsDTO2);
        merchantsDTO2.setId(merchantsDTO1.getId());
        assertThat(merchantsDTO1).isEqualTo(merchantsDTO2);
        merchantsDTO2.setId(2L);
        assertThat(merchantsDTO1).isNotEqualTo(merchantsDTO2);
        merchantsDTO1.setId(null);
        assertThat(merchantsDTO1).isNotEqualTo(merchantsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(merchantsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(merchantsMapper.fromId(null)).isNull();
    }
}
