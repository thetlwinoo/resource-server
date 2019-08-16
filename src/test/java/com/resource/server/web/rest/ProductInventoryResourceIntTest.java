package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductInventory;
import com.resource.server.repository.ProductInventoryRepository;
import com.resource.server.service.ProductInventoryService;
import com.resource.server.service.dto.ProductInventoryDTO;
import com.resource.server.service.mapper.ProductInventoryMapper;
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
 * Test class for the ProductInventoryResource REST controller.
 *
 * @see ProductInventoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductInventoryResourceIntTest {

    private static final String DEFAULT_SHELF = "AAAAAAAAAA";
    private static final String UPDATED_SHELF = "BBBBBBBBBB";

    private static final Integer DEFAULT_BIN = 1;
    private static final Integer UPDATED_BIN = 2;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private ProductInventoryService productInventoryService;

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

    private MockMvc restProductInventoryMockMvc;

    private ProductInventory productInventory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductInventoryResource productInventoryResource = new ProductInventoryResource(productInventoryService);
        this.restProductInventoryMockMvc = MockMvcBuilders.standaloneSetup(productInventoryResource)
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
    public static ProductInventory createEntity(EntityManager em) {
        ProductInventory productInventory = new ProductInventory()
            .shelf(DEFAULT_SHELF)
            .bin(DEFAULT_BIN)
            .quantity(DEFAULT_QUANTITY);
        return productInventory;
    }

    @Before
    public void initTest() {
        productInventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductInventory() throws Exception {
        int databaseSizeBeforeCreate = productInventoryRepository.findAll().size();

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);
        restProductInventoryMockMvc.perform(post("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getShelf()).isEqualTo(DEFAULT_SHELF);
        assertThat(testProductInventory.getBin()).isEqualTo(DEFAULT_BIN);
        assertThat(testProductInventory.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createProductInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productInventoryRepository.findAll().size();

        // Create the ProductInventory with an existing ID
        productInventory.setId(1L);
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInventoryMockMvc.perform(post("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShelfIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInventoryRepository.findAll().size();
        // set the field null
        productInventory.setShelf(null);

        // Create the ProductInventory, which fails.
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        restProductInventoryMockMvc.perform(post("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBinIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInventoryRepository.findAll().size();
        // set the field null
        productInventory.setBin(null);

        // Create the ProductInventory, which fails.
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        restProductInventoryMockMvc.perform(post("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInventoryRepository.findAll().size();
        // set the field null
        productInventory.setQuantity(null);

        // Create the ProductInventory, which fails.
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        restProductInventoryMockMvc.perform(post("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductInventories() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList
        restProductInventoryMockMvc.perform(get("/api/product-inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].shelf").value(hasItem(DEFAULT_SHELF)))
            .andExpect(jsonPath("$.[*].bin").value(hasItem(DEFAULT_BIN)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get the productInventory
        restProductInventoryMockMvc.perform(get("/api/product-inventories/{id}", productInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productInventory.getId().intValue()))
            .andExpect(jsonPath("$.shelf").value(DEFAULT_SHELF))
            .andExpect(jsonPath("$.bin").value(DEFAULT_BIN))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingProductInventory() throws Exception {
        // Get the productInventory
        restProductInventoryMockMvc.perform(get("/api/product-inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();

        // Update the productInventory
        ProductInventory updatedProductInventory = productInventoryRepository.findById(productInventory.getId()).get();
        // Disconnect from session so that the updates on updatedProductInventory are not directly saved in db
        em.detach(updatedProductInventory);
        updatedProductInventory
            .shelf(UPDATED_SHELF)
            .bin(UPDATED_BIN)
            .quantity(UPDATED_QUANTITY);
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(updatedProductInventory);

        restProductInventoryMockMvc.perform(put("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getShelf()).isEqualTo(UPDATED_SHELF);
        assertThat(testProductInventory.getBin()).isEqualTo(UPDATED_BIN);
        assertThat(testProductInventory.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc.perform(put("/api/product-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeDelete = productInventoryRepository.findAll().size();

        // Delete the productInventory
        restProductInventoryMockMvc.perform(delete("/api/product-inventories/{id}", productInventory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInventory.class);
        ProductInventory productInventory1 = new ProductInventory();
        productInventory1.setId(1L);
        ProductInventory productInventory2 = new ProductInventory();
        productInventory2.setId(productInventory1.getId());
        assertThat(productInventory1).isEqualTo(productInventory2);
        productInventory2.setId(2L);
        assertThat(productInventory1).isNotEqualTo(productInventory2);
        productInventory1.setId(null);
        assertThat(productInventory1).isNotEqualTo(productInventory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInventoryDTO.class);
        ProductInventoryDTO productInventoryDTO1 = new ProductInventoryDTO();
        productInventoryDTO1.setId(1L);
        ProductInventoryDTO productInventoryDTO2 = new ProductInventoryDTO();
        assertThat(productInventoryDTO1).isNotEqualTo(productInventoryDTO2);
        productInventoryDTO2.setId(productInventoryDTO1.getId());
        assertThat(productInventoryDTO1).isEqualTo(productInventoryDTO2);
        productInventoryDTO2.setId(2L);
        assertThat(productInventoryDTO1).isNotEqualTo(productInventoryDTO2);
        productInventoryDTO1.setId(null);
        assertThat(productInventoryDTO1).isNotEqualTo(productInventoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productInventoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productInventoryMapper.fromId(null)).isNull();
    }
}
