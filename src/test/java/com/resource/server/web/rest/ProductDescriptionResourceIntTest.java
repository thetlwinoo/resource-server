package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductDescription;
import com.resource.server.repository.ProductDescriptionRepository;
import com.resource.server.service.ProductDescriptionService;
import com.resource.server.service.dto.ProductDescriptionDTO;
import com.resource.server.service.mapper.ProductDescriptionMapper;
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
 * Test class for the ProductDescriptionResource REST controller.
 *
 * @see ProductDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductDescriptionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    private ProductDescriptionMapper productDescriptionMapper;

    @Autowired
    private ProductDescriptionService productDescriptionService;

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

    private MockMvc restProductDescriptionMockMvc;

    private ProductDescription productDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductDescriptionResource productDescriptionResource = new ProductDescriptionResource(productDescriptionService);
        this.restProductDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productDescriptionResource)
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
    public static ProductDescription createEntity(EntityManager em) {
        ProductDescription productDescription = new ProductDescription()
            .description(DEFAULT_DESCRIPTION);
        return productDescription;
    }

    @Before
    public void initTest() {
        productDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDescription() throws Exception {
        int databaseSizeBeforeCreate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription
        ProductDescriptionDTO productDescriptionDTO = productDescriptionMapper.toDto(productDescription);
        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDescription testProductDescription = productDescriptionList.get(productDescriptionList.size() - 1);
        assertThat(testProductDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription with an existing ID
        productDescription.setId(1L);
        ProductDescriptionDTO productDescriptionDTO = productDescriptionMapper.toDto(productDescription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDescriptionRepository.findAll().size();
        // set the field null
        productDescription.setDescription(null);

        // Create the ProductDescription, which fails.
        ProductDescriptionDTO productDescriptionDTO = productDescriptionMapper.toDto(productDescription);

        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescriptionDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductDescriptions() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get all the productDescriptionList
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getProductDescription() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", productDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDescription.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingProductDescription() throws Exception {
        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDescription() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        int databaseSizeBeforeUpdate = productDescriptionRepository.findAll().size();

        // Update the productDescription
        ProductDescription updatedProductDescription = productDescriptionRepository.findById(productDescription.getId()).get();
        // Disconnect from session so that the updates on updatedProductDescription are not directly saved in db
        em.detach(updatedProductDescription);
        updatedProductDescription
            .description(UPDATED_DESCRIPTION);
        ProductDescriptionDTO productDescriptionDTO = productDescriptionMapper.toDto(updatedProductDescription);

        restProductDescriptionMockMvc.perform(put("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescriptionDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductDescription testProductDescription = productDescriptionList.get(productDescriptionList.size() - 1);
        assertThat(testProductDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDescription() throws Exception {
        int databaseSizeBeforeUpdate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription
        ProductDescriptionDTO productDescriptionDTO = productDescriptionMapper.toDto(productDescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDescriptionMockMvc.perform(put("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDescription() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        int databaseSizeBeforeDelete = productDescriptionRepository.findAll().size();

        // Delete the productDescription
        restProductDescriptionMockMvc.perform(delete("/api/product-descriptions/{id}", productDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDescription.class);
        ProductDescription productDescription1 = new ProductDescription();
        productDescription1.setId(1L);
        ProductDescription productDescription2 = new ProductDescription();
        productDescription2.setId(productDescription1.getId());
        assertThat(productDescription1).isEqualTo(productDescription2);
        productDescription2.setId(2L);
        assertThat(productDescription1).isNotEqualTo(productDescription2);
        productDescription1.setId(null);
        assertThat(productDescription1).isNotEqualTo(productDescription2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDescriptionDTO.class);
        ProductDescriptionDTO productDescriptionDTO1 = new ProductDescriptionDTO();
        productDescriptionDTO1.setId(1L);
        ProductDescriptionDTO productDescriptionDTO2 = new ProductDescriptionDTO();
        assertThat(productDescriptionDTO1).isNotEqualTo(productDescriptionDTO2);
        productDescriptionDTO2.setId(productDescriptionDTO1.getId());
        assertThat(productDescriptionDTO1).isEqualTo(productDescriptionDTO2);
        productDescriptionDTO2.setId(2L);
        assertThat(productDescriptionDTO1).isNotEqualTo(productDescriptionDTO2);
        productDescriptionDTO1.setId(null);
        assertThat(productDescriptionDTO1).isNotEqualTo(productDescriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productDescriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productDescriptionMapper.fromId(null)).isNull();
    }
}
