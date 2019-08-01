package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.CustomerCategories;
import com.resource.server.repository.CustomerCategoriesRepository;
import com.resource.server.service.CustomerCategoriesService;
import com.resource.server.service.dto.CustomerCategoriesDTO;
import com.resource.server.service.mapper.CustomerCategoriesMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerCategoriesResource REST controller.
 *
 * @see CustomerCategoriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CustomerCategoriesResourceIntTest {

    private static final String DEFAULT_CUSTOMER_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CATEGORY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CustomerCategoriesRepository customerCategoriesRepository;

    @Autowired
    private CustomerCategoriesMapper customerCategoriesMapper;

    @Autowired
    private CustomerCategoriesService customerCategoriesService;

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

    private MockMvc restCustomerCategoriesMockMvc;

    private CustomerCategories customerCategories;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCategoriesResource customerCategoriesResource = new CustomerCategoriesResource(customerCategoriesService);
        this.restCustomerCategoriesMockMvc = MockMvcBuilders.standaloneSetup(customerCategoriesResource)
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
    public static CustomerCategories createEntity(EntityManager em) {
        CustomerCategories customerCategories = new CustomerCategories()
            .customerCategoryName(DEFAULT_CUSTOMER_CATEGORY_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return customerCategories;
    }

    @Before
    public void initTest() {
        customerCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCategories() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getCustomerCategoryName()).isEqualTo(DEFAULT_CUSTOMER_CATEGORY_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCustomerCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories with an existing ID
        customerCategories.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidFrom(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidTo(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerCategoryName").value(hasItem(DEFAULT_CUSTOMER_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", customerCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCategories.getId().intValue()))
            .andExpect(jsonPath("$.customerCategoryName").value(DEFAULT_CUSTOMER_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerCategories() throws Exception {
        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Update the customerCategories
        CustomerCategories updatedCustomerCategories = customerCategoriesRepository.findById(customerCategories.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerCategories are not directly saved in db
        em.detach(updatedCustomerCategories);
        updatedCustomerCategories
            .customerCategoryName(UPDATED_CUSTOMER_CATEGORY_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(updatedCustomerCategories);

        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getCustomerCategoryName()).isEqualTo(UPDATED_CUSTOMER_CATEGORY_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCategories() throws Exception {
        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeDelete = customerCategoriesRepository.findAll().size();

        // Delete the customerCategories
        restCustomerCategoriesMockMvc.perform(delete("/api/customer-categories/{id}", customerCategories.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategories.class);
        CustomerCategories customerCategories1 = new CustomerCategories();
        customerCategories1.setId(1L);
        CustomerCategories customerCategories2 = new CustomerCategories();
        customerCategories2.setId(customerCategories1.getId());
        assertThat(customerCategories1).isEqualTo(customerCategories2);
        customerCategories2.setId(2L);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
        customerCategories1.setId(null);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategoriesDTO.class);
        CustomerCategoriesDTO customerCategoriesDTO1 = new CustomerCategoriesDTO();
        customerCategoriesDTO1.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO2 = new CustomerCategoriesDTO();
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(customerCategoriesDTO1.getId());
        assertThat(customerCategoriesDTO1).isEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(2L);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO1.setId(null);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerCategoriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerCategoriesMapper.fromId(null)).isNull();
    }
}
