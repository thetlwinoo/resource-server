package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ReviewLines;
import com.resource.server.repository.ReviewLinesRepository;
import com.resource.server.service.ReviewLinesService;
import com.resource.server.service.dto.ReviewLinesDTO;
import com.resource.server.service.mapper.ReviewLinesMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReviewLinesResource REST controller.
 *
 * @see ReviewLinesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ReviewLinesResourceIntTest {

    private static final Integer DEFAULT_PRODUCT_RATING = 1;
    private static final Integer UPDATED_PRODUCT_RATING = 2;

    private static final String DEFAULT_PRODUCT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELLER_RATING = 1;
    private static final Integer UPDATED_SELLER_RATING = 2;

    private static final String DEFAULT_SELLER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELIVERY_RATING = 1;
    private static final Integer UPDATED_DELIVERY_RATING = 2;

    private static final String DEFAULT_DELIVERY_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_REVIEW = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private ReviewLinesRepository reviewLinesRepository;

    @Autowired
    private ReviewLinesMapper reviewLinesMapper;

    @Autowired
    private ReviewLinesService reviewLinesService;

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

    private MockMvc restReviewLinesMockMvc;

    private ReviewLines reviewLines;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewLinesResource reviewLinesResource = new ReviewLinesResource(reviewLinesService);
        this.restReviewLinesMockMvc = MockMvcBuilders.standaloneSetup(reviewLinesResource)
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
    public static ReviewLines createEntity(EntityManager em) {
        ReviewLines reviewLines = new ReviewLines()
            .productRating(DEFAULT_PRODUCT_RATING)
            .productReview(DEFAULT_PRODUCT_REVIEW)
            .sellerRating(DEFAULT_SELLER_RATING)
            .sellerReview(DEFAULT_SELLER_REVIEW)
            .deliveryRating(DEFAULT_DELIVERY_RATING)
            .deliveryReview(DEFAULT_DELIVERY_REVIEW)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return reviewLines;
    }

    @Before
    public void initTest() {
        reviewLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewLines() throws Exception {
        int databaseSizeBeforeCreate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);
        restReviewLinesMockMvc.perform(post("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewLines testReviewLines = reviewLinesList.get(reviewLinesList.size() - 1);
        assertThat(testReviewLines.getProductRating()).isEqualTo(DEFAULT_PRODUCT_RATING);
        assertThat(testReviewLines.getProductReview()).isEqualTo(DEFAULT_PRODUCT_REVIEW);
        assertThat(testReviewLines.getSellerRating()).isEqualTo(DEFAULT_SELLER_RATING);
        assertThat(testReviewLines.getSellerReview()).isEqualTo(DEFAULT_SELLER_REVIEW);
        assertThat(testReviewLines.getDeliveryRating()).isEqualTo(DEFAULT_DELIVERY_RATING);
        assertThat(testReviewLines.getDeliveryReview()).isEqualTo(DEFAULT_DELIVERY_REVIEW);
        assertThat(testReviewLines.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testReviewLines.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createReviewLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines with an existing ID
        reviewLines.setId(1L);
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewLinesMockMvc.perform(post("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList
        restReviewLinesMockMvc.perform(get("/api/review-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].productRating").value(hasItem(DEFAULT_PRODUCT_RATING)))
            .andExpect(jsonPath("$.[*].productReview").value(hasItem(DEFAULT_PRODUCT_REVIEW)))
            .andExpect(jsonPath("$.[*].sellerRating").value(hasItem(DEFAULT_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].sellerReview").value(hasItem(DEFAULT_SELLER_REVIEW)))
            .andExpect(jsonPath("$.[*].deliveryRating").value(hasItem(DEFAULT_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].deliveryReview").value(hasItem(DEFAULT_DELIVERY_REVIEW)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get the reviewLines
        restReviewLinesMockMvc.perform(get("/api/review-lines/{id}", reviewLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviewLines.getId().intValue()))
            .andExpect(jsonPath("$.productRating").value(DEFAULT_PRODUCT_RATING))
            .andExpect(jsonPath("$.productReview").value(DEFAULT_PRODUCT_REVIEW))
            .andExpect(jsonPath("$.sellerRating").value(DEFAULT_SELLER_RATING))
            .andExpect(jsonPath("$.sellerReview").value(DEFAULT_SELLER_REVIEW))
            .andExpect(jsonPath("$.deliveryRating").value(DEFAULT_DELIVERY_RATING))
            .andExpect(jsonPath("$.deliveryReview").value(DEFAULT_DELIVERY_REVIEW))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingReviewLines() throws Exception {
        // Get the reviewLines
        restReviewLinesMockMvc.perform(get("/api/review-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        int databaseSizeBeforeUpdate = reviewLinesRepository.findAll().size();

        // Update the reviewLines
        ReviewLines updatedReviewLines = reviewLinesRepository.findById(reviewLines.getId()).get();
        // Disconnect from session so that the updates on updatedReviewLines are not directly saved in db
        em.detach(updatedReviewLines);
        updatedReviewLines
            .productRating(UPDATED_PRODUCT_RATING)
            .productReview(UPDATED_PRODUCT_REVIEW)
            .sellerRating(UPDATED_SELLER_RATING)
            .sellerReview(UPDATED_SELLER_REVIEW)
            .deliveryRating(UPDATED_DELIVERY_RATING)
            .deliveryReview(UPDATED_DELIVERY_REVIEW)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(updatedReviewLines);

        restReviewLinesMockMvc.perform(put("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeUpdate);
        ReviewLines testReviewLines = reviewLinesList.get(reviewLinesList.size() - 1);
        assertThat(testReviewLines.getProductRating()).isEqualTo(UPDATED_PRODUCT_RATING);
        assertThat(testReviewLines.getProductReview()).isEqualTo(UPDATED_PRODUCT_REVIEW);
        assertThat(testReviewLines.getSellerRating()).isEqualTo(UPDATED_SELLER_RATING);
        assertThat(testReviewLines.getSellerReview()).isEqualTo(UPDATED_SELLER_REVIEW);
        assertThat(testReviewLines.getDeliveryRating()).isEqualTo(UPDATED_DELIVERY_RATING);
        assertThat(testReviewLines.getDeliveryReview()).isEqualTo(UPDATED_DELIVERY_REVIEW);
        assertThat(testReviewLines.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testReviewLines.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewLines() throws Exception {
        int databaseSizeBeforeUpdate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewLinesMockMvc.perform(put("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        int databaseSizeBeforeDelete = reviewLinesRepository.findAll().size();

        // Delete the reviewLines
        restReviewLinesMockMvc.perform(delete("/api/review-lines/{id}", reviewLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewLines.class);
        ReviewLines reviewLines1 = new ReviewLines();
        reviewLines1.setId(1L);
        ReviewLines reviewLines2 = new ReviewLines();
        reviewLines2.setId(reviewLines1.getId());
        assertThat(reviewLines1).isEqualTo(reviewLines2);
        reviewLines2.setId(2L);
        assertThat(reviewLines1).isNotEqualTo(reviewLines2);
        reviewLines1.setId(null);
        assertThat(reviewLines1).isNotEqualTo(reviewLines2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewLinesDTO.class);
        ReviewLinesDTO reviewLinesDTO1 = new ReviewLinesDTO();
        reviewLinesDTO1.setId(1L);
        ReviewLinesDTO reviewLinesDTO2 = new ReviewLinesDTO();
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
        reviewLinesDTO2.setId(reviewLinesDTO1.getId());
        assertThat(reviewLinesDTO1).isEqualTo(reviewLinesDTO2);
        reviewLinesDTO2.setId(2L);
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
        reviewLinesDTO1.setId(null);
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reviewLinesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reviewLinesMapper.fromId(null)).isNull();
    }
}
