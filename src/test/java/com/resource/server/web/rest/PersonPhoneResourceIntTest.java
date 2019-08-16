package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PersonPhone;
import com.resource.server.repository.PersonPhoneRepository;
import com.resource.server.service.PersonPhoneService;
import com.resource.server.service.dto.PersonPhoneDTO;
import com.resource.server.service.mapper.PersonPhoneMapper;
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
 * Test class for the PersonPhoneResource REST controller.
 *
 * @see PersonPhoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PersonPhoneResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private PersonPhoneRepository personPhoneRepository;

    @Autowired
    private PersonPhoneMapper personPhoneMapper;

    @Autowired
    private PersonPhoneService personPhoneService;

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

    private MockMvc restPersonPhoneMockMvc;

    private PersonPhone personPhone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonPhoneResource personPhoneResource = new PersonPhoneResource(personPhoneService);
        this.restPersonPhoneMockMvc = MockMvcBuilders.standaloneSetup(personPhoneResource)
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
    public static PersonPhone createEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
        return personPhone;
    }

    @Before
    public void initTest() {
        personPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPhone() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonPhone.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void createPersonPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone with an existing ID
        personPhone.setId(1L);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setPhoneNumber(null);

        // Create the PersonPhone, which fails.
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonPhones() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }

    @Test
    @Transactional
    public void getPersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", personPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPhone.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPhone() throws Exception {
        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Update the personPhone
        PersonPhone updatedPersonPhone = personPhoneRepository.findById(personPhone.getId()).get();
        // Disconnect from session so that the updates on updatedPersonPhone are not directly saved in db
        em.detach(updatedPersonPhone);
        updatedPersonPhone
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(updatedPersonPhone);

        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonPhone.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPhone() throws Exception {
        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeDelete = personPhoneRepository.findAll().size();

        // Delete the personPhone
        restPersonPhoneMockMvc.perform(delete("/api/person-phones/{id}", personPhone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPhone.class);
        PersonPhone personPhone1 = new PersonPhone();
        personPhone1.setId(1L);
        PersonPhone personPhone2 = new PersonPhone();
        personPhone2.setId(personPhone1.getId());
        assertThat(personPhone1).isEqualTo(personPhone2);
        personPhone2.setId(2L);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
        personPhone1.setId(null);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPhoneDTO.class);
        PersonPhoneDTO personPhoneDTO1 = new PersonPhoneDTO();
        personPhoneDTO1.setId(1L);
        PersonPhoneDTO personPhoneDTO2 = new PersonPhoneDTO();
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
        personPhoneDTO2.setId(personPhoneDTO1.getId());
        assertThat(personPhoneDTO1).isEqualTo(personPhoneDTO2);
        personPhoneDTO2.setId(2L);
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
        personPhoneDTO1.setId(null);
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personPhoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personPhoneMapper.fromId(null)).isNull();
    }
}
