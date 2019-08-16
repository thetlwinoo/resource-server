package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.AddressTypes;
import com.resource.server.repository.AddressTypesRepository;
import com.resource.server.service.AddressTypesService;
import com.resource.server.service.dto.AddressTypesDTO;
import com.resource.server.service.mapper.AddressTypesMapper;
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
 * Test class for the AddressTypesResource REST controller.
 *
 * @see AddressTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class AddressTypesResourceIntTest {

    private static final String DEFAULT_ADDRESS_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFER = "AAAAAAAAAA";
    private static final String UPDATED_REFER = "BBBBBBBBBB";

    @Autowired
    private AddressTypesRepository addressTypesRepository;

    @Autowired
    private AddressTypesMapper addressTypesMapper;

    @Autowired
    private AddressTypesService addressTypesService;

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

    private MockMvc restAddressTypesMockMvc;

    private AddressTypes addressTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressTypesResource addressTypesResource = new AddressTypesResource(addressTypesService);
        this.restAddressTypesMockMvc = MockMvcBuilders.standaloneSetup(addressTypesResource)
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
    public static AddressTypes createEntity(EntityManager em) {
        AddressTypes addressTypes = new AddressTypes()
            .addressTypeName(DEFAULT_ADDRESS_TYPE_NAME)
            .refer(DEFAULT_REFER);
        return addressTypes;
    }

    @Before
    public void initTest() {
        addressTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressTypes() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate + 1);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getAddressTypeName()).isEqualTo(DEFAULT_ADDRESS_TYPE_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(DEFAULT_REFER);
    }

    @Test
    @Transactional
    public void createAddressTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes with an existing ID
        addressTypes.setId(1L);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAddressTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypesRepository.findAll().size();
        // set the field null
        addressTypes.setAddressTypeName(null);

        // Create the AddressTypes, which fails.
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressTypeName").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].refer").value(hasItem(DEFAULT_REFER)));
    }

    @Test
    @Transactional
    public void getAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", addressTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressTypes.getId().intValue()))
            .andExpect(jsonPath("$.addressTypeName").value(DEFAULT_ADDRESS_TYPE_NAME))
            .andExpect(jsonPath("$.refer").value(DEFAULT_REFER));
    }

    @Test
    @Transactional
    public void getNonExistingAddressTypes() throws Exception {
        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Update the addressTypes
        AddressTypes updatedAddressTypes = addressTypesRepository.findById(addressTypes.getId()).get();
        // Disconnect from session so that the updates on updatedAddressTypes are not directly saved in db
        em.detach(updatedAddressTypes);
        updatedAddressTypes
            .addressTypeName(UPDATED_ADDRESS_TYPE_NAME)
            .refer(UPDATED_REFER);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(updatedAddressTypes);

        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isOk());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getAddressTypeName()).isEqualTo(UPDATED_ADDRESS_TYPE_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(UPDATED_REFER);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressTypes() throws Exception {
        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeDelete = addressTypesRepository.findAll().size();

        // Delete the addressTypes
        restAddressTypesMockMvc.perform(delete("/api/address-types/{id}", addressTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypes.class);
        AddressTypes addressTypes1 = new AddressTypes();
        addressTypes1.setId(1L);
        AddressTypes addressTypes2 = new AddressTypes();
        addressTypes2.setId(addressTypes1.getId());
        assertThat(addressTypes1).isEqualTo(addressTypes2);
        addressTypes2.setId(2L);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
        addressTypes1.setId(null);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypesDTO.class);
        AddressTypesDTO addressTypesDTO1 = new AddressTypesDTO();
        addressTypesDTO1.setId(1L);
        AddressTypesDTO addressTypesDTO2 = new AddressTypesDTO();
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(addressTypesDTO1.getId());
        assertThat(addressTypesDTO1).isEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(2L);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO1.setId(null);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressTypesMapper.fromId(null)).isNull();
    }
}
