package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockItemTransactions;
import com.resource.server.repository.StockItemTransactionsRepository;
import com.resource.server.service.StockItemTransactionsService;
import com.resource.server.service.dto.StockItemTransactionsDTO;
import com.resource.server.service.mapper.StockItemTransactionsMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockItemTransactionsResource REST controller.
 *
 * @see StockItemTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemTransactionsResourceIntTest {

    private static final LocalDate DEFAULT_TRANSACTION_OCCURRED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_OCCURRED_WHEN = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    @Autowired
    private StockItemTransactionsRepository stockItemTransactionsRepository;

    @Autowired
    private StockItemTransactionsMapper stockItemTransactionsMapper;

    @Autowired
    private StockItemTransactionsService stockItemTransactionsService;

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

    private MockMvc restStockItemTransactionsMockMvc;

    private StockItemTransactions stockItemTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemTransactionsResource stockItemTransactionsResource = new StockItemTransactionsResource(stockItemTransactionsService);
        this.restStockItemTransactionsMockMvc = MockMvcBuilders.standaloneSetup(stockItemTransactionsResource)
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
    public static StockItemTransactions createEntity(EntityManager em) {
        StockItemTransactions stockItemTransactions = new StockItemTransactions()
            .transactionOccurredWhen(DEFAULT_TRANSACTION_OCCURRED_WHEN)
            .quantity(DEFAULT_QUANTITY);
        return stockItemTransactions;
    }

    @Before
    public void initTest() {
        stockItemTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemTransactions() throws Exception {
        int databaseSizeBeforeCreate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);
        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemTransactions testStockItemTransactions = stockItemTransactionsList.get(stockItemTransactionsList.size() - 1);
        assertThat(testStockItemTransactions.getTransactionOccurredWhen()).isEqualTo(DEFAULT_TRANSACTION_OCCURRED_WHEN);
        assertThat(testStockItemTransactions.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createStockItemTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions with an existing ID
        stockItemTransactions.setId(1L);
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionOccurredWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTransactionsRepository.findAll().size();
        // set the field null
        stockItemTransactions.setTransactionOccurredWhen(null);

        // Create the StockItemTransactions, which fails.
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTransactionsRepository.findAll().size();
        // set the field null
        stockItemTransactions.setQuantity(null);

        // Create the StockItemTransactions, which fails.
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionOccurredWhen").value(hasItem(DEFAULT_TRANSACTION_OCCURRED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())));
    }
    
    @Test
    @Transactional
    public void getStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/{id}", stockItemTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemTransactions.getId().intValue()))
            .andExpect(jsonPath("$.transactionOccurredWhen").value(DEFAULT_TRANSACTION_OCCURRED_WHEN.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemTransactions() throws Exception {
        // Get the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        int databaseSizeBeforeUpdate = stockItemTransactionsRepository.findAll().size();

        // Update the stockItemTransactions
        StockItemTransactions updatedStockItemTransactions = stockItemTransactionsRepository.findById(stockItemTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemTransactions are not directly saved in db
        em.detach(updatedStockItemTransactions);
        updatedStockItemTransactions
            .transactionOccurredWhen(UPDATED_TRANSACTION_OCCURRED_WHEN)
            .quantity(UPDATED_QUANTITY);
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(updatedStockItemTransactions);

        restStockItemTransactionsMockMvc.perform(put("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeUpdate);
        StockItemTransactions testStockItemTransactions = stockItemTransactionsList.get(stockItemTransactionsList.size() - 1);
        assertThat(testStockItemTransactions.getTransactionOccurredWhen()).isEqualTo(UPDATED_TRANSACTION_OCCURRED_WHEN);
        assertThat(testStockItemTransactions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemTransactions() throws Exception {
        int databaseSizeBeforeUpdate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemTransactionsMockMvc.perform(put("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        int databaseSizeBeforeDelete = stockItemTransactionsRepository.findAll().size();

        // Delete the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(delete("/api/stock-item-transactions/{id}", stockItemTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTransactions.class);
        StockItemTransactions stockItemTransactions1 = new StockItemTransactions();
        stockItemTransactions1.setId(1L);
        StockItemTransactions stockItemTransactions2 = new StockItemTransactions();
        stockItemTransactions2.setId(stockItemTransactions1.getId());
        assertThat(stockItemTransactions1).isEqualTo(stockItemTransactions2);
        stockItemTransactions2.setId(2L);
        assertThat(stockItemTransactions1).isNotEqualTo(stockItemTransactions2);
        stockItemTransactions1.setId(null);
        assertThat(stockItemTransactions1).isNotEqualTo(stockItemTransactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTransactionsDTO.class);
        StockItemTransactionsDTO stockItemTransactionsDTO1 = new StockItemTransactionsDTO();
        stockItemTransactionsDTO1.setId(1L);
        StockItemTransactionsDTO stockItemTransactionsDTO2 = new StockItemTransactionsDTO();
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO2.setId(stockItemTransactionsDTO1.getId());
        assertThat(stockItemTransactionsDTO1).isEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO2.setId(2L);
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO1.setId(null);
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemTransactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemTransactionsMapper.fromId(null)).isNull();
    }
}
