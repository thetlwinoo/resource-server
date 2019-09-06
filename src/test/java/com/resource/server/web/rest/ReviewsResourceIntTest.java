package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Reviews;
import com.resource.server.repository.ReviewsRepository;
import com.resource.server.service.ReviewsService;
import com.resource.server.service.dto.ReviewsDTO;
import com.resource.server.service.mapper.ReviewsMapper;
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
 * Test class for the ReviewsResource REST controller.
 *
 * @see ReviewsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ReviewsResourceIntTest {

    private static final String DEFAULT_REVIEWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_OVER_ALL_SELLER_RATING = 1;
    private static final Integer UPDATED_OVER_ALL_SELLER_RATING = 2;

    private static final String DEFAULT_OVER_ALL_SELLER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVER_ALL_SELLER_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_OVER_ALL_DELIVERY_RATING = 1;
    private static final Integer UPDATED_OVER_ALL_DELIVERY_RATING = 2;

    private static final String DEFAULT_OVER_ALL_DELIVERY_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVER_ALL_DELIVERY_REVIEW = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REVIEW_AS_ANONYMOUS = false;
    private static final Boolean UPDATED_REVIEW_AS_ANONYMOUS = true;

    private static final Boolean DEFAULT_COMPLETED_REVIEW = false;
    private static final Boolean UPDATED_COMPLETED_REVIEW = true;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private ReviewsService reviewsService;

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

    private MockMvc restReviewsMockMvc;

    private Reviews reviews;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewsResource reviewsResource = new ReviewsResource(reviewsService);
        this.restReviewsMockMvc = MockMvcBuilders.standaloneSetup(reviewsResource)
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
    public static Reviews createEntity(EntityManager em) {
        Reviews reviews = new Reviews()
            .reviewerName(DEFAULT_REVIEWER_NAME)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .overAllSellerRating(DEFAULT_OVER_ALL_SELLER_RATING)
            .overAllSellerReview(DEFAULT_OVER_ALL_SELLER_REVIEW)
            .overAllDeliveryRating(DEFAULT_OVER_ALL_DELIVERY_RATING)
            .overAllDeliveryReview(DEFAULT_OVER_ALL_DELIVERY_REVIEW)
            .reviewAsAnonymous(DEFAULT_REVIEW_AS_ANONYMOUS)
            .completedReview(DEFAULT_COMPLETED_REVIEW);
        return reviews;
    }

    @Before
    public void initTest() {
        reviews = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviews() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // Create the Reviews
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isCreated());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate + 1);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getReviewerName()).isEqualTo(DEFAULT_REVIEWER_NAME);
        assertThat(testReviews.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testReviews.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testReviews.getOverAllSellerRating()).isEqualTo(DEFAULT_OVER_ALL_SELLER_RATING);
        assertThat(testReviews.getOverAllSellerReview()).isEqualTo(DEFAULT_OVER_ALL_SELLER_REVIEW);
        assertThat(testReviews.getOverAllDeliveryRating()).isEqualTo(DEFAULT_OVER_ALL_DELIVERY_RATING);
        assertThat(testReviews.getOverAllDeliveryReview()).isEqualTo(DEFAULT_OVER_ALL_DELIVERY_REVIEW);
        assertThat(testReviews.isReviewAsAnonymous()).isEqualTo(DEFAULT_REVIEW_AS_ANONYMOUS);
        assertThat(testReviews.isCompletedReview()).isEqualTo(DEFAULT_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void createReviewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // Create the Reviews with an existing ID
        reviews.setId(1L);
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList
        restReviewsMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewerName").value(hasItem(DEFAULT_REVIEWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].overAllSellerRating").value(hasItem(DEFAULT_OVER_ALL_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].overAllSellerReview").value(hasItem(DEFAULT_OVER_ALL_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].overAllDeliveryRating").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].overAllDeliveryReview").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].reviewAsAnonymous").value(hasItem(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].completedReview").value(hasItem(DEFAULT_COMPLETED_REVIEW.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", reviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviews.getId().intValue()))
            .andExpect(jsonPath("$.reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.overAllSellerRating").value(DEFAULT_OVER_ALL_SELLER_RATING))
            .andExpect(jsonPath("$.overAllSellerReview").value(DEFAULT_OVER_ALL_SELLER_REVIEW.toString()))
            .andExpect(jsonPath("$.overAllDeliveryRating").value(DEFAULT_OVER_ALL_DELIVERY_RATING))
            .andExpect(jsonPath("$.overAllDeliveryReview").value(DEFAULT_OVER_ALL_DELIVERY_REVIEW.toString()))
            .andExpect(jsonPath("$.reviewAsAnonymous").value(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue()))
            .andExpect(jsonPath("$.completedReview").value(DEFAULT_COMPLETED_REVIEW.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReviews() throws Exception {
        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews
        Reviews updatedReviews = reviewsRepository.findById(reviews.getId()).get();
        // Disconnect from session so that the updates on updatedReviews are not directly saved in db
        em.detach(updatedReviews);
        updatedReviews
            .reviewerName(UPDATED_REVIEWER_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .reviewDate(UPDATED_REVIEW_DATE)
            .overAllSellerRating(UPDATED_OVER_ALL_SELLER_RATING)
            .overAllSellerReview(UPDATED_OVER_ALL_SELLER_REVIEW)
            .overAllDeliveryRating(UPDATED_OVER_ALL_DELIVERY_RATING)
            .overAllDeliveryReview(UPDATED_OVER_ALL_DELIVERY_REVIEW)
            .reviewAsAnonymous(UPDATED_REVIEW_AS_ANONYMOUS)
            .completedReview(UPDATED_COMPLETED_REVIEW);
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(updatedReviews);

        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getReviewerName()).isEqualTo(UPDATED_REVIEWER_NAME);
        assertThat(testReviews.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testReviews.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testReviews.getOverAllSellerRating()).isEqualTo(UPDATED_OVER_ALL_SELLER_RATING);
        assertThat(testReviews.getOverAllSellerReview()).isEqualTo(UPDATED_OVER_ALL_SELLER_REVIEW);
        assertThat(testReviews.getOverAllDeliveryRating()).isEqualTo(UPDATED_OVER_ALL_DELIVERY_RATING);
        assertThat(testReviews.getOverAllDeliveryReview()).isEqualTo(UPDATED_OVER_ALL_DELIVERY_REVIEW);
        assertThat(testReviews.isReviewAsAnonymous()).isEqualTo(UPDATED_REVIEW_AS_ANONYMOUS);
        assertThat(testReviews.isCompletedReview()).isEqualTo(UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void updateNonExistingReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Create the Reviews
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeDelete = reviewsRepository.findAll().size();

        // Delete the reviews
        restReviewsMockMvc.perform(delete("/api/reviews/{id}", reviews.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reviews.class);
        Reviews reviews1 = new Reviews();
        reviews1.setId(1L);
        Reviews reviews2 = new Reviews();
        reviews2.setId(reviews1.getId());
        assertThat(reviews1).isEqualTo(reviews2);
        reviews2.setId(2L);
        assertThat(reviews1).isNotEqualTo(reviews2);
        reviews1.setId(null);
        assertThat(reviews1).isNotEqualTo(reviews2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewsDTO.class);
        ReviewsDTO reviewsDTO1 = new ReviewsDTO();
        reviewsDTO1.setId(1L);
        ReviewsDTO reviewsDTO2 = new ReviewsDTO();
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO2.setId(reviewsDTO1.getId());
        assertThat(reviewsDTO1).isEqualTo(reviewsDTO2);
        reviewsDTO2.setId(2L);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO1.setId(null);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reviewsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reviewsMapper.fromId(null)).isNull();
    }
}
