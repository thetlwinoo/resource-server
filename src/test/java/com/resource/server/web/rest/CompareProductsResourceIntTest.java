package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.CompareProducts;
import com.resource.server.repository.CompareProductsRepository;
import com.resource.server.service.CompareProductsService;
import com.resource.server.service.dto.CompareProductsDTO;
import com.resource.server.service.mapper.CompareProductsMapper;
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
 * Test class for the CompareProductsResource REST controller.
 *
 * @see CompareProductsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CompareProductsResourceIntTest {

    @Autowired
    private CompareProductsRepository compareProductsRepository;

    @Autowired
    private CompareProductsMapper compareProductsMapper;

    @Autowired
    private CompareProductsService compareProductsService;

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

    private MockMvc restCompareProductsMockMvc;

    private CompareProducts compareProducts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompareProductsResource compareProductsResource = new CompareProductsResource(compareProductsService);
        this.restCompareProductsMockMvc = MockMvcBuilders.standaloneSetup(compareProductsResource)
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
    public static CompareProducts createEntity(EntityManager em) {
        CompareProducts compareProducts = new CompareProducts();
        return compareProducts;
    }

    @Before
    public void initTest() {
        compareProducts = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompareProducts() throws Exception {
        int databaseSizeBeforeCreate = compareProductsRepository.findAll().size();

        // Create the CompareProducts
        CompareProductsDTO compareProductsDTO = compareProductsMapper.toDto(compareProducts);
        restCompareProductsMockMvc.perform(post("/api/compare-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compareProductsDTO)))
            .andExpect(status().isCreated());

        // Validate the CompareProducts in the database
        List<CompareProducts> compareProductsList = compareProductsRepository.findAll();
        assertThat(compareProductsList).hasSize(databaseSizeBeforeCreate + 1);
        CompareProducts testCompareProducts = compareProductsList.get(compareProductsList.size() - 1);
    }

    @Test
    @Transactional
    public void createCompareProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compareProductsRepository.findAll().size();

        // Create the CompareProducts with an existing ID
        compareProducts.setId(1L);
        CompareProductsDTO compareProductsDTO = compareProductsMapper.toDto(compareProducts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompareProductsMockMvc.perform(post("/api/compare-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compareProductsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompareProducts in the database
        List<CompareProducts> compareProductsList = compareProductsRepository.findAll();
        assertThat(compareProductsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompareProducts() throws Exception {
        // Initialize the database
        compareProductsRepository.saveAndFlush(compareProducts);

        // Get all the compareProductsList
        restCompareProductsMockMvc.perform(get("/api/compare-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compareProducts.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompareProducts() throws Exception {
        // Initialize the database
        compareProductsRepository.saveAndFlush(compareProducts);

        // Get the compareProducts
        restCompareProductsMockMvc.perform(get("/api/compare-products/{id}", compareProducts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compareProducts.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompareProducts() throws Exception {
        // Get the compareProducts
        restCompareProductsMockMvc.perform(get("/api/compare-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompareProducts() throws Exception {
        // Initialize the database
        compareProductsRepository.saveAndFlush(compareProducts);

        int databaseSizeBeforeUpdate = compareProductsRepository.findAll().size();

        // Update the compareProducts
        CompareProducts updatedCompareProducts = compareProductsRepository.findById(compareProducts.getId()).get();
        // Disconnect from session so that the updates on updatedCompareProducts are not directly saved in db
        em.detach(updatedCompareProducts);
        CompareProductsDTO compareProductsDTO = compareProductsMapper.toDto(updatedCompareProducts);

        restCompareProductsMockMvc.perform(put("/api/compare-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compareProductsDTO)))
            .andExpect(status().isOk());

        // Validate the CompareProducts in the database
        List<CompareProducts> compareProductsList = compareProductsRepository.findAll();
        assertThat(compareProductsList).hasSize(databaseSizeBeforeUpdate);
        CompareProducts testCompareProducts = compareProductsList.get(compareProductsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompareProducts() throws Exception {
        int databaseSizeBeforeUpdate = compareProductsRepository.findAll().size();

        // Create the CompareProducts
        CompareProductsDTO compareProductsDTO = compareProductsMapper.toDto(compareProducts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompareProductsMockMvc.perform(put("/api/compare-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compareProductsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompareProducts in the database
        List<CompareProducts> compareProductsList = compareProductsRepository.findAll();
        assertThat(compareProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompareProducts() throws Exception {
        // Initialize the database
        compareProductsRepository.saveAndFlush(compareProducts);

        int databaseSizeBeforeDelete = compareProductsRepository.findAll().size();

        // Delete the compareProducts
        restCompareProductsMockMvc.perform(delete("/api/compare-products/{id}", compareProducts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompareProducts> compareProductsList = compareProductsRepository.findAll();
        assertThat(compareProductsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareProducts.class);
        CompareProducts compareProducts1 = new CompareProducts();
        compareProducts1.setId(1L);
        CompareProducts compareProducts2 = new CompareProducts();
        compareProducts2.setId(compareProducts1.getId());
        assertThat(compareProducts1).isEqualTo(compareProducts2);
        compareProducts2.setId(2L);
        assertThat(compareProducts1).isNotEqualTo(compareProducts2);
        compareProducts1.setId(null);
        assertThat(compareProducts1).isNotEqualTo(compareProducts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareProductsDTO.class);
        CompareProductsDTO compareProductsDTO1 = new CompareProductsDTO();
        compareProductsDTO1.setId(1L);
        CompareProductsDTO compareProductsDTO2 = new CompareProductsDTO();
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
        compareProductsDTO2.setId(compareProductsDTO1.getId());
        assertThat(compareProductsDTO1).isEqualTo(compareProductsDTO2);
        compareProductsDTO2.setId(2L);
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
        compareProductsDTO1.setId(null);
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compareProductsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compareProductsMapper.fromId(null)).isNull();
    }
}
