package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.UploadActionTypes;
import com.resource.server.repository.UploadActionTypesRepository;
import com.resource.server.service.UploadActionTypesService;
import com.resource.server.service.dto.UploadActionTypesDTO;
import com.resource.server.service.mapper.UploadActionTypesMapper;
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
 * Test class for the UploadActionTypesResource REST controller.
 *
 * @see UploadActionTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class UploadActionTypesResourceIntTest {

    private static final String DEFAULT_ACTION_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private UploadActionTypesRepository uploadActionTypesRepository;

    @Autowired
    private UploadActionTypesMapper uploadActionTypesMapper;

    @Autowired
    private UploadActionTypesService uploadActionTypesService;

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

    private MockMvc restUploadActionTypesMockMvc;

    private UploadActionTypes uploadActionTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadActionTypesResource uploadActionTypesResource = new UploadActionTypesResource(uploadActionTypesService);
        this.restUploadActionTypesMockMvc = MockMvcBuilders.standaloneSetup(uploadActionTypesResource)
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
    public static UploadActionTypes createEntity(EntityManager em) {
        UploadActionTypes uploadActionTypes = new UploadActionTypes()
            .actionTypeName(DEFAULT_ACTION_TYPE_NAME);
        return uploadActionTypes;
    }

    @Before
    public void initTest() {
        uploadActionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadActionTypes() throws Exception {
        int databaseSizeBeforeCreate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);
        restUploadActionTypesMockMvc.perform(post("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        UploadActionTypes testUploadActionTypes = uploadActionTypesList.get(uploadActionTypesList.size() - 1);
        assertThat(testUploadActionTypes.getActionTypeName()).isEqualTo(DEFAULT_ACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createUploadActionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes with an existing ID
        uploadActionTypes.setId(1L);
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadActionTypesMockMvc.perform(post("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadActionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionTypeName").value(hasItem(DEFAULT_ACTION_TYPE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get the uploadActionTypes
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/{id}", uploadActionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadActionTypes.getId().intValue()))
            .andExpect(jsonPath("$.actionTypeName").value(DEFAULT_ACTION_TYPE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUploadActionTypes() throws Exception {
        // Get the uploadActionTypes
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        int databaseSizeBeforeUpdate = uploadActionTypesRepository.findAll().size();

        // Update the uploadActionTypes
        UploadActionTypes updatedUploadActionTypes = uploadActionTypesRepository.findById(uploadActionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedUploadActionTypes are not directly saved in db
        em.detach(updatedUploadActionTypes);
        updatedUploadActionTypes
            .actionTypeName(UPDATED_ACTION_TYPE_NAME);
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(updatedUploadActionTypes);

        restUploadActionTypesMockMvc.perform(put("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeUpdate);
        UploadActionTypes testUploadActionTypes = uploadActionTypesList.get(uploadActionTypesList.size() - 1);
        assertThat(testUploadActionTypes.getActionTypeName()).isEqualTo(UPDATED_ACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadActionTypes() throws Exception {
        int databaseSizeBeforeUpdate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadActionTypesMockMvc.perform(put("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        int databaseSizeBeforeDelete = uploadActionTypesRepository.findAll().size();

        // Delete the uploadActionTypes
        restUploadActionTypesMockMvc.perform(delete("/api/upload-action-types/{id}", uploadActionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadActionTypes.class);
        UploadActionTypes uploadActionTypes1 = new UploadActionTypes();
        uploadActionTypes1.setId(1L);
        UploadActionTypes uploadActionTypes2 = new UploadActionTypes();
        uploadActionTypes2.setId(uploadActionTypes1.getId());
        assertThat(uploadActionTypes1).isEqualTo(uploadActionTypes2);
        uploadActionTypes2.setId(2L);
        assertThat(uploadActionTypes1).isNotEqualTo(uploadActionTypes2);
        uploadActionTypes1.setId(null);
        assertThat(uploadActionTypes1).isNotEqualTo(uploadActionTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadActionTypesDTO.class);
        UploadActionTypesDTO uploadActionTypesDTO1 = new UploadActionTypesDTO();
        uploadActionTypesDTO1.setId(1L);
        UploadActionTypesDTO uploadActionTypesDTO2 = new UploadActionTypesDTO();
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO2.setId(uploadActionTypesDTO1.getId());
        assertThat(uploadActionTypesDTO1).isEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO2.setId(2L);
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO1.setId(null);
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uploadActionTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uploadActionTypesMapper.fromId(null)).isNull();
    }
}
