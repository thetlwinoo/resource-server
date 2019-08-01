package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.People;
import com.resource.server.repository.PeopleRepository;
import com.resource.server.service.PeopleService;
import com.resource.server.service.dto.PeopleDTO;
import com.resource.server.service.mapper.PeopleMapper;
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
 * Test class for the PeopleResource REST controller.
 *
 * @see PeopleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PeopleResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PERMITTED_TO_LOGON = false;
    private static final Boolean UPDATED_IS_PERMITTED_TO_LOGON = true;

    private static final String DEFAULT_LOGON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGON_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EXTERNAL_LOGON_PROVIDER = false;
    private static final Boolean UPDATED_IS_EXTERNAL_LOGON_PROVIDER = true;

    private static final Boolean DEFAULT_IS_SYSTEM_USER = false;
    private static final Boolean UPDATED_IS_SYSTEM_USER = true;

    private static final Boolean DEFAULT_IS_EMPLOYEE = false;
    private static final Boolean UPDATED_IS_EMPLOYEE = true;

    private static final Boolean DEFAULT_IS_SALES_PERSON = false;
    private static final Boolean UPDATED_IS_SALES_PERSON = true;

    private static final Boolean DEFAULT_IS_GUEST_USER = false;
    private static final Boolean UPDATED_IS_GUEST_USER = true;

    private static final Integer DEFAULT_EMAIL_PROMOTION = 1;
    private static final Integer UPDATED_EMAIL_PROMOTION = 2;

    private static final String DEFAULT_USER_PREFERENCES = "AAAAAAAAAA";
    private static final String UPDATED_USER_PREFERENCES = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_LANGUAGES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private PeopleService peopleService;

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

    private MockMvc restPeopleMockMvc;

    private People people;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeopleResource peopleResource = new PeopleResource(peopleService);
        this.restPeopleMockMvc = MockMvcBuilders.standaloneSetup(peopleResource)
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
    public static People createEntity(EntityManager em) {
        People people = new People()
            .fullName(DEFAULT_FULL_NAME)
            .preferredName(DEFAULT_PREFERRED_NAME)
            .searchName(DEFAULT_SEARCH_NAME)
            .isPermittedToLogon(DEFAULT_IS_PERMITTED_TO_LOGON)
            .logonName(DEFAULT_LOGON_NAME)
            .isExternalLogonProvider(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER)
            .isSystemUser(DEFAULT_IS_SYSTEM_USER)
            .isEmployee(DEFAULT_IS_EMPLOYEE)
            .isSalesPerson(DEFAULT_IS_SALES_PERSON)
            .isGuestUser(DEFAULT_IS_GUEST_USER)
            .emailPromotion(DEFAULT_EMAIL_PROMOTION)
            .userPreferences(DEFAULT_USER_PREFERENCES)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .photo(DEFAULT_PHOTO)
            .customFields(DEFAULT_CUSTOM_FIELDS)
            .otherLanguages(DEFAULT_OTHER_LANGUAGES)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return people;
    }

    @Before
    public void initTest() {
        people = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPeople.getPreferredName()).isEqualTo(DEFAULT_PREFERRED_NAME);
        assertThat(testPeople.getSearchName()).isEqualTo(DEFAULT_SEARCH_NAME);
        assertThat(testPeople.isIsPermittedToLogon()).isEqualTo(DEFAULT_IS_PERMITTED_TO_LOGON);
        assertThat(testPeople.getLogonName()).isEqualTo(DEFAULT_LOGON_NAME);
        assertThat(testPeople.isIsExternalLogonProvider()).isEqualTo(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER);
        assertThat(testPeople.isIsSystemUser()).isEqualTo(DEFAULT_IS_SYSTEM_USER);
        assertThat(testPeople.isIsEmployee()).isEqualTo(DEFAULT_IS_EMPLOYEE);
        assertThat(testPeople.isIsSalesPerson()).isEqualTo(DEFAULT_IS_SALES_PERSON);
        assertThat(testPeople.isIsGuestUser()).isEqualTo(DEFAULT_IS_GUEST_USER);
        assertThat(testPeople.getEmailPromotion()).isEqualTo(DEFAULT_EMAIL_PROMOTION);
        assertThat(testPeople.getUserPreferences()).isEqualTo(DEFAULT_USER_PREFERENCES);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPeople.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testPeople.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPeople.getCustomFields()).isEqualTo(DEFAULT_CUSTOM_FIELDS);
        assertThat(testPeople.getOtherLanguages()).isEqualTo(DEFAULT_OTHER_LANGUAGES);
        assertThat(testPeople.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPeople.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People with an existing ID
        people.setId(1L);
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setFullName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferredNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setPreferredName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSearchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setSearchName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPermittedToLogonIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsPermittedToLogon(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExternalLogonProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsExternalLogonProvider(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSystemUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsSystemUser(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEmployeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsEmployee(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSalesPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsSalesPerson(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsGuestUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsGuestUser(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPromotionIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setEmailPromotion(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setValidFrom(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setValidTo(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME.toString())))
            .andExpect(jsonPath("$.[*].searchName").value(hasItem(DEFAULT_SEARCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].isPermittedToLogon").value(hasItem(DEFAULT_IS_PERMITTED_TO_LOGON.booleanValue())))
            .andExpect(jsonPath("$.[*].logonName").value(hasItem(DEFAULT_LOGON_NAME.toString())))
            .andExpect(jsonPath("$.[*].isExternalLogonProvider").value(hasItem(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER.booleanValue())))
            .andExpect(jsonPath("$.[*].isSystemUser").value(hasItem(DEFAULT_IS_SYSTEM_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].isEmployee").value(hasItem(DEFAULT_IS_EMPLOYEE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSalesPerson").value(hasItem(DEFAULT_IS_SALES_PERSON.booleanValue())))
            .andExpect(jsonPath("$.[*].isGuestUser").value(hasItem(DEFAULT_IS_GUEST_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].emailPromotion").value(hasItem(DEFAULT_EMAIL_PROMOTION)))
            .andExpect(jsonPath("$.[*].userPreferences").value(hasItem(DEFAULT_USER_PREFERENCES.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS.toString())))
            .andExpect(jsonPath("$.[*].otherLanguages").value(hasItem(DEFAULT_OTHER_LANGUAGES.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.preferredName").value(DEFAULT_PREFERRED_NAME.toString()))
            .andExpect(jsonPath("$.searchName").value(DEFAULT_SEARCH_NAME.toString()))
            .andExpect(jsonPath("$.isPermittedToLogon").value(DEFAULT_IS_PERMITTED_TO_LOGON.booleanValue()))
            .andExpect(jsonPath("$.logonName").value(DEFAULT_LOGON_NAME.toString()))
            .andExpect(jsonPath("$.isExternalLogonProvider").value(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER.booleanValue()))
            .andExpect(jsonPath("$.isSystemUser").value(DEFAULT_IS_SYSTEM_USER.booleanValue()))
            .andExpect(jsonPath("$.isEmployee").value(DEFAULT_IS_EMPLOYEE.booleanValue()))
            .andExpect(jsonPath("$.isSalesPerson").value(DEFAULT_IS_SALES_PERSON.booleanValue()))
            .andExpect(jsonPath("$.isGuestUser").value(DEFAULT_IS_GUEST_USER.booleanValue()))
            .andExpect(jsonPath("$.emailPromotion").value(DEFAULT_EMAIL_PROMOTION))
            .andExpect(jsonPath("$.userPreferences").value(DEFAULT_USER_PREFERENCES.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.customFields").value(DEFAULT_CUSTOM_FIELDS.toString()))
            .andExpect(jsonPath("$.otherLanguages").value(DEFAULT_OTHER_LANGUAGES.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).get();
        // Disconnect from session so that the updates on updatedPeople are not directly saved in db
        em.detach(updatedPeople);
        updatedPeople
            .fullName(UPDATED_FULL_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .searchName(UPDATED_SEARCH_NAME)
            .isPermittedToLogon(UPDATED_IS_PERMITTED_TO_LOGON)
            .logonName(UPDATED_LOGON_NAME)
            .isExternalLogonProvider(UPDATED_IS_EXTERNAL_LOGON_PROVIDER)
            .isSystemUser(UPDATED_IS_SYSTEM_USER)
            .isEmployee(UPDATED_IS_EMPLOYEE)
            .isSalesPerson(UPDATED_IS_SALES_PERSON)
            .isGuestUser(UPDATED_IS_GUEST_USER)
            .emailPromotion(UPDATED_EMAIL_PROMOTION)
            .userPreferences(UPDATED_USER_PREFERENCES)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .photo(UPDATED_PHOTO)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .otherLanguages(UPDATED_OTHER_LANGUAGES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PeopleDTO peopleDTO = peopleMapper.toDto(updatedPeople);

        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPeople.getPreferredName()).isEqualTo(UPDATED_PREFERRED_NAME);
        assertThat(testPeople.getSearchName()).isEqualTo(UPDATED_SEARCH_NAME);
        assertThat(testPeople.isIsPermittedToLogon()).isEqualTo(UPDATED_IS_PERMITTED_TO_LOGON);
        assertThat(testPeople.getLogonName()).isEqualTo(UPDATED_LOGON_NAME);
        assertThat(testPeople.isIsExternalLogonProvider()).isEqualTo(UPDATED_IS_EXTERNAL_LOGON_PROVIDER);
        assertThat(testPeople.isIsSystemUser()).isEqualTo(UPDATED_IS_SYSTEM_USER);
        assertThat(testPeople.isIsEmployee()).isEqualTo(UPDATED_IS_EMPLOYEE);
        assertThat(testPeople.isIsSalesPerson()).isEqualTo(UPDATED_IS_SALES_PERSON);
        assertThat(testPeople.isIsGuestUser()).isEqualTo(UPDATED_IS_GUEST_USER);
        assertThat(testPeople.getEmailPromotion()).isEqualTo(UPDATED_EMAIL_PROMOTION);
        assertThat(testPeople.getUserPreferences()).isEqualTo(UPDATED_USER_PREFERENCES);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPeople.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testPeople.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPeople.getCustomFields()).isEqualTo(UPDATED_CUSTOM_FIELDS);
        assertThat(testPeople.getOtherLanguages()).isEqualTo(UPDATED_OTHER_LANGUAGES);
        assertThat(testPeople.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPeople.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPeople() throws Exception {
        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Delete the people
        restPeopleMockMvc.perform(delete("/api/people/{id}", people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(People.class);
        People people1 = new People();
        people1.setId(1L);
        People people2 = new People();
        people2.setId(people1.getId());
        assertThat(people1).isEqualTo(people2);
        people2.setId(2L);
        assertThat(people1).isNotEqualTo(people2);
        people1.setId(null);
        assertThat(people1).isNotEqualTo(people2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeopleDTO.class);
        PeopleDTO peopleDTO1 = new PeopleDTO();
        peopleDTO1.setId(1L);
        PeopleDTO peopleDTO2 = new PeopleDTO();
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
        peopleDTO2.setId(peopleDTO1.getId());
        assertThat(peopleDTO1).isEqualTo(peopleDTO2);
        peopleDTO2.setId(2L);
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
        peopleDTO1.setId(null);
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(peopleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(peopleMapper.fromId(null)).isNull();
    }
}
