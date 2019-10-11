package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.UploadTransactions;
import com.resource.server.repository.UploadTransactionsRepository;
import com.resource.server.service.UploadTransactionsService;
import com.resource.server.service.dto.UploadTransactionsDTO;
import com.resource.server.service.mapper.UploadTransactionsMapper;
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
 * Test class for the UploadTransactionsResource REST controller.
 *
 * @see UploadTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class UploadTransactionsResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_URL = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_GENERATED_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_CODE = "BBBBBBBBBB";

    @Autowired
    private UploadTransactionsRepository uploadTransactionsRepository;

    @Autowired
    private UploadTransactionsMapper uploadTransactionsMapper;

    @Autowired
    private UploadTransactionsService uploadTransactionsService;

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

    private MockMvc restUploadTransactionsMockMvc;

    private UploadTransactions uploadTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadTransactionsResource uploadTransactionsResource = new UploadTransactionsResource(uploadTransactionsService);
        this.restUploadTransactionsMockMvc = MockMvcBuilders.standaloneSetup(uploadTransactionsResource)
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
    public static UploadTransactions createEntity(EntityManager em) {
        UploadTransactions uploadTransactions = new UploadTransactions()
            .fileName(DEFAULT_FILE_NAME)
            .templateUrl(DEFAULT_TEMPLATE_URL)
            .status(DEFAULT_STATUS)
            .generatedCode(DEFAULT_GENERATED_CODE);
        return uploadTransactions;
    }

    @Before
    public void initTest() {
        uploadTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadTransactions() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUploadTransactions.getTemplateUrl()).isEqualTo(DEFAULT_TEMPLATE_URL);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(DEFAULT_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void createUploadTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions with an existing ID
        uploadTransactions.setId(1L);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].templateUrl").value(hasItem(DEFAULT_TEMPLATE_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].generatedCode").value(hasItem(DEFAULT_GENERATED_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", uploadTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadTransactions.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.templateUrl").value(DEFAULT_TEMPLATE_URL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.generatedCode").value(DEFAULT_GENERATED_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUploadTransactions() throws Exception {
        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Update the uploadTransactions
        UploadTransactions updatedUploadTransactions = uploadTransactionsRepository.findById(uploadTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedUploadTransactions are not directly saved in db
        em.detach(updatedUploadTransactions);
        updatedUploadTransactions
            .fileName(UPDATED_FILE_NAME)
            .templateUrl(UPDATED_TEMPLATE_URL)
            .status(UPDATED_STATUS)
            .generatedCode(UPDATED_GENERATED_CODE);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(updatedUploadTransactions);

        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUploadTransactions.getTemplateUrl()).isEqualTo(UPDATED_TEMPLATE_URL);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadTransactions() throws Exception {
        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeDelete = uploadTransactionsRepository.findAll().size();

        // Delete the uploadTransactions
        restUploadTransactionsMockMvc.perform(delete("/api/upload-transactions/{id}", uploadTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadTransactions.class);
        UploadTransactions uploadTransactions1 = new UploadTransactions();
        uploadTransactions1.setId(1L);
        UploadTransactions uploadTransactions2 = new UploadTransactions();
        uploadTransactions2.setId(uploadTransactions1.getId());
        assertThat(uploadTransactions1).isEqualTo(uploadTransactions2);
        uploadTransactions2.setId(2L);
        assertThat(uploadTransactions1).isNotEqualTo(uploadTransactions2);
        uploadTransactions1.setId(null);
        assertThat(uploadTransactions1).isNotEqualTo(uploadTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadTransactionsDTO.class);
        UploadTransactionsDTO uploadTransactionsDTO1 = new UploadTransactionsDTO();
        uploadTransactionsDTO1.setId(1L);
        UploadTransactionsDTO uploadTransactionsDTO2 = new UploadTransactionsDTO();
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO2.setId(uploadTransactionsDTO1.getId());
        assertThat(uploadTransactionsDTO1).isEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO2.setId(2L);
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO1.setId(null);
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uploadTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uploadTransactionsMapper.fromId(null)).isNull();
    }
}
