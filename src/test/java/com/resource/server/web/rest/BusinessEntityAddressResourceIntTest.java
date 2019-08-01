package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.BusinessEntityAddress;
import com.resource.server.repository.BusinessEntityAddressRepository;
import com.resource.server.service.BusinessEntityAddressService;
import com.resource.server.service.dto.BusinessEntityAddressDTO;
import com.resource.server.service.mapper.BusinessEntityAddressMapper;
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
 * Test class for the BusinessEntityAddressResource REST controller.
 *
 * @see BusinessEntityAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class BusinessEntityAddressResourceIntTest {

    @Autowired
    private BusinessEntityAddressRepository businessEntityAddressRepository;

    @Autowired
    private BusinessEntityAddressMapper businessEntityAddressMapper;

    @Autowired
    private BusinessEntityAddressService businessEntityAddressService;

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

    private MockMvc restBusinessEntityAddressMockMvc;

    private BusinessEntityAddress businessEntityAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessEntityAddressResource businessEntityAddressResource = new BusinessEntityAddressResource(businessEntityAddressService);
        this.restBusinessEntityAddressMockMvc = MockMvcBuilders.standaloneSetup(businessEntityAddressResource)
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
    public static BusinessEntityAddress createEntity(EntityManager em) {
        BusinessEntityAddress businessEntityAddress = new BusinessEntityAddress();
        return businessEntityAddress;
    }

    @Before
    public void initTest() {
        businessEntityAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntityAddress() throws Exception {
        int databaseSizeBeforeCreate = businessEntityAddressRepository.findAll().size();

        // Create the BusinessEntityAddress
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);
        restBusinessEntityAddressMockMvc.perform(post("/api/business-entity-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntityAddress testBusinessEntityAddress = businessEntityAddressList.get(businessEntityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createBusinessEntityAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityAddressRepository.findAll().size();

        // Create the BusinessEntityAddress with an existing ID
        businessEntityAddress.setId(1L);
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityAddressMockMvc.perform(post("/api/business-entity-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessEntityAddresses() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        // Get all the businessEntityAddressList
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityAddress.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        // Get the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/{id}", businessEntityAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntityAddress.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessEntityAddress() throws Exception {
        // Get the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        int databaseSizeBeforeUpdate = businessEntityAddressRepository.findAll().size();

        // Update the businessEntityAddress
        BusinessEntityAddress updatedBusinessEntityAddress = businessEntityAddressRepository.findById(businessEntityAddress.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessEntityAddress are not directly saved in db
        em.detach(updatedBusinessEntityAddress);
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(updatedBusinessEntityAddress);

        restBusinessEntityAddressMockMvc.perform(put("/api/business-entity-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntityAddress testBusinessEntityAddress = businessEntityAddressList.get(businessEntityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntityAddress() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityAddressRepository.findAll().size();

        // Create the BusinessEntityAddress
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessEntityAddressMockMvc.perform(put("/api/business-entity-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        int databaseSizeBeforeDelete = businessEntityAddressRepository.findAll().size();

        // Delete the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(delete("/api/business-entity-addresses/{id}", businessEntityAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityAddress.class);
        BusinessEntityAddress businessEntityAddress1 = new BusinessEntityAddress();
        businessEntityAddress1.setId(1L);
        BusinessEntityAddress businessEntityAddress2 = new BusinessEntityAddress();
        businessEntityAddress2.setId(businessEntityAddress1.getId());
        assertThat(businessEntityAddress1).isEqualTo(businessEntityAddress2);
        businessEntityAddress2.setId(2L);
        assertThat(businessEntityAddress1).isNotEqualTo(businessEntityAddress2);
        businessEntityAddress1.setId(null);
        assertThat(businessEntityAddress1).isNotEqualTo(businessEntityAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityAddressDTO.class);
        BusinessEntityAddressDTO businessEntityAddressDTO1 = new BusinessEntityAddressDTO();
        businessEntityAddressDTO1.setId(1L);
        BusinessEntityAddressDTO businessEntityAddressDTO2 = new BusinessEntityAddressDTO();
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO2.setId(businessEntityAddressDTO1.getId());
        assertThat(businessEntityAddressDTO1).isEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO2.setId(2L);
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO1.setId(null);
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessEntityAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessEntityAddressMapper.fromId(null)).isNull();
    }
}
