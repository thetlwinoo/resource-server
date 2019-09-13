package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductSetDetails;
import com.resource.server.repository.ProductSetDetailsRepository;
import com.resource.server.service.ProductSetDetailsService;
import com.resource.server.service.dto.ProductSetDetailsDTO;
import com.resource.server.service.mapper.ProductSetDetailsMapper;
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
 * Test class for the ProductSetDetailsResource REST controller.
 *
 * @see ProductSetDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductSetDetailsResourceIntTest {

    private static final Integer DEFAULT_SUB_GROUP_NO = 1;
    private static final Integer UPDATED_SUB_GROUP_NO = 2;

    private static final Integer DEFAULT_SUB_GROUP_MIN_COUNT = 1;
    private static final Integer UPDATED_SUB_GROUP_MIN_COUNT = 2;

    private static final Float DEFAULT_SUB_GROUP_MIN_TOTAL = 1F;
    private static final Float UPDATED_SUB_GROUP_MIN_TOTAL = 2F;

    private static final Integer DEFAULT_MIN_COUNT = 1;
    private static final Integer UPDATED_MIN_COUNT = 2;

    private static final Integer DEFAULT_MAX_COUNT = 1;
    private static final Integer UPDATED_MAX_COUNT = 2;

    private static final Boolean DEFAULT_IS_OPTIONAL = false;
    private static final Boolean UPDATED_IS_OPTIONAL = true;

    @Autowired
    private ProductSetDetailsRepository productSetDetailsRepository;

    @Autowired
    private ProductSetDetailsMapper productSetDetailsMapper;

    @Autowired
    private ProductSetDetailsService productSetDetailsService;

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

    private MockMvc restProductSetDetailsMockMvc;

    private ProductSetDetails productSetDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSetDetailsResource productSetDetailsResource = new ProductSetDetailsResource(productSetDetailsService);
        this.restProductSetDetailsMockMvc = MockMvcBuilders.standaloneSetup(productSetDetailsResource)
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
    public static ProductSetDetails createEntity(EntityManager em) {
        ProductSetDetails productSetDetails = new ProductSetDetails()
            .subGroupNo(DEFAULT_SUB_GROUP_NO)
            .subGroupMinCount(DEFAULT_SUB_GROUP_MIN_COUNT)
            .subGroupMinTotal(DEFAULT_SUB_GROUP_MIN_TOTAL)
            .minCount(DEFAULT_MIN_COUNT)
            .maxCount(DEFAULT_MAX_COUNT)
            .isOptional(DEFAULT_IS_OPTIONAL);
        return productSetDetails;
    }

    @Before
    public void initTest() {
        productSetDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSetDetails() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);
        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSetDetails testProductSetDetails = productSetDetailsList.get(productSetDetailsList.size() - 1);
        assertThat(testProductSetDetails.getSubGroupNo()).isEqualTo(DEFAULT_SUB_GROUP_NO);
        assertThat(testProductSetDetails.getSubGroupMinCount()).isEqualTo(DEFAULT_SUB_GROUP_MIN_COUNT);
        assertThat(testProductSetDetails.getSubGroupMinTotal()).isEqualTo(DEFAULT_SUB_GROUP_MIN_TOTAL);
        assertThat(testProductSetDetails.getMinCount()).isEqualTo(DEFAULT_MIN_COUNT);
        assertThat(testProductSetDetails.getMaxCount()).isEqualTo(DEFAULT_MAX_COUNT);
        assertThat(testProductSetDetails.isIsOptional()).isEqualTo(DEFAULT_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void createProductSetDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails with an existing ID
        productSetDetails.setId(1L);
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubGroupMinTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailsRepository.findAll().size();
        // set the field null
        productSetDetails.setSubGroupMinTotal(null);

        // Create the ProductSetDetails, which fails.
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].subGroupNo").value(hasItem(DEFAULT_SUB_GROUP_NO)))
            .andExpect(jsonPath("$.[*].subGroupMinCount").value(hasItem(DEFAULT_SUB_GROUP_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].subGroupMinTotal").value(hasItem(DEFAULT_SUB_GROUP_MIN_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].minCount").value(hasItem(DEFAULT_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].maxCount").value(hasItem(DEFAULT_MAX_COUNT)))
            .andExpect(jsonPath("$.[*].isOptional").value(hasItem(DEFAULT_IS_OPTIONAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get the productSetDetails
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/{id}", productSetDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSetDetails.getId().intValue()))
            .andExpect(jsonPath("$.subGroupNo").value(DEFAULT_SUB_GROUP_NO))
            .andExpect(jsonPath("$.subGroupMinCount").value(DEFAULT_SUB_GROUP_MIN_COUNT))
            .andExpect(jsonPath("$.subGroupMinTotal").value(DEFAULT_SUB_GROUP_MIN_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.minCount").value(DEFAULT_MIN_COUNT))
            .andExpect(jsonPath("$.maxCount").value(DEFAULT_MAX_COUNT))
            .andExpect(jsonPath("$.isOptional").value(DEFAULT_IS_OPTIONAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductSetDetails() throws Exception {
        // Get the productSetDetails
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        int databaseSizeBeforeUpdate = productSetDetailsRepository.findAll().size();

        // Update the productSetDetails
        ProductSetDetails updatedProductSetDetails = productSetDetailsRepository.findById(productSetDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProductSetDetails are not directly saved in db
        em.detach(updatedProductSetDetails);
        updatedProductSetDetails
            .subGroupNo(UPDATED_SUB_GROUP_NO)
            .subGroupMinCount(UPDATED_SUB_GROUP_MIN_COUNT)
            .subGroupMinTotal(UPDATED_SUB_GROUP_MIN_TOTAL)
            .minCount(UPDATED_MIN_COUNT)
            .maxCount(UPDATED_MAX_COUNT)
            .isOptional(UPDATED_IS_OPTIONAL);
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(updatedProductSetDetails);

        restProductSetDetailsMockMvc.perform(put("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductSetDetails testProductSetDetails = productSetDetailsList.get(productSetDetailsList.size() - 1);
        assertThat(testProductSetDetails.getSubGroupNo()).isEqualTo(UPDATED_SUB_GROUP_NO);
        assertThat(testProductSetDetails.getSubGroupMinCount()).isEqualTo(UPDATED_SUB_GROUP_MIN_COUNT);
        assertThat(testProductSetDetails.getSubGroupMinTotal()).isEqualTo(UPDATED_SUB_GROUP_MIN_TOTAL);
        assertThat(testProductSetDetails.getMinCount()).isEqualTo(UPDATED_MIN_COUNT);
        assertThat(testProductSetDetails.getMaxCount()).isEqualTo(UPDATED_MAX_COUNT);
        assertThat(testProductSetDetails.isIsOptional()).isEqualTo(UPDATED_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSetDetails() throws Exception {
        int databaseSizeBeforeUpdate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSetDetailsMockMvc.perform(put("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        int databaseSizeBeforeDelete = productSetDetailsRepository.findAll().size();

        // Delete the productSetDetails
        restProductSetDetailsMockMvc.perform(delete("/api/product-set-details/{id}", productSetDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetails.class);
        ProductSetDetails productSetDetails1 = new ProductSetDetails();
        productSetDetails1.setId(1L);
        ProductSetDetails productSetDetails2 = new ProductSetDetails();
        productSetDetails2.setId(productSetDetails1.getId());
        assertThat(productSetDetails1).isEqualTo(productSetDetails2);
        productSetDetails2.setId(2L);
        assertThat(productSetDetails1).isNotEqualTo(productSetDetails2);
        productSetDetails1.setId(null);
        assertThat(productSetDetails1).isNotEqualTo(productSetDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetailsDTO.class);
        ProductSetDetailsDTO productSetDetailsDTO1 = new ProductSetDetailsDTO();
        productSetDetailsDTO1.setId(1L);
        ProductSetDetailsDTO productSetDetailsDTO2 = new ProductSetDetailsDTO();
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO2.setId(productSetDetailsDTO1.getId());
        assertThat(productSetDetailsDTO1).isEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO2.setId(2L);
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO1.setId(null);
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productSetDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productSetDetailsMapper.fromId(null)).isNull();
    }
}
