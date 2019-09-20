package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockItems;
import com.resource.server.repository.StockItemsRepository;
import com.resource.server.service.StockItemsService;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.mapper.StockItemsMapper;
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
 * Test class for the StockItemsResource REST controller.
 *
 * @see StockItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemsResourceIntTest {

    private static final String DEFAULT_STOCK_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_SKU = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Float DEFAULT_RECOMMENDED_RETAIL_PRICE = 1F;
    private static final Float UPDATED_RECOMMENDED_RETAIL_PRICE = 2F;

    private static final Integer DEFAULT_QUANTITY_PER_OUTER = 1;
    private static final Integer UPDATED_QUANTITY_PER_OUTER = 2;

    private static final Float DEFAULT_TYPICAL_WEIGHT_PER_UNIT = 1F;
    private static final Float UPDATED_TYPICAL_WEIGHT_PER_UNIT = 2F;

    private static final Integer DEFAULT_TYPICAL_LENGTH_PER_UNIT = 1;
    private static final Integer UPDATED_TYPICAL_LENGTH_PER_UNIT = 2;

    private static final Integer DEFAULT_TYPICAL_WIDTH_PER_UNIT = 1;
    private static final Integer UPDATED_TYPICAL_WIDTH_PER_UNIT = 2;

    private static final Integer DEFAULT_TYPICAL_HEIGHT_PER_UNIT = 1;
    private static final Integer UPDATED_TYPICAL_HEIGHT_PER_UNIT = 2;

    private static final String DEFAULT_MARKETING_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SELL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SELL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;

    private static final String DEFAULT_CUSTOM_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    @Autowired
    private StockItemsRepository stockItemsRepository;

    @Autowired
    private StockItemsMapper stockItemsMapper;

    @Autowired
    private StockItemsService stockItemsService;

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

    private MockMvc restStockItemsMockMvc;

    private StockItems stockItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemsResource stockItemsResource = new StockItemsResource(stockItemsService);
        this.restStockItemsMockMvc = MockMvcBuilders.standaloneSetup(stockItemsResource)
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
    public static StockItems createEntity(EntityManager em) {
        StockItems stockItems = new StockItems()
            .stockItemName(DEFAULT_STOCK_ITEM_NAME)
            .sellerSKU(DEFAULT_SELLER_SKU)
            .generatedSKU(DEFAULT_GENERATED_SKU)
            .barcode(DEFAULT_BARCODE)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .recommendedRetailPrice(DEFAULT_RECOMMENDED_RETAIL_PRICE)
            .quantityPerOuter(DEFAULT_QUANTITY_PER_OUTER)
            .typicalWeightPerUnit(DEFAULT_TYPICAL_WEIGHT_PER_UNIT)
            .typicalLengthPerUnit(DEFAULT_TYPICAL_LENGTH_PER_UNIT)
            .typicalWidthPerUnit(DEFAULT_TYPICAL_WIDTH_PER_UNIT)
            .typicalHeightPerUnit(DEFAULT_TYPICAL_HEIGHT_PER_UNIT)
            .marketingComments(DEFAULT_MARKETING_COMMENTS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .sellStartDate(DEFAULT_SELL_START_DATE)
            .sellEndDate(DEFAULT_SELL_END_DATE)
            .sellCount(DEFAULT_SELL_COUNT)
            .customFields(DEFAULT_CUSTOM_FIELDS)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL);
        return stockItems;
    }

    @Before
    public void initTest() {
        stockItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItems() throws Exception {
        int databaseSizeBeforeCreate = stockItemsRepository.findAll().size();

        // Create the StockItems
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);
        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItems testStockItems = stockItemsList.get(stockItemsList.size() - 1);
        assertThat(testStockItems.getStockItemName()).isEqualTo(DEFAULT_STOCK_ITEM_NAME);
        assertThat(testStockItems.getSellerSKU()).isEqualTo(DEFAULT_SELLER_SKU);
        assertThat(testStockItems.getGeneratedSKU()).isEqualTo(DEFAULT_GENERATED_SKU);
        assertThat(testStockItems.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testStockItems.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testStockItems.getRecommendedRetailPrice()).isEqualTo(DEFAULT_RECOMMENDED_RETAIL_PRICE);
        assertThat(testStockItems.getQuantityPerOuter()).isEqualTo(DEFAULT_QUANTITY_PER_OUTER);
        assertThat(testStockItems.getTypicalWeightPerUnit()).isEqualTo(DEFAULT_TYPICAL_WEIGHT_PER_UNIT);
        assertThat(testStockItems.getTypicalLengthPerUnit()).isEqualTo(DEFAULT_TYPICAL_LENGTH_PER_UNIT);
        assertThat(testStockItems.getTypicalWidthPerUnit()).isEqualTo(DEFAULT_TYPICAL_WIDTH_PER_UNIT);
        assertThat(testStockItems.getTypicalHeightPerUnit()).isEqualTo(DEFAULT_TYPICAL_HEIGHT_PER_UNIT);
        assertThat(testStockItems.getMarketingComments()).isEqualTo(DEFAULT_MARKETING_COMMENTS);
        assertThat(testStockItems.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testStockItems.getSellStartDate()).isEqualTo(DEFAULT_SELL_START_DATE);
        assertThat(testStockItems.getSellEndDate()).isEqualTo(DEFAULT_SELL_END_DATE);
        assertThat(testStockItems.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
        assertThat(testStockItems.getCustomFields()).isEqualTo(DEFAULT_CUSTOM_FIELDS);
        assertThat(testStockItems.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void createStockItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemsRepository.findAll().size();

        // Create the StockItems with an existing ID
        stockItems.setId(1L);
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStockItemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setStockItemName(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setUnitPrice(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityPerOuterIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setQuantityPerOuter(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList
        restStockItemsMockMvc.perform(get("/api/stock-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockItemName").value(hasItem(DEFAULT_STOCK_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].sellerSKU").value(hasItem(DEFAULT_SELLER_SKU.toString())))
            .andExpect(jsonPath("$.[*].generatedSKU").value(hasItem(DEFAULT_GENERATED_SKU.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].recommendedRetailPrice").value(hasItem(DEFAULT_RECOMMENDED_RETAIL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityPerOuter").value(hasItem(DEFAULT_QUANTITY_PER_OUTER)))
            .andExpect(jsonPath("$.[*].typicalWeightPerUnit").value(hasItem(DEFAULT_TYPICAL_WEIGHT_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].typicalLengthPerUnit").value(hasItem(DEFAULT_TYPICAL_LENGTH_PER_UNIT)))
            .andExpect(jsonPath("$.[*].typicalWidthPerUnit").value(hasItem(DEFAULT_TYPICAL_WIDTH_PER_UNIT)))
            .andExpect(jsonPath("$.[*].typicalHeightPerUnit").value(hasItem(DEFAULT_TYPICAL_HEIGHT_PER_UNIT)))
            .andExpect(jsonPath("$.[*].marketingComments").value(hasItem(DEFAULT_MARKETING_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS.toString())))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get the stockItems
        restStockItemsMockMvc.perform(get("/api/stock-items/{id}", stockItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItems.getId().intValue()))
            .andExpect(jsonPath("$.stockItemName").value(DEFAULT_STOCK_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.sellerSKU").value(DEFAULT_SELLER_SKU.toString()))
            .andExpect(jsonPath("$.generatedSKU").value(DEFAULT_GENERATED_SKU.toString()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.recommendedRetailPrice").value(DEFAULT_RECOMMENDED_RETAIL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantityPerOuter").value(DEFAULT_QUANTITY_PER_OUTER))
            .andExpect(jsonPath("$.typicalWeightPerUnit").value(DEFAULT_TYPICAL_WEIGHT_PER_UNIT.doubleValue()))
            .andExpect(jsonPath("$.typicalLengthPerUnit").value(DEFAULT_TYPICAL_LENGTH_PER_UNIT))
            .andExpect(jsonPath("$.typicalWidthPerUnit").value(DEFAULT_TYPICAL_WIDTH_PER_UNIT))
            .andExpect(jsonPath("$.typicalHeightPerUnit").value(DEFAULT_TYPICAL_HEIGHT_PER_UNIT))
            .andExpect(jsonPath("$.marketingComments").value(DEFAULT_MARKETING_COMMENTS.toString()))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.sellStartDate").value(DEFAULT_SELL_START_DATE.toString()))
            .andExpect(jsonPath("$.sellEndDate").value(DEFAULT_SELL_END_DATE.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.customFields").value(DEFAULT_CUSTOM_FIELDS.toString()))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockItems() throws Exception {
        // Get the stockItems
        restStockItemsMockMvc.perform(get("/api/stock-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        int databaseSizeBeforeUpdate = stockItemsRepository.findAll().size();

        // Update the stockItems
        StockItems updatedStockItems = stockItemsRepository.findById(stockItems.getId()).get();
        // Disconnect from session so that the updates on updatedStockItems are not directly saved in db
        em.detach(updatedStockItems);
        updatedStockItems
            .stockItemName(UPDATED_STOCK_ITEM_NAME)
            .sellerSKU(UPDATED_SELLER_SKU)
            .generatedSKU(UPDATED_GENERATED_SKU)
            .barcode(UPDATED_BARCODE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .recommendedRetailPrice(UPDATED_RECOMMENDED_RETAIL_PRICE)
            .quantityPerOuter(UPDATED_QUANTITY_PER_OUTER)
            .typicalWeightPerUnit(UPDATED_TYPICAL_WEIGHT_PER_UNIT)
            .typicalLengthPerUnit(UPDATED_TYPICAL_LENGTH_PER_UNIT)
            .typicalWidthPerUnit(UPDATED_TYPICAL_WIDTH_PER_UNIT)
            .typicalHeightPerUnit(UPDATED_TYPICAL_HEIGHT_PER_UNIT)
            .marketingComments(UPDATED_MARKETING_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .sellCount(UPDATED_SELL_COUNT)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(updatedStockItems);

        restStockItemsMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeUpdate);
        StockItems testStockItems = stockItemsList.get(stockItemsList.size() - 1);
        assertThat(testStockItems.getStockItemName()).isEqualTo(UPDATED_STOCK_ITEM_NAME);
        assertThat(testStockItems.getSellerSKU()).isEqualTo(UPDATED_SELLER_SKU);
        assertThat(testStockItems.getGeneratedSKU()).isEqualTo(UPDATED_GENERATED_SKU);
        assertThat(testStockItems.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testStockItems.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testStockItems.getRecommendedRetailPrice()).isEqualTo(UPDATED_RECOMMENDED_RETAIL_PRICE);
        assertThat(testStockItems.getQuantityPerOuter()).isEqualTo(UPDATED_QUANTITY_PER_OUTER);
        assertThat(testStockItems.getTypicalWeightPerUnit()).isEqualTo(UPDATED_TYPICAL_WEIGHT_PER_UNIT);
        assertThat(testStockItems.getTypicalLengthPerUnit()).isEqualTo(UPDATED_TYPICAL_LENGTH_PER_UNIT);
        assertThat(testStockItems.getTypicalWidthPerUnit()).isEqualTo(UPDATED_TYPICAL_WIDTH_PER_UNIT);
        assertThat(testStockItems.getTypicalHeightPerUnit()).isEqualTo(UPDATED_TYPICAL_HEIGHT_PER_UNIT);
        assertThat(testStockItems.getMarketingComments()).isEqualTo(UPDATED_MARKETING_COMMENTS);
        assertThat(testStockItems.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testStockItems.getSellStartDate()).isEqualTo(UPDATED_SELL_START_DATE);
        assertThat(testStockItems.getSellEndDate()).isEqualTo(UPDATED_SELL_END_DATE);
        assertThat(testStockItems.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
        assertThat(testStockItems.getCustomFields()).isEqualTo(UPDATED_CUSTOM_FIELDS);
        assertThat(testStockItems.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItems() throws Exception {
        int databaseSizeBeforeUpdate = stockItemsRepository.findAll().size();

        // Create the StockItems
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemsMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        int databaseSizeBeforeDelete = stockItemsRepository.findAll().size();

        // Delete the stockItems
        restStockItemsMockMvc.perform(delete("/api/stock-items/{id}", stockItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItems.class);
        StockItems stockItems1 = new StockItems();
        stockItems1.setId(1L);
        StockItems stockItems2 = new StockItems();
        stockItems2.setId(stockItems1.getId());
        assertThat(stockItems1).isEqualTo(stockItems2);
        stockItems2.setId(2L);
        assertThat(stockItems1).isNotEqualTo(stockItems2);
        stockItems1.setId(null);
        assertThat(stockItems1).isNotEqualTo(stockItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsDTO.class);
        StockItemsDTO stockItemsDTO1 = new StockItemsDTO();
        stockItemsDTO1.setId(1L);
        StockItemsDTO stockItemsDTO2 = new StockItemsDTO();
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
        stockItemsDTO2.setId(stockItemsDTO1.getId());
        assertThat(stockItemsDTO1).isEqualTo(stockItemsDTO2);
        stockItemsDTO2.setId(2L);
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
        stockItemsDTO1.setId(null);
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemsMapper.fromId(null)).isNull();
    }
}
