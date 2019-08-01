package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductTransactions;
import com.resource.server.repository.ProductTransactionsRepository;
import com.resource.server.service.ProductTransactionsService;
import com.resource.server.service.dto.ProductTransactionsDTO;
import com.resource.server.service.mapper.ProductTransactionsMapper;
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
 * Test class for the ProductTransactionsResource REST controller.
 *
 * @see ProductTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductTransactionsResourceIntTest {

    private static final LocalDate DEFAULT_TRANSACTION_OCCURED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_OCCURED_WHEN = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    @Autowired
    private ProductTransactionsRepository productTransactionsRepository;

    @Autowired
    private ProductTransactionsMapper productTransactionsMapper;

    @Autowired
    private ProductTransactionsService productTransactionsService;

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

    private MockMvc restProductTransactionsMockMvc;

    private ProductTransactions productTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductTransactionsResource productTransactionsResource = new ProductTransactionsResource(productTransactionsService);
        this.restProductTransactionsMockMvc = MockMvcBuilders.standaloneSetup(productTransactionsResource)
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
    public static ProductTransactions createEntity(EntityManager em) {
        ProductTransactions productTransactions = new ProductTransactions()
            .transactionOccuredWhen(DEFAULT_TRANSACTION_OCCURED_WHEN)
            .quantity(DEFAULT_QUANTITY);
        return productTransactions;
    }

    @Before
    public void initTest() {
        productTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductTransactions() throws Exception {
        int databaseSizeBeforeCreate = productTransactionsRepository.findAll().size();

        // Create the ProductTransactions
        ProductTransactionsDTO productTransactionsDTO = productTransactionsMapper.toDto(productTransactions);
        restProductTransactionsMockMvc.perform(post("/api/product-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductTransactions in the database
        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTransactions testProductTransactions = productTransactionsList.get(productTransactionsList.size() - 1);
        assertThat(testProductTransactions.getTransactionOccuredWhen()).isEqualTo(DEFAULT_TRANSACTION_OCCURED_WHEN);
        assertThat(testProductTransactions.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createProductTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTransactionsRepository.findAll().size();

        // Create the ProductTransactions with an existing ID
        productTransactions.setId(1L);
        ProductTransactionsDTO productTransactionsDTO = productTransactionsMapper.toDto(productTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTransactionsMockMvc.perform(post("/api/product-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactions in the database
        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionOccuredWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTransactionsRepository.findAll().size();
        // set the field null
        productTransactions.setTransactionOccuredWhen(null);

        // Create the ProductTransactions, which fails.
        ProductTransactionsDTO productTransactionsDTO = productTransactionsMapper.toDto(productTransactions);

        restProductTransactionsMockMvc.perform(post("/api/product-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductTransactions() throws Exception {
        // Initialize the database
        productTransactionsRepository.saveAndFlush(productTransactions);

        // Get all the productTransactionsList
        restProductTransactionsMockMvc.perform(get("/api/product-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionOccuredWhen").value(hasItem(DEFAULT_TRANSACTION_OCCURED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProductTransactions() throws Exception {
        // Initialize the database
        productTransactionsRepository.saveAndFlush(productTransactions);

        // Get the productTransactions
        restProductTransactionsMockMvc.perform(get("/api/product-transactions/{id}", productTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productTransactions.getId().intValue()))
            .andExpect(jsonPath("$.transactionOccuredWhen").value(DEFAULT_TRANSACTION_OCCURED_WHEN.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductTransactions() throws Exception {
        // Get the productTransactions
        restProductTransactionsMockMvc.perform(get("/api/product-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductTransactions() throws Exception {
        // Initialize the database
        productTransactionsRepository.saveAndFlush(productTransactions);

        int databaseSizeBeforeUpdate = productTransactionsRepository.findAll().size();

        // Update the productTransactions
        ProductTransactions updatedProductTransactions = productTransactionsRepository.findById(productTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedProductTransactions are not directly saved in db
        em.detach(updatedProductTransactions);
        updatedProductTransactions
            .transactionOccuredWhen(UPDATED_TRANSACTION_OCCURED_WHEN)
            .quantity(UPDATED_QUANTITY);
        ProductTransactionsDTO productTransactionsDTO = productTransactionsMapper.toDto(updatedProductTransactions);

        restProductTransactionsMockMvc.perform(put("/api/product-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductTransactions in the database
        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeUpdate);
        ProductTransactions testProductTransactions = productTransactionsList.get(productTransactionsList.size() - 1);
        assertThat(testProductTransactions.getTransactionOccuredWhen()).isEqualTo(UPDATED_TRANSACTION_OCCURED_WHEN);
        assertThat(testProductTransactions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductTransactions() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionsRepository.findAll().size();

        // Create the ProductTransactions
        ProductTransactionsDTO productTransactionsDTO = productTransactionsMapper.toDto(productTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionsMockMvc.perform(put("/api/product-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactions in the database
        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductTransactions() throws Exception {
        // Initialize the database
        productTransactionsRepository.saveAndFlush(productTransactions);

        int databaseSizeBeforeDelete = productTransactionsRepository.findAll().size();

        // Delete the productTransactions
        restProductTransactionsMockMvc.perform(delete("/api/product-transactions/{id}", productTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductTransactions> productTransactionsList = productTransactionsRepository.findAll();
        assertThat(productTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTransactions.class);
        ProductTransactions productTransactions1 = new ProductTransactions();
        productTransactions1.setId(1L);
        ProductTransactions productTransactions2 = new ProductTransactions();
        productTransactions2.setId(productTransactions1.getId());
        assertThat(productTransactions1).isEqualTo(productTransactions2);
        productTransactions2.setId(2L);
        assertThat(productTransactions1).isNotEqualTo(productTransactions2);
        productTransactions1.setId(null);
        assertThat(productTransactions1).isNotEqualTo(productTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTransactionsDTO.class);
        ProductTransactionsDTO productTransactionsDTO1 = new ProductTransactionsDTO();
        productTransactionsDTO1.setId(1L);
        ProductTransactionsDTO productTransactionsDTO2 = new ProductTransactionsDTO();
        assertThat(productTransactionsDTO1).isNotEqualTo(productTransactionsDTO2);
        productTransactionsDTO2.setId(productTransactionsDTO1.getId());
        assertThat(productTransactionsDTO1).isEqualTo(productTransactionsDTO2);
        productTransactionsDTO2.setId(2L);
        assertThat(productTransactionsDTO1).isNotEqualTo(productTransactionsDTO2);
        productTransactionsDTO1.setId(null);
        assertThat(productTransactionsDTO1).isNotEqualTo(productTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productTransactionsMapper.fromId(null)).isNull();
    }
}
