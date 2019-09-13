package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductModelDescription;
import com.resource.server.repository.ProductModelDescriptionRepository;
import com.resource.server.service.ProductModelDescriptionService;
import com.resource.server.service.dto.ProductModelDescriptionDTO;
import com.resource.server.service.mapper.ProductModelDescriptionMapper;
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

import com.resource.server.domain.enumeration.Language;
/**
 * Test class for the ProductModelDescriptionResource REST controller.
 *
 * @see ProductModelDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductModelDescriptionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.MYANMAR;

    @Autowired
    private ProductModelDescriptionRepository productModelDescriptionRepository;

    @Autowired
    private ProductModelDescriptionMapper productModelDescriptionMapper;

    @Autowired
    private ProductModelDescriptionService productModelDescriptionService;

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

    private MockMvc restProductModelDescriptionMockMvc;

    private ProductModelDescription productModelDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductModelDescriptionResource productModelDescriptionResource = new ProductModelDescriptionResource(productModelDescriptionService);
        this.restProductModelDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productModelDescriptionResource)
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
    public static ProductModelDescription createEntity(EntityManager em) {
        ProductModelDescription productModelDescription = new ProductModelDescription()
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return productModelDescription;
    }

    @Before
    public void initTest() {
        productModelDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductModelDescription() throws Exception {
        int databaseSizeBeforeCreate = productModelDescriptionRepository.findAll().size();

        // Create the ProductModelDescription
        ProductModelDescriptionDTO productModelDescriptionDTO = productModelDescriptionMapper.toDto(productModelDescription);
        restProductModelDescriptionMockMvc.perform(post("/api/product-model-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productModelDescriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductModelDescription in the database
        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductModelDescription testProductModelDescription = productModelDescriptionList.get(productModelDescriptionList.size() - 1);
        assertThat(testProductModelDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductModelDescription.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createProductModelDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productModelDescriptionRepository.findAll().size();

        // Create the ProductModelDescription with an existing ID
        productModelDescription.setId(1L);
        ProductModelDescriptionDTO productModelDescriptionDTO = productModelDescriptionMapper.toDto(productModelDescription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductModelDescriptionMockMvc.perform(post("/api/product-model-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productModelDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductModelDescription in the database
        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productModelDescriptionRepository.findAll().size();
        // set the field null
        productModelDescription.setDescription(null);

        // Create the ProductModelDescription, which fails.
        ProductModelDescriptionDTO productModelDescriptionDTO = productModelDescriptionMapper.toDto(productModelDescription);

        restProductModelDescriptionMockMvc.perform(post("/api/product-model-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productModelDescriptionDTO)))
            .andExpect(status().isBadRequest());

        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductModelDescriptions() throws Exception {
        // Initialize the database
        productModelDescriptionRepository.saveAndFlush(productModelDescription);

        // Get all the productModelDescriptionList
        restProductModelDescriptionMockMvc.perform(get("/api/product-model-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productModelDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductModelDescription() throws Exception {
        // Initialize the database
        productModelDescriptionRepository.saveAndFlush(productModelDescription);

        // Get the productModelDescription
        restProductModelDescriptionMockMvc.perform(get("/api/product-model-descriptions/{id}", productModelDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productModelDescription.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductModelDescription() throws Exception {
        // Get the productModelDescription
        restProductModelDescriptionMockMvc.perform(get("/api/product-model-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductModelDescription() throws Exception {
        // Initialize the database
        productModelDescriptionRepository.saveAndFlush(productModelDescription);

        int databaseSizeBeforeUpdate = productModelDescriptionRepository.findAll().size();

        // Update the productModelDescription
        ProductModelDescription updatedProductModelDescription = productModelDescriptionRepository.findById(productModelDescription.getId()).get();
        // Disconnect from session so that the updates on updatedProductModelDescription are not directly saved in db
        em.detach(updatedProductModelDescription);
        updatedProductModelDescription
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        ProductModelDescriptionDTO productModelDescriptionDTO = productModelDescriptionMapper.toDto(updatedProductModelDescription);

        restProductModelDescriptionMockMvc.perform(put("/api/product-model-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productModelDescriptionDTO)))
            .andExpect(status().isOk());

        // Validate the ProductModelDescription in the database
        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductModelDescription testProductModelDescription = productModelDescriptionList.get(productModelDescriptionList.size() - 1);
        assertThat(testProductModelDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductModelDescription.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductModelDescription() throws Exception {
        int databaseSizeBeforeUpdate = productModelDescriptionRepository.findAll().size();

        // Create the ProductModelDescription
        ProductModelDescriptionDTO productModelDescriptionDTO = productModelDescriptionMapper.toDto(productModelDescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductModelDescriptionMockMvc.perform(put("/api/product-model-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productModelDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductModelDescription in the database
        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductModelDescription() throws Exception {
        // Initialize the database
        productModelDescriptionRepository.saveAndFlush(productModelDescription);

        int databaseSizeBeforeDelete = productModelDescriptionRepository.findAll().size();

        // Delete the productModelDescription
        restProductModelDescriptionMockMvc.perform(delete("/api/product-model-descriptions/{id}", productModelDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductModelDescription> productModelDescriptionList = productModelDescriptionRepository.findAll();
        assertThat(productModelDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductModelDescription.class);
        ProductModelDescription productModelDescription1 = new ProductModelDescription();
        productModelDescription1.setId(1L);
        ProductModelDescription productModelDescription2 = new ProductModelDescription();
        productModelDescription2.setId(productModelDescription1.getId());
        assertThat(productModelDescription1).isEqualTo(productModelDescription2);
        productModelDescription2.setId(2L);
        assertThat(productModelDescription1).isNotEqualTo(productModelDescription2);
        productModelDescription1.setId(null);
        assertThat(productModelDescription1).isNotEqualTo(productModelDescription2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductModelDescriptionDTO.class);
        ProductModelDescriptionDTO productModelDescriptionDTO1 = new ProductModelDescriptionDTO();
        productModelDescriptionDTO1.setId(1L);
        ProductModelDescriptionDTO productModelDescriptionDTO2 = new ProductModelDescriptionDTO();
        assertThat(productModelDescriptionDTO1).isNotEqualTo(productModelDescriptionDTO2);
        productModelDescriptionDTO2.setId(productModelDescriptionDTO1.getId());
        assertThat(productModelDescriptionDTO1).isEqualTo(productModelDescriptionDTO2);
        productModelDescriptionDTO2.setId(2L);
        assertThat(productModelDescriptionDTO1).isNotEqualTo(productModelDescriptionDTO2);
        productModelDescriptionDTO1.setId(null);
        assertThat(productModelDescriptionDTO1).isNotEqualTo(productModelDescriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productModelDescriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productModelDescriptionMapper.fromId(null)).isNull();
    }
}
