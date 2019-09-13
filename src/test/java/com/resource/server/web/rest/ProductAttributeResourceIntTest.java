package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductAttribute;
import com.resource.server.repository.ProductAttributeRepository;
import com.resource.server.service.ProductAttributeService;
import com.resource.server.service.dto.ProductAttributeDTO;
import com.resource.server.service.mapper.ProductAttributeMapper;
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
 * Test class for the ProductAttributeResource REST controller.
 *
 * @see ProductAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductAttributeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private ProductAttributeService productAttributeService;

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

    private MockMvc restProductAttributeMockMvc;

    private ProductAttribute productAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAttributeResource productAttributeResource = new ProductAttributeResource(productAttributeService);
        this.restProductAttributeMockMvc = MockMvcBuilders.standaloneSetup(productAttributeResource)
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
    public static ProductAttribute createEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .value(DEFAULT_VALUE);
        return productAttribute;
    }

    @Before
    public void initTest() {
        productAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute with an existing ID
        productAttribute.setId(1L);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAttributeRepository.findAll().size();
        // set the field null
        productAttribute.setValue(null);

        // Create the ProductAttribute, which fails.
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAttributes() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttribute.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductAttribute() throws Exception {
        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Update the productAttribute
        ProductAttribute updatedProductAttribute = productAttributeRepository.findById(productAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttribute are not directly saved in db
        em.detach(updatedProductAttribute);
        updatedProductAttribute
            .value(UPDATED_VALUE);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(updatedProductAttribute);

        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeDelete = productAttributeRepository.findAll().size();

        // Delete the productAttribute
        restProductAttributeMockMvc.perform(delete("/api/product-attributes/{id}", productAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttribute.class);
        ProductAttribute productAttribute1 = new ProductAttribute();
        productAttribute1.setId(1L);
        ProductAttribute productAttribute2 = new ProductAttribute();
        productAttribute2.setId(productAttribute1.getId());
        assertThat(productAttribute1).isEqualTo(productAttribute2);
        productAttribute2.setId(2L);
        assertThat(productAttribute1).isNotEqualTo(productAttribute2);
        productAttribute1.setId(null);
        assertThat(productAttribute1).isNotEqualTo(productAttribute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeDTO.class);
        ProductAttributeDTO productAttributeDTO1 = new ProductAttributeDTO();
        productAttributeDTO1.setId(1L);
        ProductAttributeDTO productAttributeDTO2 = new ProductAttributeDTO();
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(productAttributeDTO1.getId());
        assertThat(productAttributeDTO1).isEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(2L);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO1.setId(null);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productAttributeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productAttributeMapper.fromId(null)).isNull();
    }
}
