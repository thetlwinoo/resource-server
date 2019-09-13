package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductBrand;
import com.resource.server.repository.ProductBrandRepository;
import com.resource.server.service.ProductBrandService;
import com.resource.server.service.dto.ProductBrandDTO;
import com.resource.server.service.mapper.ProductBrandMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductBrandResource REST controller.
 *
 * @see ProductBrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductBrandResourceIntTest {

    private static final String DEFAULT_PRODUCT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_BRAND_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private ProductBrandService productBrandService;

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

    private MockMvc restProductBrandMockMvc;

    private ProductBrand productBrand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductBrandResource productBrandResource = new ProductBrandResource(productBrandService);
        this.restProductBrandMockMvc = MockMvcBuilders.standaloneSetup(productBrandResource)
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
    public static ProductBrand createEntity(EntityManager em) {
        ProductBrand productBrand = new ProductBrand()
            .productBrandName(DEFAULT_PRODUCT_BRAND_NAME)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return productBrand;
    }

    @Before
    public void initTest() {
        productBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBrand() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();

        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);
        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate + 1);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getProductBrandName()).isEqualTo(DEFAULT_PRODUCT_BRAND_NAME);
        assertThat(testProductBrand.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProductBrand.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProductBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();

        // Create the ProductBrand with an existing ID
        productBrand.setId(1L);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductBrandNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandRepository.findAll().size();
        // set the field null
        productBrand.setProductBrandName(null);

        // Create the ProductBrand, which fails.
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductBrands() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].productBrandName").value(hasItem(DEFAULT_PRODUCT_BRAND_NAME.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
    
    @Test
    @Transactional
    public void getProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", productBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productBrand.getId().intValue()))
            .andExpect(jsonPath("$.productBrandName").value(DEFAULT_PRODUCT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingProductBrand() throws Exception {
        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Update the productBrand
        ProductBrand updatedProductBrand = productBrandRepository.findById(productBrand.getId()).get();
        // Disconnect from session so that the updates on updatedProductBrand are not directly saved in db
        em.detach(updatedProductBrand);
        updatedProductBrand
            .productBrandName(UPDATED_PRODUCT_BRAND_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(updatedProductBrand);

        restProductBrandMockMvc.perform(put("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isOk());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getProductBrandName()).isEqualTo(UPDATED_PRODUCT_BRAND_NAME);
        assertThat(testProductBrand.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProductBrand.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductBrand() throws Exception {
        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductBrandMockMvc.perform(put("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeDelete = productBrandRepository.findAll().size();

        // Delete the productBrand
        restProductBrandMockMvc.perform(delete("/api/product-brands/{id}", productBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrand.class);
        ProductBrand productBrand1 = new ProductBrand();
        productBrand1.setId(1L);
        ProductBrand productBrand2 = new ProductBrand();
        productBrand2.setId(productBrand1.getId());
        assertThat(productBrand1).isEqualTo(productBrand2);
        productBrand2.setId(2L);
        assertThat(productBrand1).isNotEqualTo(productBrand2);
        productBrand1.setId(null);
        assertThat(productBrand1).isNotEqualTo(productBrand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandDTO.class);
        ProductBrandDTO productBrandDTO1 = new ProductBrandDTO();
        productBrandDTO1.setId(1L);
        ProductBrandDTO productBrandDTO2 = new ProductBrandDTO();
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
        productBrandDTO2.setId(productBrandDTO1.getId());
        assertThat(productBrandDTO1).isEqualTo(productBrandDTO2);
        productBrandDTO2.setId(2L);
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
        productBrandDTO1.setId(null);
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productBrandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productBrandMapper.fromId(null)).isNull();
    }
}
