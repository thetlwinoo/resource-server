package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Products;
import com.resource.server.domain.ReviewLines;
import com.resource.server.domain.PackageTypes;
import com.resource.server.domain.Suppliers;
import com.resource.server.domain.Merchants;
import com.resource.server.domain.ProductSubCategory;
import com.resource.server.domain.UnitMeasure;
import com.resource.server.domain.ProductModel;
import com.resource.server.repository.ProductsRepository;
import com.resource.server.service.ProductsService;
import com.resource.server.service.dto.ProductsDTO;
import com.resource.server.service.mapper.ProductsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductsCriteria;
import com.resource.server.service.ProductsQueryService;

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
 * Test class for the ProductsResource REST controller.
 *
 * @see ProductsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductsResourceIntTest {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAKE_FLAG = false;
    private static final Boolean UPDATED_MAKE_FLAG = true;

    private static final Boolean DEFAULT_FINISHED_GOODS_FLAG = false;
    private static final Boolean UPDATED_FINISHED_GOODS_FLAG = true;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_SAFETY_STOCK_LEVEL = 1;
    private static final Integer UPDATED_SAFETY_STOCK_LEVEL = 2;

    private static final Integer DEFAULT_REORDER_POINT = 1;
    private static final Integer UPDATED_REORDER_POINT = 2;

    private static final Float DEFAULT_STANDARD_COST = 1F;
    private static final Float UPDATED_STANDARD_COST = 2F;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Float DEFAULT_RECOMMENDED_RETAIL_PRICE = 1F;
    private static final Float UPDATED_RECOMMENDED_RETAIL_PRICE = 2F;

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFY_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFY_SIZE = "BBBBBBBBBB";

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Integer DEFAULT_DAYS_TO_MANUFACTURE = 1;
    private static final Integer UPDATED_DAYS_TO_MANUFACTURE = 2;

    private static final String DEFAULT_PRODUCT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SELL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SELL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MARKETING_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DISCONTINUED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISCONTINUED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductsQueryService productsQueryService;

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

    private MockMvc restProductsMockMvc;

    private Products products;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductsResource productsResource = new ProductsResource(productsService, productsQueryService);
        this.restProductsMockMvc = MockMvcBuilders.standaloneSetup(productsResource)
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
    public static Products createEntity(EntityManager em) {
        Products products = new Products()
            .productName(DEFAULT_PRODUCT_NAME)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .searchDetails(DEFAULT_SEARCH_DETAILS)
            .makeFlag(DEFAULT_MAKE_FLAG)
            .finishedGoodsFlag(DEFAULT_FINISHED_GOODS_FLAG)
            .color(DEFAULT_COLOR)
            .safetyStockLevel(DEFAULT_SAFETY_STOCK_LEVEL)
            .reorderPoint(DEFAULT_REORDER_POINT)
            .standardCost(DEFAULT_STANDARD_COST)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .recommendedRetailPrice(DEFAULT_RECOMMENDED_RETAIL_PRICE)
            .brand(DEFAULT_BRAND)
            .specifySize(DEFAULT_SPECIFY_SIZE)
            .weight(DEFAULT_WEIGHT)
            .daysToManufacture(DEFAULT_DAYS_TO_MANUFACTURE)
            .productLine(DEFAULT_PRODUCT_LINE)
            .classType(DEFAULT_CLASS_TYPE)
            .style(DEFAULT_STYLE)
            .customFields(DEFAULT_CUSTOM_FIELDS)
            .photo(DEFAULT_PHOTO)
            .sellStartDate(DEFAULT_SELL_START_DATE)
            .sellEndDate(DEFAULT_SELL_END_DATE)
            .marketingComments(DEFAULT_MARKETING_COMMENTS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .discontinuedDate(DEFAULT_DISCONTINUED_DATE)
            .sellCount(DEFAULT_SELL_COUNT);
        return products;
    }

    @Before
    public void initTest() {
        products = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);
        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProducts.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(DEFAULT_SEARCH_DETAILS);
        assertThat(testProducts.isMakeFlag()).isEqualTo(DEFAULT_MAKE_FLAG);
        assertThat(testProducts.isFinishedGoodsFlag()).isEqualTo(DEFAULT_FINISHED_GOODS_FLAG);
        assertThat(testProducts.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProducts.getSafetyStockLevel()).isEqualTo(DEFAULT_SAFETY_STOCK_LEVEL);
        assertThat(testProducts.getReorderPoint()).isEqualTo(DEFAULT_REORDER_POINT);
        assertThat(testProducts.getStandardCost()).isEqualTo(DEFAULT_STANDARD_COST);
        assertThat(testProducts.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testProducts.getRecommendedRetailPrice()).isEqualTo(DEFAULT_RECOMMENDED_RETAIL_PRICE);
        assertThat(testProducts.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProducts.getSpecifySize()).isEqualTo(DEFAULT_SPECIFY_SIZE);
        assertThat(testProducts.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProducts.getDaysToManufacture()).isEqualTo(DEFAULT_DAYS_TO_MANUFACTURE);
        assertThat(testProducts.getProductLine()).isEqualTo(DEFAULT_PRODUCT_LINE);
        assertThat(testProducts.getClassType()).isEqualTo(DEFAULT_CLASS_TYPE);
        assertThat(testProducts.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testProducts.getCustomFields()).isEqualTo(DEFAULT_CUSTOM_FIELDS);
        assertThat(testProducts.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProducts.getSellStartDate()).isEqualTo(DEFAULT_SELL_START_DATE);
        assertThat(testProducts.getSellEndDate()).isEqualTo(DEFAULT_SELL_END_DATE);
        assertThat(testProducts.getMarketingComments()).isEqualTo(DEFAULT_MARKETING_COMMENTS);
        assertThat(testProducts.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testProducts.getDiscontinuedDate()).isEqualTo(DEFAULT_DISCONTINUED_DATE);
        assertThat(testProducts.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
    }

    @Test
    @Transactional
    public void createProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products with an existing ID
        products.setId(1L);
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setProductName(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSearchDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setSearchDetails(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMakeFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setMakeFlag(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinishedGoodsFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setFinishedGoodsFlag(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSafetyStockLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setSafetyStockLevel(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReorderPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setReorderPoint(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStandardCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setStandardCost(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setUnitPrice(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaysToManufactureIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setDaysToManufacture(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setSellStartDate(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList
        restProductsMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].makeFlag").value(hasItem(DEFAULT_MAKE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].finishedGoodsFlag").value(hasItem(DEFAULT_FINISHED_GOODS_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].safetyStockLevel").value(hasItem(DEFAULT_SAFETY_STOCK_LEVEL)))
            .andExpect(jsonPath("$.[*].reorderPoint").value(hasItem(DEFAULT_REORDER_POINT)))
            .andExpect(jsonPath("$.[*].standardCost").value(hasItem(DEFAULT_STANDARD_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].recommendedRetailPrice").value(hasItem(DEFAULT_RECOMMENDED_RETAIL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].specifySize").value(hasItem(DEFAULT_SPECIFY_SIZE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].daysToManufacture").value(hasItem(DEFAULT_DAYS_TO_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].productLine").value(hasItem(DEFAULT_PRODUCT_LINE.toString())))
            .andExpect(jsonPath("$.[*].classType").value(hasItem(DEFAULT_CLASS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].marketingComments").value(hasItem(DEFAULT_MARKETING_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].discontinuedDate").value(hasItem(DEFAULT_DISCONTINUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)));
    }
    
    @Test
    @Transactional
    public void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", products.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(products.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER.toString()))
            .andExpect(jsonPath("$.searchDetails").value(DEFAULT_SEARCH_DETAILS.toString()))
            .andExpect(jsonPath("$.makeFlag").value(DEFAULT_MAKE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.finishedGoodsFlag").value(DEFAULT_FINISHED_GOODS_FLAG.booleanValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.safetyStockLevel").value(DEFAULT_SAFETY_STOCK_LEVEL))
            .andExpect(jsonPath("$.reorderPoint").value(DEFAULT_REORDER_POINT))
            .andExpect(jsonPath("$.standardCost").value(DEFAULT_STANDARD_COST.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.recommendedRetailPrice").value(DEFAULT_RECOMMENDED_RETAIL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.specifySize").value(DEFAULT_SPECIFY_SIZE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.daysToManufacture").value(DEFAULT_DAYS_TO_MANUFACTURE))
            .andExpect(jsonPath("$.productLine").value(DEFAULT_PRODUCT_LINE.toString()))
            .andExpect(jsonPath("$.classType").value(DEFAULT_CLASS_TYPE.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()))
            .andExpect(jsonPath("$.customFields").value(DEFAULT_CUSTOM_FIELDS.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.sellStartDate").value(DEFAULT_SELL_START_DATE.toString()))
            .andExpect(jsonPath("$.sellEndDate").value(DEFAULT_SELL_END_DATE.toString()))
            .andExpect(jsonPath("$.marketingComments").value(DEFAULT_MARKETING_COMMENTS.toString()))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.discontinuedDate").value(DEFAULT_DISCONTINUED_DATE.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT));
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productsList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productsList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName is not null
        defaultProductsShouldBeFound("productName.specified=true");

        // Get all the productsList where productName is null
        defaultProductsShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber equals to DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.equals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.equals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber in DEFAULT_PRODUCT_NUMBER or UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.in=" + DEFAULT_PRODUCT_NUMBER + "," + UPDATED_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.in=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber is not null
        defaultProductsShouldBeFound("productNumber.specified=true");

        // Get all the productsList where productNumber is null
        defaultProductsShouldNotBeFound("productNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails equals to DEFAULT_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.equals=" + DEFAULT_SEARCH_DETAILS);

        // Get all the productsList where searchDetails equals to UPDATED_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.equals=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails in DEFAULT_SEARCH_DETAILS or UPDATED_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.in=" + DEFAULT_SEARCH_DETAILS + "," + UPDATED_SEARCH_DETAILS);

        // Get all the productsList where searchDetails equals to UPDATED_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.in=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails is not null
        defaultProductsShouldBeFound("searchDetails.specified=true");

        // Get all the productsList where searchDetails is null
        defaultProductsShouldNotBeFound("searchDetails.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByMakeFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where makeFlag equals to DEFAULT_MAKE_FLAG
        defaultProductsShouldBeFound("makeFlag.equals=" + DEFAULT_MAKE_FLAG);

        // Get all the productsList where makeFlag equals to UPDATED_MAKE_FLAG
        defaultProductsShouldNotBeFound("makeFlag.equals=" + UPDATED_MAKE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductsByMakeFlagIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where makeFlag in DEFAULT_MAKE_FLAG or UPDATED_MAKE_FLAG
        defaultProductsShouldBeFound("makeFlag.in=" + DEFAULT_MAKE_FLAG + "," + UPDATED_MAKE_FLAG);

        // Get all the productsList where makeFlag equals to UPDATED_MAKE_FLAG
        defaultProductsShouldNotBeFound("makeFlag.in=" + UPDATED_MAKE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductsByMakeFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where makeFlag is not null
        defaultProductsShouldBeFound("makeFlag.specified=true");

        // Get all the productsList where makeFlag is null
        defaultProductsShouldNotBeFound("makeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByFinishedGoodsFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where finishedGoodsFlag equals to DEFAULT_FINISHED_GOODS_FLAG
        defaultProductsShouldBeFound("finishedGoodsFlag.equals=" + DEFAULT_FINISHED_GOODS_FLAG);

        // Get all the productsList where finishedGoodsFlag equals to UPDATED_FINISHED_GOODS_FLAG
        defaultProductsShouldNotBeFound("finishedGoodsFlag.equals=" + UPDATED_FINISHED_GOODS_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductsByFinishedGoodsFlagIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where finishedGoodsFlag in DEFAULT_FINISHED_GOODS_FLAG or UPDATED_FINISHED_GOODS_FLAG
        defaultProductsShouldBeFound("finishedGoodsFlag.in=" + DEFAULT_FINISHED_GOODS_FLAG + "," + UPDATED_FINISHED_GOODS_FLAG);

        // Get all the productsList where finishedGoodsFlag equals to UPDATED_FINISHED_GOODS_FLAG
        defaultProductsShouldNotBeFound("finishedGoodsFlag.in=" + UPDATED_FINISHED_GOODS_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductsByFinishedGoodsFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where finishedGoodsFlag is not null
        defaultProductsShouldBeFound("finishedGoodsFlag.specified=true");

        // Get all the productsList where finishedGoodsFlag is null
        defaultProductsShouldNotBeFound("finishedGoodsFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where color equals to DEFAULT_COLOR
        defaultProductsShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the productsList where color equals to UPDATED_COLOR
        defaultProductsShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultProductsShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the productsList where color equals to UPDATED_COLOR
        defaultProductsShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where color is not null
        defaultProductsShouldBeFound("color.specified=true");

        // Get all the productsList where color is null
        defaultProductsShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySafetyStockLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where safetyStockLevel equals to DEFAULT_SAFETY_STOCK_LEVEL
        defaultProductsShouldBeFound("safetyStockLevel.equals=" + DEFAULT_SAFETY_STOCK_LEVEL);

        // Get all the productsList where safetyStockLevel equals to UPDATED_SAFETY_STOCK_LEVEL
        defaultProductsShouldNotBeFound("safetyStockLevel.equals=" + UPDATED_SAFETY_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllProductsBySafetyStockLevelIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where safetyStockLevel in DEFAULT_SAFETY_STOCK_LEVEL or UPDATED_SAFETY_STOCK_LEVEL
        defaultProductsShouldBeFound("safetyStockLevel.in=" + DEFAULT_SAFETY_STOCK_LEVEL + "," + UPDATED_SAFETY_STOCK_LEVEL);

        // Get all the productsList where safetyStockLevel equals to UPDATED_SAFETY_STOCK_LEVEL
        defaultProductsShouldNotBeFound("safetyStockLevel.in=" + UPDATED_SAFETY_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllProductsBySafetyStockLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where safetyStockLevel is not null
        defaultProductsShouldBeFound("safetyStockLevel.specified=true");

        // Get all the productsList where safetyStockLevel is null
        defaultProductsShouldNotBeFound("safetyStockLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySafetyStockLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where safetyStockLevel greater than or equals to DEFAULT_SAFETY_STOCK_LEVEL
        defaultProductsShouldBeFound("safetyStockLevel.greaterOrEqualThan=" + DEFAULT_SAFETY_STOCK_LEVEL);

        // Get all the productsList where safetyStockLevel greater than or equals to UPDATED_SAFETY_STOCK_LEVEL
        defaultProductsShouldNotBeFound("safetyStockLevel.greaterOrEqualThan=" + UPDATED_SAFETY_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllProductsBySafetyStockLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where safetyStockLevel less than or equals to DEFAULT_SAFETY_STOCK_LEVEL
        defaultProductsShouldNotBeFound("safetyStockLevel.lessThan=" + DEFAULT_SAFETY_STOCK_LEVEL);

        // Get all the productsList where safetyStockLevel less than or equals to UPDATED_SAFETY_STOCK_LEVEL
        defaultProductsShouldBeFound("safetyStockLevel.lessThan=" + UPDATED_SAFETY_STOCK_LEVEL);
    }


    @Test
    @Transactional
    public void getAllProductsByReorderPointIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where reorderPoint equals to DEFAULT_REORDER_POINT
        defaultProductsShouldBeFound("reorderPoint.equals=" + DEFAULT_REORDER_POINT);

        // Get all the productsList where reorderPoint equals to UPDATED_REORDER_POINT
        defaultProductsShouldNotBeFound("reorderPoint.equals=" + UPDATED_REORDER_POINT);
    }

    @Test
    @Transactional
    public void getAllProductsByReorderPointIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where reorderPoint in DEFAULT_REORDER_POINT or UPDATED_REORDER_POINT
        defaultProductsShouldBeFound("reorderPoint.in=" + DEFAULT_REORDER_POINT + "," + UPDATED_REORDER_POINT);

        // Get all the productsList where reorderPoint equals to UPDATED_REORDER_POINT
        defaultProductsShouldNotBeFound("reorderPoint.in=" + UPDATED_REORDER_POINT);
    }

    @Test
    @Transactional
    public void getAllProductsByReorderPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where reorderPoint is not null
        defaultProductsShouldBeFound("reorderPoint.specified=true");

        // Get all the productsList where reorderPoint is null
        defaultProductsShouldNotBeFound("reorderPoint.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByReorderPointIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where reorderPoint greater than or equals to DEFAULT_REORDER_POINT
        defaultProductsShouldBeFound("reorderPoint.greaterOrEqualThan=" + DEFAULT_REORDER_POINT);

        // Get all the productsList where reorderPoint greater than or equals to UPDATED_REORDER_POINT
        defaultProductsShouldNotBeFound("reorderPoint.greaterOrEqualThan=" + UPDATED_REORDER_POINT);
    }

    @Test
    @Transactional
    public void getAllProductsByReorderPointIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where reorderPoint less than or equals to DEFAULT_REORDER_POINT
        defaultProductsShouldNotBeFound("reorderPoint.lessThan=" + DEFAULT_REORDER_POINT);

        // Get all the productsList where reorderPoint less than or equals to UPDATED_REORDER_POINT
        defaultProductsShouldBeFound("reorderPoint.lessThan=" + UPDATED_REORDER_POINT);
    }


    @Test
    @Transactional
    public void getAllProductsByStandardCostIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where standardCost equals to DEFAULT_STANDARD_COST
        defaultProductsShouldBeFound("standardCost.equals=" + DEFAULT_STANDARD_COST);

        // Get all the productsList where standardCost equals to UPDATED_STANDARD_COST
        defaultProductsShouldNotBeFound("standardCost.equals=" + UPDATED_STANDARD_COST);
    }

    @Test
    @Transactional
    public void getAllProductsByStandardCostIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where standardCost in DEFAULT_STANDARD_COST or UPDATED_STANDARD_COST
        defaultProductsShouldBeFound("standardCost.in=" + DEFAULT_STANDARD_COST + "," + UPDATED_STANDARD_COST);

        // Get all the productsList where standardCost equals to UPDATED_STANDARD_COST
        defaultProductsShouldNotBeFound("standardCost.in=" + UPDATED_STANDARD_COST);
    }

    @Test
    @Transactional
    public void getAllProductsByStandardCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where standardCost is not null
        defaultProductsShouldBeFound("standardCost.specified=true");

        // Get all the productsList where standardCost is null
        defaultProductsShouldNotBeFound("standardCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultProductsShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the productsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultProductsShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultProductsShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the productsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultProductsShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where unitPrice is not null
        defaultProductsShouldBeFound("unitPrice.specified=true");

        // Get all the productsList where unitPrice is null
        defaultProductsShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByRecommendedRetailPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where recommendedRetailPrice equals to DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultProductsShouldBeFound("recommendedRetailPrice.equals=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the productsList where recommendedRetailPrice equals to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultProductsShouldNotBeFound("recommendedRetailPrice.equals=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByRecommendedRetailPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where recommendedRetailPrice in DEFAULT_RECOMMENDED_RETAIL_PRICE or UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultProductsShouldBeFound("recommendedRetailPrice.in=" + DEFAULT_RECOMMENDED_RETAIL_PRICE + "," + UPDATED_RECOMMENDED_RETAIL_PRICE);

        // Get all the productsList where recommendedRetailPrice equals to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultProductsShouldNotBeFound("recommendedRetailPrice.in=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByRecommendedRetailPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where recommendedRetailPrice is not null
        defaultProductsShouldBeFound("recommendedRetailPrice.specified=true");

        // Get all the productsList where recommendedRetailPrice is null
        defaultProductsShouldNotBeFound("recommendedRetailPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where brand equals to DEFAULT_BRAND
        defaultProductsShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the productsList where brand equals to UPDATED_BRAND
        defaultProductsShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultProductsShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the productsList where brand equals to UPDATED_BRAND
        defaultProductsShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where brand is not null
        defaultProductsShouldBeFound("brand.specified=true");

        // Get all the productsList where brand is null
        defaultProductsShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySpecifySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where specifySize equals to DEFAULT_SPECIFY_SIZE
        defaultProductsShouldBeFound("specifySize.equals=" + DEFAULT_SPECIFY_SIZE);

        // Get all the productsList where specifySize equals to UPDATED_SPECIFY_SIZE
        defaultProductsShouldNotBeFound("specifySize.equals=" + UPDATED_SPECIFY_SIZE);
    }

    @Test
    @Transactional
    public void getAllProductsBySpecifySizeIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where specifySize in DEFAULT_SPECIFY_SIZE or UPDATED_SPECIFY_SIZE
        defaultProductsShouldBeFound("specifySize.in=" + DEFAULT_SPECIFY_SIZE + "," + UPDATED_SPECIFY_SIZE);

        // Get all the productsList where specifySize equals to UPDATED_SPECIFY_SIZE
        defaultProductsShouldNotBeFound("specifySize.in=" + UPDATED_SPECIFY_SIZE);
    }

    @Test
    @Transactional
    public void getAllProductsBySpecifySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where specifySize is not null
        defaultProductsShouldBeFound("specifySize.specified=true");

        // Get all the productsList where specifySize is null
        defaultProductsShouldNotBeFound("specifySize.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where weight equals to DEFAULT_WEIGHT
        defaultProductsShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the productsList where weight equals to UPDATED_WEIGHT
        defaultProductsShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultProductsShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the productsList where weight equals to UPDATED_WEIGHT
        defaultProductsShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where weight is not null
        defaultProductsShouldBeFound("weight.specified=true");

        // Get all the productsList where weight is null
        defaultProductsShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDaysToManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where daysToManufacture equals to DEFAULT_DAYS_TO_MANUFACTURE
        defaultProductsShouldBeFound("daysToManufacture.equals=" + DEFAULT_DAYS_TO_MANUFACTURE);

        // Get all the productsList where daysToManufacture equals to UPDATED_DAYS_TO_MANUFACTURE
        defaultProductsShouldNotBeFound("daysToManufacture.equals=" + UPDATED_DAYS_TO_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllProductsByDaysToManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where daysToManufacture in DEFAULT_DAYS_TO_MANUFACTURE or UPDATED_DAYS_TO_MANUFACTURE
        defaultProductsShouldBeFound("daysToManufacture.in=" + DEFAULT_DAYS_TO_MANUFACTURE + "," + UPDATED_DAYS_TO_MANUFACTURE);

        // Get all the productsList where daysToManufacture equals to UPDATED_DAYS_TO_MANUFACTURE
        defaultProductsShouldNotBeFound("daysToManufacture.in=" + UPDATED_DAYS_TO_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllProductsByDaysToManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where daysToManufacture is not null
        defaultProductsShouldBeFound("daysToManufacture.specified=true");

        // Get all the productsList where daysToManufacture is null
        defaultProductsShouldNotBeFound("daysToManufacture.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDaysToManufactureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where daysToManufacture greater than or equals to DEFAULT_DAYS_TO_MANUFACTURE
        defaultProductsShouldBeFound("daysToManufacture.greaterOrEqualThan=" + DEFAULT_DAYS_TO_MANUFACTURE);

        // Get all the productsList where daysToManufacture greater than or equals to UPDATED_DAYS_TO_MANUFACTURE
        defaultProductsShouldNotBeFound("daysToManufacture.greaterOrEqualThan=" + UPDATED_DAYS_TO_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllProductsByDaysToManufactureIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where daysToManufacture less than or equals to DEFAULT_DAYS_TO_MANUFACTURE
        defaultProductsShouldNotBeFound("daysToManufacture.lessThan=" + DEFAULT_DAYS_TO_MANUFACTURE);

        // Get all the productsList where daysToManufacture less than or equals to UPDATED_DAYS_TO_MANUFACTURE
        defaultProductsShouldBeFound("daysToManufacture.lessThan=" + UPDATED_DAYS_TO_MANUFACTURE);
    }


    @Test
    @Transactional
    public void getAllProductsByProductLineIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productLine equals to DEFAULT_PRODUCT_LINE
        defaultProductsShouldBeFound("productLine.equals=" + DEFAULT_PRODUCT_LINE);

        // Get all the productsList where productLine equals to UPDATED_PRODUCT_LINE
        defaultProductsShouldNotBeFound("productLine.equals=" + UPDATED_PRODUCT_LINE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductLineIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productLine in DEFAULT_PRODUCT_LINE or UPDATED_PRODUCT_LINE
        defaultProductsShouldBeFound("productLine.in=" + DEFAULT_PRODUCT_LINE + "," + UPDATED_PRODUCT_LINE);

        // Get all the productsList where productLine equals to UPDATED_PRODUCT_LINE
        defaultProductsShouldNotBeFound("productLine.in=" + UPDATED_PRODUCT_LINE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productLine is not null
        defaultProductsShouldBeFound("productLine.specified=true");

        // Get all the productsList where productLine is null
        defaultProductsShouldNotBeFound("productLine.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByClassTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where classType equals to DEFAULT_CLASS_TYPE
        defaultProductsShouldBeFound("classType.equals=" + DEFAULT_CLASS_TYPE);

        // Get all the productsList where classType equals to UPDATED_CLASS_TYPE
        defaultProductsShouldNotBeFound("classType.equals=" + UPDATED_CLASS_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByClassTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where classType in DEFAULT_CLASS_TYPE or UPDATED_CLASS_TYPE
        defaultProductsShouldBeFound("classType.in=" + DEFAULT_CLASS_TYPE + "," + UPDATED_CLASS_TYPE);

        // Get all the productsList where classType equals to UPDATED_CLASS_TYPE
        defaultProductsShouldNotBeFound("classType.in=" + UPDATED_CLASS_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByClassTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where classType is not null
        defaultProductsShouldBeFound("classType.specified=true");

        // Get all the productsList where classType is null
        defaultProductsShouldNotBeFound("classType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where style equals to DEFAULT_STYLE
        defaultProductsShouldBeFound("style.equals=" + DEFAULT_STYLE);

        // Get all the productsList where style equals to UPDATED_STYLE
        defaultProductsShouldNotBeFound("style.equals=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductsByStyleIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where style in DEFAULT_STYLE or UPDATED_STYLE
        defaultProductsShouldBeFound("style.in=" + DEFAULT_STYLE + "," + UPDATED_STYLE);

        // Get all the productsList where style equals to UPDATED_STYLE
        defaultProductsShouldNotBeFound("style.in=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductsByStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where style is not null
        defaultProductsShouldBeFound("style.specified=true");

        // Get all the productsList where style is null
        defaultProductsShouldNotBeFound("style.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByCustomFieldsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where customFields equals to DEFAULT_CUSTOM_FIELDS
        defaultProductsShouldBeFound("customFields.equals=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the productsList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultProductsShouldNotBeFound("customFields.equals=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllProductsByCustomFieldsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where customFields in DEFAULT_CUSTOM_FIELDS or UPDATED_CUSTOM_FIELDS
        defaultProductsShouldBeFound("customFields.in=" + DEFAULT_CUSTOM_FIELDS + "," + UPDATED_CUSTOM_FIELDS);

        // Get all the productsList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultProductsShouldNotBeFound("customFields.in=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllProductsByCustomFieldsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where customFields is not null
        defaultProductsShouldBeFound("customFields.specified=true");

        // Get all the productsList where customFields is null
        defaultProductsShouldNotBeFound("customFields.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where photo equals to DEFAULT_PHOTO
        defaultProductsShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the productsList where photo equals to UPDATED_PHOTO
        defaultProductsShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductsByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultProductsShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the productsList where photo equals to UPDATED_PHOTO
        defaultProductsShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductsByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where photo is not null
        defaultProductsShouldBeFound("photo.specified=true");

        // Get all the productsList where photo is null
        defaultProductsShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellStartDate equals to DEFAULT_SELL_START_DATE
        defaultProductsShouldBeFound("sellStartDate.equals=" + DEFAULT_SELL_START_DATE);

        // Get all the productsList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultProductsShouldNotBeFound("sellStartDate.equals=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellStartDate in DEFAULT_SELL_START_DATE or UPDATED_SELL_START_DATE
        defaultProductsShouldBeFound("sellStartDate.in=" + DEFAULT_SELL_START_DATE + "," + UPDATED_SELL_START_DATE);

        // Get all the productsList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultProductsShouldNotBeFound("sellStartDate.in=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellStartDate is not null
        defaultProductsShouldBeFound("sellStartDate.specified=true");

        // Get all the productsList where sellStartDate is null
        defaultProductsShouldNotBeFound("sellStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellStartDate greater than or equals to DEFAULT_SELL_START_DATE
        defaultProductsShouldBeFound("sellStartDate.greaterOrEqualThan=" + DEFAULT_SELL_START_DATE);

        // Get all the productsList where sellStartDate greater than or equals to UPDATED_SELL_START_DATE
        defaultProductsShouldNotBeFound("sellStartDate.greaterOrEqualThan=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellStartDate less than or equals to DEFAULT_SELL_START_DATE
        defaultProductsShouldNotBeFound("sellStartDate.lessThan=" + DEFAULT_SELL_START_DATE);

        // Get all the productsList where sellStartDate less than or equals to UPDATED_SELL_START_DATE
        defaultProductsShouldBeFound("sellStartDate.lessThan=" + UPDATED_SELL_START_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsBySellEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellEndDate equals to DEFAULT_SELL_END_DATE
        defaultProductsShouldBeFound("sellEndDate.equals=" + DEFAULT_SELL_END_DATE);

        // Get all the productsList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultProductsShouldNotBeFound("sellEndDate.equals=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellEndDate in DEFAULT_SELL_END_DATE or UPDATED_SELL_END_DATE
        defaultProductsShouldBeFound("sellEndDate.in=" + DEFAULT_SELL_END_DATE + "," + UPDATED_SELL_END_DATE);

        // Get all the productsList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultProductsShouldNotBeFound("sellEndDate.in=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellEndDate is not null
        defaultProductsShouldBeFound("sellEndDate.specified=true");

        // Get all the productsList where sellEndDate is null
        defaultProductsShouldNotBeFound("sellEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellEndDate greater than or equals to DEFAULT_SELL_END_DATE
        defaultProductsShouldBeFound("sellEndDate.greaterOrEqualThan=" + DEFAULT_SELL_END_DATE);

        // Get all the productsList where sellEndDate greater than or equals to UPDATED_SELL_END_DATE
        defaultProductsShouldNotBeFound("sellEndDate.greaterOrEqualThan=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellEndDate less than or equals to DEFAULT_SELL_END_DATE
        defaultProductsShouldNotBeFound("sellEndDate.lessThan=" + DEFAULT_SELL_END_DATE);

        // Get all the productsList where sellEndDate less than or equals to UPDATED_SELL_END_DATE
        defaultProductsShouldBeFound("sellEndDate.lessThan=" + UPDATED_SELL_END_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByMarketingCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where marketingComments equals to DEFAULT_MARKETING_COMMENTS
        defaultProductsShouldBeFound("marketingComments.equals=" + DEFAULT_MARKETING_COMMENTS);

        // Get all the productsList where marketingComments equals to UPDATED_MARKETING_COMMENTS
        defaultProductsShouldNotBeFound("marketingComments.equals=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductsByMarketingCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where marketingComments in DEFAULT_MARKETING_COMMENTS or UPDATED_MARKETING_COMMENTS
        defaultProductsShouldBeFound("marketingComments.in=" + DEFAULT_MARKETING_COMMENTS + "," + UPDATED_MARKETING_COMMENTS);

        // Get all the productsList where marketingComments equals to UPDATED_MARKETING_COMMENTS
        defaultProductsShouldNotBeFound("marketingComments.in=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductsByMarketingCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where marketingComments is not null
        defaultProductsShouldBeFound("marketingComments.specified=true");

        // Get all the productsList where marketingComments is null
        defaultProductsShouldNotBeFound("marketingComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultProductsShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the productsList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultProductsShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultProductsShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the productsList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultProductsShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where internalComments is not null
        defaultProductsShouldBeFound("internalComments.specified=true");

        // Get all the productsList where internalComments is null
        defaultProductsShouldNotBeFound("internalComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDiscontinuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discontinuedDate equals to DEFAULT_DISCONTINUED_DATE
        defaultProductsShouldBeFound("discontinuedDate.equals=" + DEFAULT_DISCONTINUED_DATE);

        // Get all the productsList where discontinuedDate equals to UPDATED_DISCONTINUED_DATE
        defaultProductsShouldNotBeFound("discontinuedDate.equals=" + UPDATED_DISCONTINUED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscontinuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discontinuedDate in DEFAULT_DISCONTINUED_DATE or UPDATED_DISCONTINUED_DATE
        defaultProductsShouldBeFound("discontinuedDate.in=" + DEFAULT_DISCONTINUED_DATE + "," + UPDATED_DISCONTINUED_DATE);

        // Get all the productsList where discontinuedDate equals to UPDATED_DISCONTINUED_DATE
        defaultProductsShouldNotBeFound("discontinuedDate.in=" + UPDATED_DISCONTINUED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscontinuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discontinuedDate is not null
        defaultProductsShouldBeFound("discontinuedDate.specified=true");

        // Get all the productsList where discontinuedDate is null
        defaultProductsShouldNotBeFound("discontinuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDiscontinuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discontinuedDate greater than or equals to DEFAULT_DISCONTINUED_DATE
        defaultProductsShouldBeFound("discontinuedDate.greaterOrEqualThan=" + DEFAULT_DISCONTINUED_DATE);

        // Get all the productsList where discontinuedDate greater than or equals to UPDATED_DISCONTINUED_DATE
        defaultProductsShouldNotBeFound("discontinuedDate.greaterOrEqualThan=" + UPDATED_DISCONTINUED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscontinuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discontinuedDate less than or equals to DEFAULT_DISCONTINUED_DATE
        defaultProductsShouldNotBeFound("discontinuedDate.lessThan=" + DEFAULT_DISCONTINUED_DATE);

        // Get all the productsList where discontinuedDate less than or equals to UPDATED_DISCONTINUED_DATE
        defaultProductsShouldBeFound("discontinuedDate.lessThan=" + UPDATED_DISCONTINUED_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsBySellCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount equals to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.equals=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.equals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount in DEFAULT_SELL_COUNT or UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.in=" + DEFAULT_SELL_COUNT + "," + UPDATED_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.in=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is not null
        defaultProductsShouldBeFound("sellCount.specified=true");

        // Get all the productsList where sellCount is null
        defaultProductsShouldNotBeFound("sellCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount greater than or equals to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.greaterOrEqualThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount greater than or equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.greaterOrEqualThan=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount less than or equals to DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.lessThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount less than or equals to UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.lessThan=" + UPDATED_SELL_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductsByReviewLineIsEqualToSomething() throws Exception {
        // Initialize the database
        ReviewLines reviewLine = ReviewLinesResourceIntTest.createEntity(em);
        em.persist(reviewLine);
        em.flush();
        products.setReviewLine(reviewLine);
        productsRepository.saveAndFlush(products);
        Long reviewLineId = reviewLine.getId();

        // Get all the productsList where reviewLine equals to reviewLineId
        defaultProductsShouldBeFound("reviewLineId.equals=" + reviewLineId);

        // Get all the productsList where reviewLine equals to reviewLineId + 1
        defaultProductsShouldNotBeFound("reviewLineId.equals=" + (reviewLineId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByUnitPackageIsEqualToSomething() throws Exception {
        // Initialize the database
        PackageTypes unitPackage = PackageTypesResourceIntTest.createEntity(em);
        em.persist(unitPackage);
        em.flush();
        products.setUnitPackage(unitPackage);
        productsRepository.saveAndFlush(products);
        Long unitPackageId = unitPackage.getId();

        // Get all the productsList where unitPackage equals to unitPackageId
        defaultProductsShouldBeFound("unitPackageId.equals=" + unitPackageId);

        // Get all the productsList where unitPackage equals to unitPackageId + 1
        defaultProductsShouldNotBeFound("unitPackageId.equals=" + (unitPackageId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByOuterPackageIsEqualToSomething() throws Exception {
        // Initialize the database
        PackageTypes outerPackage = PackageTypesResourceIntTest.createEntity(em);
        em.persist(outerPackage);
        em.flush();
        products.setOuterPackage(outerPackage);
        productsRepository.saveAndFlush(products);
        Long outerPackageId = outerPackage.getId();

        // Get all the productsList where outerPackage equals to outerPackageId
        defaultProductsShouldBeFound("outerPackageId.equals=" + outerPackageId);

        // Get all the productsList where outerPackage equals to outerPackageId + 1
        defaultProductsShouldNotBeFound("outerPackageId.equals=" + (outerPackageId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        Suppliers supplier = SuppliersResourceIntTest.createEntity(em);
        em.persist(supplier);
        em.flush();
        products.setSupplier(supplier);
        productsRepository.saveAndFlush(products);
        Long supplierId = supplier.getId();

        // Get all the productsList where supplier equals to supplierId
        defaultProductsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productsList where supplier equals to supplierId + 1
        defaultProductsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByMerchantIsEqualToSomething() throws Exception {
        // Initialize the database
        Merchants merchant = MerchantsResourceIntTest.createEntity(em);
        em.persist(merchant);
        em.flush();
        products.setMerchant(merchant);
        productsRepository.saveAndFlush(products);
        Long merchantId = merchant.getId();

        // Get all the productsList where merchant equals to merchantId
        defaultProductsShouldBeFound("merchantId.equals=" + merchantId);

        // Get all the productsList where merchant equals to merchantId + 1
        defaultProductsShouldNotBeFound("merchantId.equals=" + (merchantId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductSubCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductSubCategory productSubCategory = ProductSubCategoryResourceIntTest.createEntity(em);
        em.persist(productSubCategory);
        em.flush();
        products.setProductSubCategory(productSubCategory);
        productsRepository.saveAndFlush(products);
        Long productSubCategoryId = productSubCategory.getId();

        // Get all the productsList where productSubCategory equals to productSubCategoryId
        defaultProductsShouldBeFound("productSubCategoryId.equals=" + productSubCategoryId);

        // Get all the productsList where productSubCategory equals to productSubCategoryId + 1
        defaultProductsShouldNotBeFound("productSubCategoryId.equals=" + (productSubCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsBySizeUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        UnitMeasure sizeUnitMeasureCode = UnitMeasureResourceIntTest.createEntity(em);
        em.persist(sizeUnitMeasureCode);
        em.flush();
        products.setSizeUnitMeasureCode(sizeUnitMeasureCode);
        productsRepository.saveAndFlush(products);
        Long sizeUnitMeasureCodeId = sizeUnitMeasureCode.getId();

        // Get all the productsList where sizeUnitMeasureCode equals to sizeUnitMeasureCodeId
        defaultProductsShouldBeFound("sizeUnitMeasureCodeId.equals=" + sizeUnitMeasureCodeId);

        // Get all the productsList where sizeUnitMeasureCode equals to sizeUnitMeasureCodeId + 1
        defaultProductsShouldNotBeFound("sizeUnitMeasureCodeId.equals=" + (sizeUnitMeasureCodeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByWeightUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        UnitMeasure weightUnitMeasureCode = UnitMeasureResourceIntTest.createEntity(em);
        em.persist(weightUnitMeasureCode);
        em.flush();
        products.setWeightUnitMeasureCode(weightUnitMeasureCode);
        productsRepository.saveAndFlush(products);
        Long weightUnitMeasureCodeId = weightUnitMeasureCode.getId();

        // Get all the productsList where weightUnitMeasureCode equals to weightUnitMeasureCodeId
        defaultProductsShouldBeFound("weightUnitMeasureCodeId.equals=" + weightUnitMeasureCodeId);

        // Get all the productsList where weightUnitMeasureCode equals to weightUnitMeasureCodeId + 1
        defaultProductsShouldNotBeFound("weightUnitMeasureCodeId.equals=" + (weightUnitMeasureCodeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductModelIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductModel productModel = ProductModelResourceIntTest.createEntity(em);
        em.persist(productModel);
        em.flush();
        products.setProductModel(productModel);
        productsRepository.saveAndFlush(products);
        Long productModelId = productModel.getId();

        // Get all the productsList where productModel equals to productModelId
        defaultProductsShouldBeFound("productModelId.equals=" + productModelId);

        // Get all the productsList where productModel equals to productModelId + 1
        defaultProductsShouldNotBeFound("productModelId.equals=" + (productModelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductsShouldBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS)))
            .andExpect(jsonPath("$.[*].makeFlag").value(hasItem(DEFAULT_MAKE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].finishedGoodsFlag").value(hasItem(DEFAULT_FINISHED_GOODS_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].safetyStockLevel").value(hasItem(DEFAULT_SAFETY_STOCK_LEVEL)))
            .andExpect(jsonPath("$.[*].reorderPoint").value(hasItem(DEFAULT_REORDER_POINT)))
            .andExpect(jsonPath("$.[*].standardCost").value(hasItem(DEFAULT_STANDARD_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].recommendedRetailPrice").value(hasItem(DEFAULT_RECOMMENDED_RETAIL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].specifySize").value(hasItem(DEFAULT_SPECIFY_SIZE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].daysToManufacture").value(hasItem(DEFAULT_DAYS_TO_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].productLine").value(hasItem(DEFAULT_PRODUCT_LINE)))
            .andExpect(jsonPath("$.[*].classType").value(hasItem(DEFAULT_CLASS_TYPE)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].marketingComments").value(hasItem(DEFAULT_MARKETING_COMMENTS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].discontinuedDate").value(hasItem(DEFAULT_DISCONTINUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)));

        // Check, that the count call also returns 1
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductsShouldNotBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        Products updatedProducts = productsRepository.findById(products.getId()).get();
        // Disconnect from session so that the updates on updatedProducts are not directly saved in db
        em.detach(updatedProducts);
        updatedProducts
            .productName(UPDATED_PRODUCT_NAME)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .makeFlag(UPDATED_MAKE_FLAG)
            .finishedGoodsFlag(UPDATED_FINISHED_GOODS_FLAG)
            .color(UPDATED_COLOR)
            .safetyStockLevel(UPDATED_SAFETY_STOCK_LEVEL)
            .reorderPoint(UPDATED_REORDER_POINT)
            .standardCost(UPDATED_STANDARD_COST)
            .unitPrice(UPDATED_UNIT_PRICE)
            .recommendedRetailPrice(UPDATED_RECOMMENDED_RETAIL_PRICE)
            .brand(UPDATED_BRAND)
            .specifySize(UPDATED_SPECIFY_SIZE)
            .weight(UPDATED_WEIGHT)
            .daysToManufacture(UPDATED_DAYS_TO_MANUFACTURE)
            .productLine(UPDATED_PRODUCT_LINE)
            .classType(UPDATED_CLASS_TYPE)
            .style(UPDATED_STYLE)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .photo(UPDATED_PHOTO)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .marketingComments(UPDATED_MARKETING_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .discontinuedDate(UPDATED_DISCONTINUED_DATE)
            .sellCount(UPDATED_SELL_COUNT);
        ProductsDTO productsDTO = productsMapper.toDto(updatedProducts);

        restProductsMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProducts.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(UPDATED_SEARCH_DETAILS);
        assertThat(testProducts.isMakeFlag()).isEqualTo(UPDATED_MAKE_FLAG);
        assertThat(testProducts.isFinishedGoodsFlag()).isEqualTo(UPDATED_FINISHED_GOODS_FLAG);
        assertThat(testProducts.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducts.getSafetyStockLevel()).isEqualTo(UPDATED_SAFETY_STOCK_LEVEL);
        assertThat(testProducts.getReorderPoint()).isEqualTo(UPDATED_REORDER_POINT);
        assertThat(testProducts.getStandardCost()).isEqualTo(UPDATED_STANDARD_COST);
        assertThat(testProducts.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testProducts.getRecommendedRetailPrice()).isEqualTo(UPDATED_RECOMMENDED_RETAIL_PRICE);
        assertThat(testProducts.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProducts.getSpecifySize()).isEqualTo(UPDATED_SPECIFY_SIZE);
        assertThat(testProducts.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProducts.getDaysToManufacture()).isEqualTo(UPDATED_DAYS_TO_MANUFACTURE);
        assertThat(testProducts.getProductLine()).isEqualTo(UPDATED_PRODUCT_LINE);
        assertThat(testProducts.getClassType()).isEqualTo(UPDATED_CLASS_TYPE);
        assertThat(testProducts.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testProducts.getCustomFields()).isEqualTo(UPDATED_CUSTOM_FIELDS);
        assertThat(testProducts.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProducts.getSellStartDate()).isEqualTo(UPDATED_SELL_START_DATE);
        assertThat(testProducts.getSellEndDate()).isEqualTo(UPDATED_SELL_END_DATE);
        assertThat(testProducts.getMarketingComments()).isEqualTo(UPDATED_MARKETING_COMMENTS);
        assertThat(testProducts.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testProducts.getDiscontinuedDate()).isEqualTo(UPDATED_DISCONTINUED_DATE);
        assertThat(testProducts.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Delete the products
        restProductsMockMvc.perform(delete("/api/products/{id}", products.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Products.class);
        Products products1 = new Products();
        products1.setId(1L);
        Products products2 = new Products();
        products2.setId(products1.getId());
        assertThat(products1).isEqualTo(products2);
        products2.setId(2L);
        assertThat(products1).isNotEqualTo(products2);
        products1.setId(null);
        assertThat(products1).isNotEqualTo(products2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsDTO.class);
        ProductsDTO productsDTO1 = new ProductsDTO();
        productsDTO1.setId(1L);
        ProductsDTO productsDTO2 = new ProductsDTO();
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO2.setId(productsDTO1.getId());
        assertThat(productsDTO1).isEqualTo(productsDTO2);
        productsDTO2.setId(2L);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO1.setId(null);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productsMapper.fromId(null)).isNull();
    }
}
