package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductPhoto;
import com.resource.server.repository.ProductPhotoRepository;
import com.resource.server.service.ProductPhotoService;
import com.resource.server.service.dto.ProductPhotoDTO;
import com.resource.server.service.mapper.ProductPhotoMapper;
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
 * Test class for the ProductPhotoResource REST controller.
 *
 * @see ProductPhotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductPhotoResourceIntTest {

    private static final String DEFAULT_THUMBNAIL_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_TALL_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TALL_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_WIDE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_WIDE_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_CIRCLE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_CIRCLE_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_SHARPENED_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_SHARPENED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_SQUARE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_SQUARE_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_WATERMARK_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_WATERMARK_PHOTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final String DEFAULT_DELETE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_DELETE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private ProductPhotoMapper productPhotoMapper;

    @Autowired
    private ProductPhotoService productPhotoService;

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

    private MockMvc restProductPhotoMockMvc;

    private ProductPhoto productPhoto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductPhotoResource productPhotoResource = new ProductPhotoResource(productPhotoService);
        this.restProductPhotoMockMvc = MockMvcBuilders.standaloneSetup(productPhotoResource)
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
    public static ProductPhoto createEntity(EntityManager em) {
        ProductPhoto productPhoto = new ProductPhoto()
            .thumbnailPhoto(DEFAULT_THUMBNAIL_PHOTO)
            .originalPhoto(DEFAULT_ORIGINAL_PHOTO)
            .bannerTallPhoto(DEFAULT_BANNER_TALL_PHOTO)
            .bannerWidePhoto(DEFAULT_BANNER_WIDE_PHOTO)
            .circlePhoto(DEFAULT_CIRCLE_PHOTO)
            .sharpenedPhoto(DEFAULT_SHARPENED_PHOTO)
            .squarePhoto(DEFAULT_SQUARE_PHOTO)
            .watermarkPhoto(DEFAULT_WATERMARK_PHOTO)
            .priority(DEFAULT_PRIORITY)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .deleteToken(DEFAULT_DELETE_TOKEN);
        return productPhoto;
    }

    @Before
    public void initTest() {
        productPhoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPhoto() throws Exception {
        int databaseSizeBeforeCreate = productPhotoRepository.findAll().size();

        // Create the ProductPhoto
        ProductPhotoDTO productPhotoDTO = productPhotoMapper.toDto(productPhoto);
        restProductPhotoMockMvc.perform(post("/api/product-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPhotoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductPhoto in the database
        List<ProductPhoto> productPhotoList = productPhotoRepository.findAll();
        assertThat(productPhotoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPhoto testProductPhoto = productPhotoList.get(productPhotoList.size() - 1);
        assertThat(testProductPhoto.getThumbnailPhoto()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO);
        assertThat(testProductPhoto.getOriginalPhoto()).isEqualTo(DEFAULT_ORIGINAL_PHOTO);
        assertThat(testProductPhoto.getBannerTallPhoto()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO);
        assertThat(testProductPhoto.getBannerWidePhoto()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO);
        assertThat(testProductPhoto.getCirclePhoto()).isEqualTo(DEFAULT_CIRCLE_PHOTO);
        assertThat(testProductPhoto.getSharpenedPhoto()).isEqualTo(DEFAULT_SHARPENED_PHOTO);
        assertThat(testProductPhoto.getSquarePhoto()).isEqualTo(DEFAULT_SQUARE_PHOTO);
        assertThat(testProductPhoto.getWatermarkPhoto()).isEqualTo(DEFAULT_WATERMARK_PHOTO);
        assertThat(testProductPhoto.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProductPhoto.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testProductPhoto.getDeleteToken()).isEqualTo(DEFAULT_DELETE_TOKEN);
    }

    @Test
    @Transactional
    public void createProductPhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPhotoRepository.findAll().size();

        // Create the ProductPhoto with an existing ID
        productPhoto.setId(1L);
        ProductPhotoDTO productPhotoDTO = productPhotoMapper.toDto(productPhoto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPhotoMockMvc.perform(post("/api/product-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPhotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPhoto in the database
        List<ProductPhoto> productPhotoList = productPhotoRepository.findAll();
        assertThat(productPhotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductPhotos() throws Exception {
        // Initialize the database
        productPhotoRepository.saveAndFlush(productPhoto);

        // Get all the productPhotoList
        restProductPhotoMockMvc.perform(get("/api/product-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnailPhoto").value(hasItem(DEFAULT_THUMBNAIL_PHOTO)))
            .andExpect(jsonPath("$.[*].originalPhoto").value(hasItem(DEFAULT_ORIGINAL_PHOTO)))
            .andExpect(jsonPath("$.[*].bannerTallPhoto").value(hasItem(DEFAULT_BANNER_TALL_PHOTO)))
            .andExpect(jsonPath("$.[*].bannerWidePhoto").value(hasItem(DEFAULT_BANNER_WIDE_PHOTO)))
            .andExpect(jsonPath("$.[*].circlePhoto").value(hasItem(DEFAULT_CIRCLE_PHOTO)))
            .andExpect(jsonPath("$.[*].sharpenedPhoto").value(hasItem(DEFAULT_SHARPENED_PHOTO)))
            .andExpect(jsonPath("$.[*].squarePhoto").value(hasItem(DEFAULT_SQUARE_PHOTO)))
            .andExpect(jsonPath("$.[*].watermarkPhoto").value(hasItem(DEFAULT_WATERMARK_PHOTO)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].deleteToken").value(hasItem(DEFAULT_DELETE_TOKEN)));
    }

    @Test
    @Transactional
    public void getProductPhoto() throws Exception {
        // Initialize the database
        productPhotoRepository.saveAndFlush(productPhoto);

        // Get the productPhoto
        restProductPhotoMockMvc.perform(get("/api/product-photos/{id}", productPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productPhoto.getId().intValue()))
            .andExpect(jsonPath("$.thumbnailPhoto").value(DEFAULT_THUMBNAIL_PHOTO))
            .andExpect(jsonPath("$.originalPhoto").value(DEFAULT_ORIGINAL_PHOTO))
            .andExpect(jsonPath("$.bannerTallPhoto").value(DEFAULT_BANNER_TALL_PHOTO))
            .andExpect(jsonPath("$.bannerWidePhoto").value(DEFAULT_BANNER_WIDE_PHOTO))
            .andExpect(jsonPath("$.circlePhoto").value(DEFAULT_CIRCLE_PHOTO))
            .andExpect(jsonPath("$.sharpenedPhoto").value(DEFAULT_SHARPENED_PHOTO))
            .andExpect(jsonPath("$.squarePhoto").value(DEFAULT_SQUARE_PHOTO))
            .andExpect(jsonPath("$.watermarkPhoto").value(DEFAULT_WATERMARK_PHOTO))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.deleteToken").value(DEFAULT_DELETE_TOKEN));
    }

    @Test
    @Transactional
    public void getNonExistingProductPhoto() throws Exception {
        // Get the productPhoto
        restProductPhotoMockMvc.perform(get("/api/product-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPhoto() throws Exception {
        // Initialize the database
        productPhotoRepository.saveAndFlush(productPhoto);

        int databaseSizeBeforeUpdate = productPhotoRepository.findAll().size();

        // Update the productPhoto
        ProductPhoto updatedProductPhoto = productPhotoRepository.findById(productPhoto.getId()).get();
        // Disconnect from session so that the updates on updatedProductPhoto are not directly saved in db
        em.detach(updatedProductPhoto);
        updatedProductPhoto
            .thumbnailPhoto(UPDATED_THUMBNAIL_PHOTO)
            .originalPhoto(UPDATED_ORIGINAL_PHOTO)
            .bannerTallPhoto(UPDATED_BANNER_TALL_PHOTO)
            .bannerWidePhoto(UPDATED_BANNER_WIDE_PHOTO)
            .circlePhoto(UPDATED_CIRCLE_PHOTO)
            .sharpenedPhoto(UPDATED_SHARPENED_PHOTO)
            .squarePhoto(UPDATED_SQUARE_PHOTO)
            .watermarkPhoto(UPDATED_WATERMARK_PHOTO)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND)
            .deleteToken(UPDATED_DELETE_TOKEN);
        ProductPhotoDTO productPhotoDTO = productPhotoMapper.toDto(updatedProductPhoto);

        restProductPhotoMockMvc.perform(put("/api/product-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPhotoDTO)))
            .andExpect(status().isOk());

        // Validate the ProductPhoto in the database
        List<ProductPhoto> productPhotoList = productPhotoRepository.findAll();
        assertThat(productPhotoList).hasSize(databaseSizeBeforeUpdate);
        ProductPhoto testProductPhoto = productPhotoList.get(productPhotoList.size() - 1);
        assertThat(testProductPhoto.getThumbnailPhoto()).isEqualTo(UPDATED_THUMBNAIL_PHOTO);
        assertThat(testProductPhoto.getOriginalPhoto()).isEqualTo(UPDATED_ORIGINAL_PHOTO);
        assertThat(testProductPhoto.getBannerTallPhoto()).isEqualTo(UPDATED_BANNER_TALL_PHOTO);
        assertThat(testProductPhoto.getBannerWidePhoto()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO);
        assertThat(testProductPhoto.getCirclePhoto()).isEqualTo(UPDATED_CIRCLE_PHOTO);
        assertThat(testProductPhoto.getSharpenedPhoto()).isEqualTo(UPDATED_SHARPENED_PHOTO);
        assertThat(testProductPhoto.getSquarePhoto()).isEqualTo(UPDATED_SQUARE_PHOTO);
        assertThat(testProductPhoto.getWatermarkPhoto()).isEqualTo(UPDATED_WATERMARK_PHOTO);
        assertThat(testProductPhoto.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProductPhoto.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testProductPhoto.getDeleteToken()).isEqualTo(UPDATED_DELETE_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPhoto() throws Exception {
        int databaseSizeBeforeUpdate = productPhotoRepository.findAll().size();

        // Create the ProductPhoto
        ProductPhotoDTO productPhotoDTO = productPhotoMapper.toDto(productPhoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPhotoMockMvc.perform(put("/api/product-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPhotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPhoto in the database
        List<ProductPhoto> productPhotoList = productPhotoRepository.findAll();
        assertThat(productPhotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductPhoto() throws Exception {
        // Initialize the database
        productPhotoRepository.saveAndFlush(productPhoto);

        int databaseSizeBeforeDelete = productPhotoRepository.findAll().size();

        // Delete the productPhoto
        restProductPhotoMockMvc.perform(delete("/api/product-photos/{id}", productPhoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductPhoto> productPhotoList = productPhotoRepository.findAll();
        assertThat(productPhotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPhoto.class);
        ProductPhoto productPhoto1 = new ProductPhoto();
        productPhoto1.setId(1L);
        ProductPhoto productPhoto2 = new ProductPhoto();
        productPhoto2.setId(productPhoto1.getId());
        assertThat(productPhoto1).isEqualTo(productPhoto2);
        productPhoto2.setId(2L);
        assertThat(productPhoto1).isNotEqualTo(productPhoto2);
        productPhoto1.setId(null);
        assertThat(productPhoto1).isNotEqualTo(productPhoto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPhotoDTO.class);
        ProductPhotoDTO productPhotoDTO1 = new ProductPhotoDTO();
        productPhotoDTO1.setId(1L);
        ProductPhotoDTO productPhotoDTO2 = new ProductPhotoDTO();
        assertThat(productPhotoDTO1).isNotEqualTo(productPhotoDTO2);
        productPhotoDTO2.setId(productPhotoDTO1.getId());
        assertThat(productPhotoDTO1).isEqualTo(productPhotoDTO2);
        productPhotoDTO2.setId(2L);
        assertThat(productPhotoDTO1).isNotEqualTo(productPhotoDTO2);
        productPhotoDTO1.setId(null);
        assertThat(productPhotoDTO1).isNotEqualTo(productPhotoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productPhotoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productPhotoMapper.fromId(null)).isNull();
    }
}
