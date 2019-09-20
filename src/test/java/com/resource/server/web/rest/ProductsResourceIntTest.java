package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Products;
import com.resource.server.domain.StockItems;
import com.resource.server.domain.Suppliers;
import com.resource.server.domain.Merchants;
import com.resource.server.domain.PackageTypes;
import com.resource.server.domain.ProductModel;
import com.resource.server.domain.ProductCategory;
import com.resource.server.domain.ProductBrand;
import com.resource.server.domain.WarrantyTypes;
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

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;

    private static final String DEFAULT_WHAT_IN_THE_BOX = "AAAAAAAAAA";
    private static final String UPDATED_WHAT_IN_THE_BOX = "BBBBBBBBBB";

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
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY)
            .sellCount(DEFAULT_SELL_COUNT)
            .whatInTheBox(DEFAULT_WHAT_IN_THE_BOX);
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
        assertThat(testProducts.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
        assertThat(testProducts.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testProducts.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
        assertThat(testProducts.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
        assertThat(testProducts.getWhatInTheBox()).isEqualTo(DEFAULT_WHAT_IN_THE_BOX);
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
    public void checkWhatInTheBoxIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setWhatInTheBox(null);

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
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL.toString())))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())));
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
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL.toString()))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD.toString()))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.whatInTheBox").value(DEFAULT_WHAT_IN_THE_BOX.toString()));
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
    public void getAllProductsByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultProductsShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productsList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductsShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultProductsShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the productsList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductsShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where thumbnailUrl is not null
        defaultProductsShouldBeFound("thumbnailUrl.specified=true");

        // Get all the productsList where thumbnailUrl is null
        defaultProductsShouldNotBeFound("thumbnailUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPeriod equals to DEFAULT_WARRANTY_PERIOD
        defaultProductsShouldBeFound("warrantyPeriod.equals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productsList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductsShouldNotBeFound("warrantyPeriod.equals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPeriod in DEFAULT_WARRANTY_PERIOD or UPDATED_WARRANTY_PERIOD
        defaultProductsShouldBeFound("warrantyPeriod.in=" + DEFAULT_WARRANTY_PERIOD + "," + UPDATED_WARRANTY_PERIOD);

        // Get all the productsList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductsShouldNotBeFound("warrantyPeriod.in=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPeriod is not null
        defaultProductsShouldBeFound("warrantyPeriod.specified=true");

        // Get all the productsList where warrantyPeriod is null
        defaultProductsShouldNotBeFound("warrantyPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPolicy equals to DEFAULT_WARRANTY_POLICY
        defaultProductsShouldBeFound("warrantyPolicy.equals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productsList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductsShouldNotBeFound("warrantyPolicy.equals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPolicy in DEFAULT_WARRANTY_POLICY or UPDATED_WARRANTY_POLICY
        defaultProductsShouldBeFound("warrantyPolicy.in=" + DEFAULT_WARRANTY_POLICY + "," + UPDATED_WARRANTY_POLICY);

        // Get all the productsList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductsShouldNotBeFound("warrantyPolicy.in=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductsByWarrantyPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where warrantyPolicy is not null
        defaultProductsShouldBeFound("warrantyPolicy.specified=true");

        // Get all the productsList where warrantyPolicy is null
        defaultProductsShouldNotBeFound("warrantyPolicy.specified=false");
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
    public void getAllProductsByWhatInTheBoxIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where whatInTheBox equals to DEFAULT_WHAT_IN_THE_BOX
        defaultProductsShouldBeFound("whatInTheBox.equals=" + DEFAULT_WHAT_IN_THE_BOX);

        // Get all the productsList where whatInTheBox equals to UPDATED_WHAT_IN_THE_BOX
        defaultProductsShouldNotBeFound("whatInTheBox.equals=" + UPDATED_WHAT_IN_THE_BOX);
    }

    @Test
    @Transactional
    public void getAllProductsByWhatInTheBoxIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where whatInTheBox in DEFAULT_WHAT_IN_THE_BOX or UPDATED_WHAT_IN_THE_BOX
        defaultProductsShouldBeFound("whatInTheBox.in=" + DEFAULT_WHAT_IN_THE_BOX + "," + UPDATED_WHAT_IN_THE_BOX);

        // Get all the productsList where whatInTheBox equals to UPDATED_WHAT_IN_THE_BOX
        defaultProductsShouldNotBeFound("whatInTheBox.in=" + UPDATED_WHAT_IN_THE_BOX);
    }

    @Test
    @Transactional
    public void getAllProductsByWhatInTheBoxIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where whatInTheBox is not null
        defaultProductsShouldBeFound("whatInTheBox.specified=true");

        // Get all the productsList where whatInTheBox is null
        defaultProductsShouldNotBeFound("whatInTheBox.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByStockItemListIsEqualToSomething() throws Exception {
        // Initialize the database
        StockItems stockItemList = StockItemsResourceIntTest.createEntity(em);
        em.persist(stockItemList);
        em.flush();
        products.addStockItemList(stockItemList);
        productsRepository.saveAndFlush(products);
        Long stockItemListId = stockItemList.getId();

        // Get all the productsList where stockItemList equals to stockItemListId
        defaultProductsShouldBeFound("stockItemListId.equals=" + stockItemListId);

        // Get all the productsList where stockItemList equals to stockItemListId + 1
        defaultProductsShouldNotBeFound("stockItemListId.equals=" + (stockItemListId + 1));
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


    @Test
    @Transactional
    public void getAllProductsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        products.setProductCategory(productCategory);
        productsRepository.saveAndFlush(products);
        Long productCategoryId = productCategory.getId();

        // Get all the productsList where productCategory equals to productCategoryId
        defaultProductsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productsList where productCategory equals to productCategoryId + 1
        defaultProductsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductBrand productBrand = ProductBrandResourceIntTest.createEntity(em);
        em.persist(productBrand);
        em.flush();
        products.setProductBrand(productBrand);
        productsRepository.saveAndFlush(products);
        Long productBrandId = productBrand.getId();

        // Get all the productsList where productBrand equals to productBrandId
        defaultProductsShouldBeFound("productBrandId.equals=" + productBrandId);

        // Get all the productsList where productBrand equals to productBrandId + 1
        defaultProductsShouldNotBeFound("productBrandId.equals=" + (productBrandId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        WarrantyTypes warrantyType = WarrantyTypesResourceIntTest.createEntity(em);
        em.persist(warrantyType);
        em.flush();
        products.setWarrantyType(warrantyType);
        productsRepository.saveAndFlush(products);
        Long warrantyTypeId = warrantyType.getId();

        // Get all the productsList where warrantyType equals to warrantyTypeId
        defaultProductsShouldBeFound("warrantyTypeId.equals=" + warrantyTypeId);

        // Get all the productsList where warrantyType equals to warrantyTypeId + 1
        defaultProductsShouldNotBeFound("warrantyTypeId.equals=" + (warrantyTypeId + 1));
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
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX)));

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
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .sellCount(UPDATED_SELL_COUNT)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX);
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
        assertThat(testProducts.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
        assertThat(testProducts.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testProducts.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
        assertThat(testProducts.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
        assertThat(testProducts.getWhatInTheBox()).isEqualTo(UPDATED_WHAT_IN_THE_BOX);
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
