package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PhoneNumberType;
import com.resource.server.repository.PhoneNumberTypeRepository;
import com.resource.server.service.PhoneNumberTypeService;
import com.resource.server.service.dto.PhoneNumberTypeDTO;
import com.resource.server.service.mapper.PhoneNumberTypeMapper;
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
 * Test class for the PhoneNumberTypeResource REST controller.
 *
 * @see PhoneNumberTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PhoneNumberTypeResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private PhoneNumberTypeRepository phoneNumberTypeRepository;

    @Autowired
    private PhoneNumberTypeMapper phoneNumberTypeMapper;

    @Autowired
    private PhoneNumberTypeService phoneNumberTypeService;

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

    private MockMvc restPhoneNumberTypeMockMvc;

    private PhoneNumberType phoneNumberType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhoneNumberTypeResource phoneNumberTypeResource = new PhoneNumberTypeResource(phoneNumberTypeService);
        this.restPhoneNumberTypeMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberTypeResource)
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
    public static PhoneNumberType createEntity(EntityManager em) {
        PhoneNumberType phoneNumberType = new PhoneNumberType()
            .phoneNumberTypeName(DEFAULT_PHONE_NUMBER_TYPE_NAME);
        return phoneNumberType;
    }

    @Before
    public void initTest() {
        phoneNumberType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumberType() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getPhoneNumberTypeName()).isEqualTo(DEFAULT_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createPhoneNumberTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType with an existing ID
        phoneNumberType.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhoneNumberTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberTypeRepository.findAll().size();
        // set the field null
        phoneNumberType.setPhoneNumberTypeName(null);

        // Create the PhoneNumberType, which fails.
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypes() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberTypeName").value(hasItem(DEFAULT_PHONE_NUMBER_TYPE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", phoneNumberType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumberType.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumberTypeName").value(DEFAULT_PHONE_NUMBER_TYPE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumberType() throws Exception {
        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Update the phoneNumberType
        PhoneNumberType updatedPhoneNumberType = phoneNumberTypeRepository.findById(phoneNumberType.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneNumberType are not directly saved in db
        em.detach(updatedPhoneNumberType);
        updatedPhoneNumberType
            .phoneNumberTypeName(UPDATED_PHONE_NUMBER_TYPE_NAME);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(updatedPhoneNumberType);

        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getPhoneNumberTypeName()).isEqualTo(UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumberType() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeDelete = phoneNumberTypeRepository.findAll().size();

        // Delete the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(delete("/api/phone-number-types/{id}", phoneNumberType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberType.class);
        PhoneNumberType phoneNumberType1 = new PhoneNumberType();
        phoneNumberType1.setId(1L);
        PhoneNumberType phoneNumberType2 = new PhoneNumberType();
        phoneNumberType2.setId(phoneNumberType1.getId());
        assertThat(phoneNumberType1).isEqualTo(phoneNumberType2);
        phoneNumberType2.setId(2L);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
        phoneNumberType1.setId(null);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberTypeDTO.class);
        PhoneNumberTypeDTO phoneNumberTypeDTO1 = new PhoneNumberTypeDTO();
        phoneNumberTypeDTO1.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO2 = new PhoneNumberTypeDTO();
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(phoneNumberTypeDTO1.getId());
        assertThat(phoneNumberTypeDTO1).isEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(2L);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO1.setId(null);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(phoneNumberTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(phoneNumberTypeMapper.fromId(null)).isNull();
    }
}
