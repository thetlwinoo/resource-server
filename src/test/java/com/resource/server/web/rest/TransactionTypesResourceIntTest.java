package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.TransactionTypes;
import com.resource.server.repository.TransactionTypesRepository;
import com.resource.server.service.TransactionTypesService;
import com.resource.server.service.dto.TransactionTypesDTO;
import com.resource.server.service.mapper.TransactionTypesMapper;
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
 * Test class for the TransactionTypesResource REST controller.
 *
 * @see TransactionTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class TransactionTypesResourceIntTest {

    private static final String DEFAULT_TRANSACTION_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Autowired
    private TransactionTypesMapper transactionTypesMapper;

    @Autowired
    private TransactionTypesService transactionTypesService;

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

    private MockMvc restTransactionTypesMockMvc;

    private TransactionTypes transactionTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypesResource transactionTypesResource = new TransactionTypesResource(transactionTypesService);
        this.restTransactionTypesMockMvc = MockMvcBuilders.standaloneSetup(transactionTypesResource)
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
    public static TransactionTypes createEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .transactionTypeName(DEFAULT_TRANSACTION_TYPE_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return transactionTypes;
    }

    @Before
    public void initTest() {
        transactionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionTypes() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getTransactionTypeName()).isEqualTo(DEFAULT_TRANSACTION_TYPE_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTransactionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes with an existing ID
        transactionTypes.setId(1L);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setTransactionTypeName(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidFrom(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidTo(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionTypeName").value(hasItem(DEFAULT_TRANSACTION_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }

    @Test
    @Transactional
    public void getTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", transactionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionTypes.getId().intValue()))
            .andExpect(jsonPath("$.transactionTypeName").value(DEFAULT_TRANSACTION_TYPE_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionTypes() throws Exception {
        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes
        TransactionTypes updatedTransactionTypes = transactionTypesRepository.findById(transactionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionTypes are not directly saved in db
        em.detach(updatedTransactionTypes);
        updatedTransactionTypes
            .transactionTypeName(UPDATED_TRANSACTION_TYPE_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(updatedTransactionTypes);

        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getTransactionTypeName()).isEqualTo(UPDATED_TRANSACTION_TYPE_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeDelete = transactionTypesRepository.findAll().size();

        // Delete the transactionTypes
        restTransactionTypesMockMvc.perform(delete("/api/transaction-types/{id}", transactionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypes.class);
        TransactionTypes transactionTypes1 = new TransactionTypes();
        transactionTypes1.setId(1L);
        TransactionTypes transactionTypes2 = new TransactionTypes();
        transactionTypes2.setId(transactionTypes1.getId());
        assertThat(transactionTypes1).isEqualTo(transactionTypes2);
        transactionTypes2.setId(2L);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
        transactionTypes1.setId(null);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypesDTO.class);
        TransactionTypesDTO transactionTypesDTO1 = new TransactionTypesDTO();
        transactionTypesDTO1.setId(1L);
        TransactionTypesDTO transactionTypesDTO2 = new TransactionTypesDTO();
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(transactionTypesDTO1.getId());
        assertThat(transactionTypesDTO1).isEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(2L);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO1.setId(null);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionTypesMapper.fromId(null)).isNull();
    }
}
