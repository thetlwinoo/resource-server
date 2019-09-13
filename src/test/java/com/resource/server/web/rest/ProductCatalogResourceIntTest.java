package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductCatalog;
import com.resource.server.repository.ProductCatalogRepository;
import com.resource.server.service.ProductCatalogService;
import com.resource.server.service.dto.ProductCatalogDTO;
import com.resource.server.service.mapper.ProductCatalogMapper;
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
 * Test class for the ProductCatalogResource REST controller.
 *
 * @see ProductCatalogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductCatalogResourceIntTest {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductCatalogMapper productCatalogMapper;

    @Autowired
    private ProductCatalogService productCatalogService;

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

    private MockMvc restProductCatalogMockMvc;

    private ProductCatalog productCatalog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCatalogResource productCatalogResource = new ProductCatalogResource(productCatalogService);
        this.restProductCatalogMockMvc = MockMvcBuilders.standaloneSetup(productCatalogResource)
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
    public static ProductCatalog createEntity(EntityManager em) {
        ProductCatalog productCatalog = new ProductCatalog();
        return productCatalog;
    }

    @Before
    public void initTest() {
        productCatalog = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCatalog() throws Exception {
        int databaseSizeBeforeCreate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);
        restProductCatalogMockMvc.perform(post("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCatalog testProductCatalog = productCatalogList.get(productCatalogList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog with an existing ID
        productCatalog.setId(1L);
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCatalogMockMvc.perform(post("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductCatalogs() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        // Get all the productCatalogList
        restProductCatalogMockMvc.perform(get("/api/product-catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCatalog.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        // Get the productCatalog
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/{id}", productCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCatalog.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductCatalog() throws Exception {
        // Get the productCatalog
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        int databaseSizeBeforeUpdate = productCatalogRepository.findAll().size();

        // Update the productCatalog
        ProductCatalog updatedProductCatalog = productCatalogRepository.findById(productCatalog.getId()).get();
        // Disconnect from session so that the updates on updatedProductCatalog are not directly saved in db
        em.detach(updatedProductCatalog);
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(updatedProductCatalog);

        restProductCatalogMockMvc.perform(put("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeUpdate);
        ProductCatalog testProductCatalog = productCatalogList.get(productCatalogList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCatalog() throws Exception {
        int databaseSizeBeforeUpdate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCatalogMockMvc.perform(put("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        int databaseSizeBeforeDelete = productCatalogRepository.findAll().size();

        // Delete the productCatalog
        restProductCatalogMockMvc.perform(delete("/api/product-catalogs/{id}", productCatalog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCatalog.class);
        ProductCatalog productCatalog1 = new ProductCatalog();
        productCatalog1.setId(1L);
        ProductCatalog productCatalog2 = new ProductCatalog();
        productCatalog2.setId(productCatalog1.getId());
        assertThat(productCatalog1).isEqualTo(productCatalog2);
        productCatalog2.setId(2L);
        assertThat(productCatalog1).isNotEqualTo(productCatalog2);
        productCatalog1.setId(null);
        assertThat(productCatalog1).isNotEqualTo(productCatalog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCatalogDTO.class);
        ProductCatalogDTO productCatalogDTO1 = new ProductCatalogDTO();
        productCatalogDTO1.setId(1L);
        ProductCatalogDTO productCatalogDTO2 = new ProductCatalogDTO();
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
        productCatalogDTO2.setId(productCatalogDTO1.getId());
        assertThat(productCatalogDTO1).isEqualTo(productCatalogDTO2);
        productCatalogDTO2.setId(2L);
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
        productCatalogDTO1.setId(null);
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productCatalogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productCatalogMapper.fromId(null)).isNull();
    }
}
