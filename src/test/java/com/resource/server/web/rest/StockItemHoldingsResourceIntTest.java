package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockItemHoldings;
import com.resource.server.repository.StockItemHoldingsRepository;
import com.resource.server.service.StockItemHoldingsService;
import com.resource.server.service.dto.StockItemHoldingsDTO;
import com.resource.server.service.mapper.StockItemHoldingsMapper;
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
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockItemHoldingsResource REST controller.
 *
 * @see StockItemHoldingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemHoldingsResourceIntTest {

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;

    private static final String DEFAULT_BIN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_BIN_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAST_STOCKTAKE_QUANTITY = 1;
    private static final Integer UPDATED_LAST_STOCKTAKE_QUANTITY = 2;

    private static final BigDecimal DEFAULT_LAST_COST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_COST_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_REORDER_LEVEL = 1;
    private static final Integer UPDATED_REORDER_LEVEL = 2;

    private static final Integer DEFAULT_TARGER_STOCK_LEVEL = 1;
    private static final Integer UPDATED_TARGER_STOCK_LEVEL = 2;

    @Autowired
    private StockItemHoldingsRepository stockItemHoldingsRepository;

    @Autowired
    private StockItemHoldingsMapper stockItemHoldingsMapper;

    @Autowired
    private StockItemHoldingsService stockItemHoldingsService;

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

    private MockMvc restStockItemHoldingsMockMvc;

    private StockItemHoldings stockItemHoldings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemHoldingsResource stockItemHoldingsResource = new StockItemHoldingsResource(stockItemHoldingsService);
        this.restStockItemHoldingsMockMvc = MockMvcBuilders.standaloneSetup(stockItemHoldingsResource)
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
    public static StockItemHoldings createEntity(EntityManager em) {
        StockItemHoldings stockItemHoldings = new StockItemHoldings()
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .binLocation(DEFAULT_BIN_LOCATION)
            .lastStocktakeQuantity(DEFAULT_LAST_STOCKTAKE_QUANTITY)
            .lastCostPrice(DEFAULT_LAST_COST_PRICE)
            .reorderLevel(DEFAULT_REORDER_LEVEL)
            .targerStockLevel(DEFAULT_TARGER_STOCK_LEVEL);
        return stockItemHoldings;
    }

    @Before
    public void initTest() {
        stockItemHoldings = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemHoldings() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(DEFAULT_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStocktakeQuantity()).isEqualTo(DEFAULT_LAST_STOCKTAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(DEFAULT_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(DEFAULT_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(DEFAULT_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void createStockItemHoldingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings with an existing ID
        stockItemHoldings.setId(1L);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityOnHandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setQuantityOnHand(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBinLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setBinLocation(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastStocktakeQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastStocktakeQuantity(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastCostPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastCostPrice(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReorderLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setReorderLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargerStockLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setTargerStockLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemHoldings.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].binLocation").value(hasItem(DEFAULT_BIN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].lastStocktakeQuantity").value(hasItem(DEFAULT_LAST_STOCKTAKE_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastCostPrice").value(hasItem(DEFAULT_LAST_COST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targerStockLevel").value(hasItem(DEFAULT_TARGER_STOCK_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", stockItemHoldings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemHoldings.getId().intValue()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.binLocation").value(DEFAULT_BIN_LOCATION.toString()))
            .andExpect(jsonPath("$.lastStocktakeQuantity").value(DEFAULT_LAST_STOCKTAKE_QUANTITY))
            .andExpect(jsonPath("$.lastCostPrice").value(DEFAULT_LAST_COST_PRICE.intValue()))
            .andExpect(jsonPath("$.reorderLevel").value(DEFAULT_REORDER_LEVEL))
            .andExpect(jsonPath("$.targerStockLevel").value(DEFAULT_TARGER_STOCK_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemHoldings() throws Exception {
        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Update the stockItemHoldings
        StockItemHoldings updatedStockItemHoldings = stockItemHoldingsRepository.findById(stockItemHoldings.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemHoldings are not directly saved in db
        em.detach(updatedStockItemHoldings);
        updatedStockItemHoldings
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .binLocation(UPDATED_BIN_LOCATION)
            .lastStocktakeQuantity(UPDATED_LAST_STOCKTAKE_QUANTITY)
            .lastCostPrice(UPDATED_LAST_COST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targerStockLevel(UPDATED_TARGER_STOCK_LEVEL);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(updatedStockItemHoldings);

        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(UPDATED_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStocktakeQuantity()).isEqualTo(UPDATED_LAST_STOCKTAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(UPDATED_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(UPDATED_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemHoldings() throws Exception {
        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeDelete = stockItemHoldingsRepository.findAll().size();

        // Delete the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(delete("/api/stock-item-holdings/{id}", stockItemHoldings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemHoldings.class);
        StockItemHoldings stockItemHoldings1 = new StockItemHoldings();
        stockItemHoldings1.setId(1L);
        StockItemHoldings stockItemHoldings2 = new StockItemHoldings();
        stockItemHoldings2.setId(stockItemHoldings1.getId());
        assertThat(stockItemHoldings1).isEqualTo(stockItemHoldings2);
        stockItemHoldings2.setId(2L);
        assertThat(stockItemHoldings1).isNotEqualTo(stockItemHoldings2);
        stockItemHoldings1.setId(null);
        assertThat(stockItemHoldings1).isNotEqualTo(stockItemHoldings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemHoldingsDTO.class);
        StockItemHoldingsDTO stockItemHoldingsDTO1 = new StockItemHoldingsDTO();
        stockItemHoldingsDTO1.setId(1L);
        StockItemHoldingsDTO stockItemHoldingsDTO2 = new StockItemHoldingsDTO();
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO2.setId(stockItemHoldingsDTO1.getId());
        assertThat(stockItemHoldingsDTO1).isEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO2.setId(2L);
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO1.setId(null);
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemHoldingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemHoldingsMapper.fromId(null)).isNull();
    }
}
