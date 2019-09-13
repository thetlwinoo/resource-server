package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductChoice;
import com.resource.server.repository.ProductChoiceRepository;
import com.resource.server.service.ProductChoiceService;
import com.resource.server.service.dto.ProductChoiceDTO;
import com.resource.server.service.mapper.ProductChoiceMapper;
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
 * Test class for the ProductChoiceResource REST controller.
 *
 * @see ProductChoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductChoiceResourceIntTest {

    private static final Boolean DEFAULT_IS_MULTIPLY = false;
    private static final Boolean UPDATED_IS_MULTIPLY = true;

    @Autowired
    private ProductChoiceRepository productChoiceRepository;

    @Autowired
    private ProductChoiceMapper productChoiceMapper;

    @Autowired
    private ProductChoiceService productChoiceService;

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

    private MockMvc restProductChoiceMockMvc;

    private ProductChoice productChoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductChoiceResource productChoiceResource = new ProductChoiceResource(productChoiceService);
        this.restProductChoiceMockMvc = MockMvcBuilders.standaloneSetup(productChoiceResource)
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
    public static ProductChoice createEntity(EntityManager em) {
        ProductChoice productChoice = new ProductChoice()
            .isMultiply(DEFAULT_IS_MULTIPLY);
        return productChoice;
    }

    @Before
    public void initTest() {
        productChoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductChoice() throws Exception {
        int databaseSizeBeforeCreate = productChoiceRepository.findAll().size();

        // Create the ProductChoice
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);
        restProductChoiceMockMvc.perform(post("/api/product-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductChoice testProductChoice = productChoiceList.get(productChoiceList.size() - 1);
        assertThat(testProductChoice.isIsMultiply()).isEqualTo(DEFAULT_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void createProductChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productChoiceRepository.findAll().size();

        // Create the ProductChoice with an existing ID
        productChoice.setId(1L);
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductChoiceMockMvc.perform(post("/api/product-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIsMultiplyIsRequired() throws Exception {
        int databaseSizeBeforeTest = productChoiceRepository.findAll().size();
        // set the field null
        productChoice.setIsMultiply(null);

        // Create the ProductChoice, which fails.
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);

        restProductChoiceMockMvc.perform(post("/api/product-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductChoices() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList
        restProductChoiceMockMvc.perform(get("/api/product-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].isMultiply").value(hasItem(DEFAULT_IS_MULTIPLY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get the productChoice
        restProductChoiceMockMvc.perform(get("/api/product-choices/{id}", productChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productChoice.getId().intValue()))
            .andExpect(jsonPath("$.isMultiply").value(DEFAULT_IS_MULTIPLY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductChoice() throws Exception {
        // Get the productChoice
        restProductChoiceMockMvc.perform(get("/api/product-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        int databaseSizeBeforeUpdate = productChoiceRepository.findAll().size();

        // Update the productChoice
        ProductChoice updatedProductChoice = productChoiceRepository.findById(productChoice.getId()).get();
        // Disconnect from session so that the updates on updatedProductChoice are not directly saved in db
        em.detach(updatedProductChoice);
        updatedProductChoice
            .isMultiply(UPDATED_IS_MULTIPLY);
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(updatedProductChoice);

        restProductChoiceMockMvc.perform(put("/api/product-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeUpdate);
        ProductChoice testProductChoice = productChoiceList.get(productChoiceList.size() - 1);
        assertThat(testProductChoice.isIsMultiply()).isEqualTo(UPDATED_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductChoice() throws Exception {
        int databaseSizeBeforeUpdate = productChoiceRepository.findAll().size();

        // Create the ProductChoice
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductChoiceMockMvc.perform(put("/api/product-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        int databaseSizeBeforeDelete = productChoiceRepository.findAll().size();

        // Delete the productChoice
        restProductChoiceMockMvc.perform(delete("/api/product-choices/{id}", productChoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductChoice.class);
        ProductChoice productChoice1 = new ProductChoice();
        productChoice1.setId(1L);
        ProductChoice productChoice2 = new ProductChoice();
        productChoice2.setId(productChoice1.getId());
        assertThat(productChoice1).isEqualTo(productChoice2);
        productChoice2.setId(2L);
        assertThat(productChoice1).isNotEqualTo(productChoice2);
        productChoice1.setId(null);
        assertThat(productChoice1).isNotEqualTo(productChoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductChoiceDTO.class);
        ProductChoiceDTO productChoiceDTO1 = new ProductChoiceDTO();
        productChoiceDTO1.setId(1L);
        ProductChoiceDTO productChoiceDTO2 = new ProductChoiceDTO();
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
        productChoiceDTO2.setId(productChoiceDTO1.getId());
        assertThat(productChoiceDTO1).isEqualTo(productChoiceDTO2);
        productChoiceDTO2.setId(2L);
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
        productChoiceDTO1.setId(null);
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productChoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productChoiceMapper.fromId(null)).isNull();
    }
}
