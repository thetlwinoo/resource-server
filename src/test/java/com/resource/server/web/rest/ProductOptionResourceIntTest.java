package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductOption;
import com.resource.server.repository.ProductOptionRepository;
import com.resource.server.service.ProductOptionService;
import com.resource.server.service.dto.ProductOptionDTO;
import com.resource.server.service.mapper.ProductOptionMapper;
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
 * Test class for the ProductOptionResource REST controller.
 *
 * @see ProductOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductOptionResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @Autowired
    private ProductOptionService productOptionService;

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

    private MockMvc restProductOptionMockMvc;

    private ProductOption productOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductOptionResource productOptionResource = new ProductOptionResource(productOptionService);
        this.restProductOptionMockMvc = MockMvcBuilders.standaloneSetup(productOptionResource)
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
    public static ProductOption createEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .value(DEFAULT_VALUE);
        return productOption;
    }

    @Before
    public void initTest() {
        productOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOption() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProductOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption with an existing ID
        productOption.setId(1L);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionRepository.findAll().size();
        // set the field null
        productOption.setValue(null);

        // Create the ProductOption, which fails.
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptions() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList
        restProductOptionMockMvc.perform(get("/api/product-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOption.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductOption() throws Exception {
        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductOption are not directly saved in db
        em.detach(updatedProductOption);
        updatedProductOption
            .value(UPDATED_VALUE);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(updatedProductOption);

        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeDelete = productOptionRepository.findAll().size();

        // Delete the productOption
        restProductOptionMockMvc.perform(delete("/api/product-options/{id}", productOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOption.class);
        ProductOption productOption1 = new ProductOption();
        productOption1.setId(1L);
        ProductOption productOption2 = new ProductOption();
        productOption2.setId(productOption1.getId());
        assertThat(productOption1).isEqualTo(productOption2);
        productOption2.setId(2L);
        assertThat(productOption1).isNotEqualTo(productOption2);
        productOption1.setId(null);
        assertThat(productOption1).isNotEqualTo(productOption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionDTO.class);
        ProductOptionDTO productOptionDTO1 = new ProductOptionDTO();
        productOptionDTO1.setId(1L);
        ProductOptionDTO productOptionDTO2 = new ProductOptionDTO();
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
        productOptionDTO2.setId(productOptionDTO1.getId());
        assertThat(productOptionDTO1).isEqualTo(productOptionDTO2);
        productOptionDTO2.setId(2L);
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
        productOptionDTO1.setId(null);
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productOptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productOptionMapper.fromId(null)).isNull();
    }
}
