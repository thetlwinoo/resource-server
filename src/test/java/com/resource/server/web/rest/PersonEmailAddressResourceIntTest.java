package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PersonEmailAddress;
import com.resource.server.repository.PersonEmailAddressRepository;
import com.resource.server.service.PersonEmailAddressService;
import com.resource.server.service.dto.PersonEmailAddressDTO;
import com.resource.server.service.mapper.PersonEmailAddressMapper;
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
 * Test class for the PersonEmailAddressResource REST controller.
 *
 * @see PersonEmailAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PersonEmailAddressResourceIntTest {

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private PersonEmailAddressRepository personEmailAddressRepository;

    @Autowired
    private PersonEmailAddressMapper personEmailAddressMapper;

    @Autowired
    private PersonEmailAddressService personEmailAddressService;

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

    private MockMvc restPersonEmailAddressMockMvc;

    private PersonEmailAddress personEmailAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonEmailAddressResource personEmailAddressResource = new PersonEmailAddressResource(personEmailAddressService);
        this.restPersonEmailAddressMockMvc = MockMvcBuilders.standaloneSetup(personEmailAddressResource)
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
    public static PersonEmailAddress createEntity(EntityManager em) {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress()
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
        return personEmailAddress;
    }

    @Before
    public void initTest() {
        personEmailAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEmailAddress() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonEmailAddress.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void createPersonEmailAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress with an existing ID
        personEmailAddress.setId(1L);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personEmailAddressRepository.findAll().size();
        // set the field null
        personEmailAddress.setEmailAddress(null);

        // Create the PersonEmailAddress, which fails.
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddresses() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEmailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }

    @Test
    @Transactional
    public void getPersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", personEmailAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personEmailAddress.getId().intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonEmailAddress() throws Exception {
        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Update the personEmailAddress
        PersonEmailAddress updatedPersonEmailAddress = personEmailAddressRepository.findById(personEmailAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPersonEmailAddress are not directly saved in db
        em.detach(updatedPersonEmailAddress);
        updatedPersonEmailAddress
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(updatedPersonEmailAddress);

        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isOk());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonEmailAddress.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEmailAddress() throws Exception {
        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeDelete = personEmailAddressRepository.findAll().size();

        // Delete the personEmailAddress
        restPersonEmailAddressMockMvc.perform(delete("/api/person-email-addresses/{id}", personEmailAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEmailAddress.class);
        PersonEmailAddress personEmailAddress1 = new PersonEmailAddress();
        personEmailAddress1.setId(1L);
        PersonEmailAddress personEmailAddress2 = new PersonEmailAddress();
        personEmailAddress2.setId(personEmailAddress1.getId());
        assertThat(personEmailAddress1).isEqualTo(personEmailAddress2);
        personEmailAddress2.setId(2L);
        assertThat(personEmailAddress1).isNotEqualTo(personEmailAddress2);
        personEmailAddress1.setId(null);
        assertThat(personEmailAddress1).isNotEqualTo(personEmailAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEmailAddressDTO.class);
        PersonEmailAddressDTO personEmailAddressDTO1 = new PersonEmailAddressDTO();
        personEmailAddressDTO1.setId(1L);
        PersonEmailAddressDTO personEmailAddressDTO2 = new PersonEmailAddressDTO();
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO2.setId(personEmailAddressDTO1.getId());
        assertThat(personEmailAddressDTO1).isEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO2.setId(2L);
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO1.setId(null);
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personEmailAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personEmailAddressMapper.fromId(null)).isNull();
    }
}
