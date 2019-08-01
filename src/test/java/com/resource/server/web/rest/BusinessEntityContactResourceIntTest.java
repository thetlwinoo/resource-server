package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.BusinessEntityContact;
import com.resource.server.repository.BusinessEntityContactRepository;
import com.resource.server.service.BusinessEntityContactService;
import com.resource.server.service.dto.BusinessEntityContactDTO;
import com.resource.server.service.mapper.BusinessEntityContactMapper;
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
 * Test class for the BusinessEntityContactResource REST controller.
 *
 * @see BusinessEntityContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class BusinessEntityContactResourceIntTest {

    @Autowired
    private BusinessEntityContactRepository businessEntityContactRepository;

    @Autowired
    private BusinessEntityContactMapper businessEntityContactMapper;

    @Autowired
    private BusinessEntityContactService businessEntityContactService;

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

    private MockMvc restBusinessEntityContactMockMvc;

    private BusinessEntityContact businessEntityContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessEntityContactResource businessEntityContactResource = new BusinessEntityContactResource(businessEntityContactService);
        this.restBusinessEntityContactMockMvc = MockMvcBuilders.standaloneSetup(businessEntityContactResource)
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
    public static BusinessEntityContact createEntity(EntityManager em) {
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        return businessEntityContact;
    }

    @Before
    public void initTest() {
        businessEntityContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntityContact() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);
    }

    @Test
    @Transactional
    public void createBusinessEntityContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact with an existing ID
        businessEntityContact.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessEntityContacts() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get all the businessEntityContactList
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", businessEntityContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntityContact.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessEntityContact() throws Exception {
        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Update the businessEntityContact
        BusinessEntityContact updatedBusinessEntityContact = businessEntityContactRepository.findById(businessEntityContact.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessEntityContact are not directly saved in db
        em.detach(updatedBusinessEntityContact);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(updatedBusinessEntityContact);

        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntityContact() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeDelete = businessEntityContactRepository.findAll().size();

        // Delete the businessEntityContact
        restBusinessEntityContactMockMvc.perform(delete("/api/business-entity-contacts/{id}", businessEntityContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContact.class);
        BusinessEntityContact businessEntityContact1 = new BusinessEntityContact();
        businessEntityContact1.setId(1L);
        BusinessEntityContact businessEntityContact2 = new BusinessEntityContact();
        businessEntityContact2.setId(businessEntityContact1.getId());
        assertThat(businessEntityContact1).isEqualTo(businessEntityContact2);
        businessEntityContact2.setId(2L);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
        businessEntityContact1.setId(null);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContactDTO.class);
        BusinessEntityContactDTO businessEntityContactDTO1 = new BusinessEntityContactDTO();
        businessEntityContactDTO1.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO2 = new BusinessEntityContactDTO();
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(businessEntityContactDTO1.getId());
        assertThat(businessEntityContactDTO1).isEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(2L);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO1.setId(null);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessEntityContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessEntityContactMapper.fromId(null)).isNull();
    }
}
