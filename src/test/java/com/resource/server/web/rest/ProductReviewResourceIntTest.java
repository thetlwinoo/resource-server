package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductReview;
import com.resource.server.repository.ProductReviewRepository;
import com.resource.server.service.ProductReviewService;
import com.resource.server.service.dto.ProductReviewDTO;
import com.resource.server.service.mapper.ProductReviewMapper;
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
 * Test class for the ProductReviewResource REST controller.
 *
 * @see ProductReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductReviewResourceIntTest {

    private static final String DEFAULT_REVIEWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductReviewMapper productReviewMapper;

    @Autowired
    private ProductReviewService productReviewService;

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

    private MockMvc restProductReviewMockMvc;

    private ProductReview productReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductReviewResource productReviewResource = new ProductReviewResource(productReviewService);
        this.restProductReviewMockMvc = MockMvcBuilders.standaloneSetup(productReviewResource)
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
    public static ProductReview createEntity(EntityManager em) {
        ProductReview productReview = new ProductReview()
            .reviewerName(DEFAULT_REVIEWER_NAME)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .rating(DEFAULT_RATING)
            .comments(DEFAULT_COMMENTS);
        return productReview;
    }

    @Before
    public void initTest() {
        productReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductReview() throws Exception {
        int databaseSizeBeforeCreate = productReviewRepository.findAll().size();

        // Create the ProductReview
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);
        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeCreate + 1);
        ProductReview testProductReview = productReviewList.get(productReviewList.size() - 1);
        assertThat(testProductReview.getReviewerName()).isEqualTo(DEFAULT_REVIEWER_NAME);
        assertThat(testProductReview.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testProductReview.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testProductReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testProductReview.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createProductReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productReviewRepository.findAll().size();

        // Create the ProductReview with an existing ID
        productReview.setId(1L);
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReviewerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReviewRepository.findAll().size();
        // set the field null
        productReview.setReviewerName(null);

        // Create the ProductReview, which fails.
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReviewRepository.findAll().size();
        // set the field null
        productReview.setReviewDate(null);

        // Create the ProductReview, which fails.
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReviewRepository.findAll().size();
        // set the field null
        productReview.setEmailAddress(null);

        // Create the ProductReview, which fails.
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReviewRepository.findAll().size();
        // set the field null
        productReview.setRating(null);

        // Create the ProductReview, which fails.
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductReviews() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        // Get all the productReviewList
        restProductReviewMockMvc.perform(get("/api/product-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewerName").value(hasItem(DEFAULT_REVIEWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }
    
    @Test
    @Transactional
    public void getProductReview() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        // Get the productReview
        restProductReviewMockMvc.perform(get("/api/product-reviews/{id}", productReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productReview.getId().intValue()))
            .andExpect(jsonPath("$.reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductReview() throws Exception {
        // Get the productReview
        restProductReviewMockMvc.perform(get("/api/product-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductReview() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        int databaseSizeBeforeUpdate = productReviewRepository.findAll().size();

        // Update the productReview
        ProductReview updatedProductReview = productReviewRepository.findById(productReview.getId()).get();
        // Disconnect from session so that the updates on updatedProductReview are not directly saved in db
        em.detach(updatedProductReview);
        updatedProductReview
            .reviewerName(UPDATED_REVIEWER_NAME)
            .reviewDate(UPDATED_REVIEW_DATE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .rating(UPDATED_RATING)
            .comments(UPDATED_COMMENTS);
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(updatedProductReview);

        restProductReviewMockMvc.perform(put("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isOk());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeUpdate);
        ProductReview testProductReview = productReviewList.get(productReviewList.size() - 1);
        assertThat(testProductReview.getReviewerName()).isEqualTo(UPDATED_REVIEWER_NAME);
        assertThat(testProductReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testProductReview.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testProductReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testProductReview.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingProductReview() throws Exception {
        int databaseSizeBeforeUpdate = productReviewRepository.findAll().size();

        // Create the ProductReview
        ProductReviewDTO productReviewDTO = productReviewMapper.toDto(productReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductReviewMockMvc.perform(put("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductReview() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        int databaseSizeBeforeDelete = productReviewRepository.findAll().size();

        // Delete the productReview
        restProductReviewMockMvc.perform(delete("/api/product-reviews/{id}", productReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReview.class);
        ProductReview productReview1 = new ProductReview();
        productReview1.setId(1L);
        ProductReview productReview2 = new ProductReview();
        productReview2.setId(productReview1.getId());
        assertThat(productReview1).isEqualTo(productReview2);
        productReview2.setId(2L);
        assertThat(productReview1).isNotEqualTo(productReview2);
        productReview1.setId(null);
        assertThat(productReview1).isNotEqualTo(productReview2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReviewDTO.class);
        ProductReviewDTO productReviewDTO1 = new ProductReviewDTO();
        productReviewDTO1.setId(1L);
        ProductReviewDTO productReviewDTO2 = new ProductReviewDTO();
        assertThat(productReviewDTO1).isNotEqualTo(productReviewDTO2);
        productReviewDTO2.setId(productReviewDTO1.getId());
        assertThat(productReviewDTO1).isEqualTo(productReviewDTO2);
        productReviewDTO2.setId(2L);
        assertThat(productReviewDTO1).isNotEqualTo(productReviewDTO2);
        productReviewDTO1.setId(null);
        assertThat(productReviewDTO1).isNotEqualTo(productReviewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productReviewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productReviewMapper.fromId(null)).isNull();
    }
}
