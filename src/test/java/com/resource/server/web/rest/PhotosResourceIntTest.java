package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Photos;
import com.resource.server.repository.PhotosRepository;
import com.resource.server.service.PhotosService;
import com.resource.server.service.dto.PhotosDTO;
import com.resource.server.service.mapper.PhotosMapper;
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
 * Test class for the PhotosResource REST controller.
 *
 * @see PhotosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PhotosResourceIntTest {

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

    private static final byte[] DEFAULT_THUMBNAIL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMBNAIL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ORIGINAL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ORIGINAL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BANNER_TALL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_TALL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BANNER_WIDE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_WIDE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CIRCLE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CIRCLE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SHARPENED_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SHARPENED_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SQUARE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SQUARE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_WATERMARK_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_WATERMARK_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final String DEFAULT_DELETE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_DELETE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private PhotosRepository photosRepository;

    @Autowired
    private PhotosMapper photosMapper;

    @Autowired
    private PhotosService photosService;

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

    private MockMvc restPhotosMockMvc;

    private Photos photos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhotosResource photosResource = new PhotosResource(photosService);
        this.restPhotosMockMvc = MockMvcBuilders.standaloneSetup(photosResource)
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
    public static Photos createEntity(EntityManager em) {
        Photos photos = new Photos()
            .thumbnailPhoto(DEFAULT_THUMBNAIL_PHOTO)
            .originalPhoto(DEFAULT_ORIGINAL_PHOTO)
            .bannerTallPhoto(DEFAULT_BANNER_TALL_PHOTO)
            .bannerWidePhoto(DEFAULT_BANNER_WIDE_PHOTO)
            .circlePhoto(DEFAULT_CIRCLE_PHOTO)
            .sharpenedPhoto(DEFAULT_SHARPENED_PHOTO)
            .squarePhoto(DEFAULT_SQUARE_PHOTO)
            .watermarkPhoto(DEFAULT_WATERMARK_PHOTO)
            .thumbnailPhotoBlob(DEFAULT_THUMBNAIL_PHOTO_BLOB)
            .thumbnailPhotoBlobContentType(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)
            .originalPhotoBlob(DEFAULT_ORIGINAL_PHOTO_BLOB)
            .originalPhotoBlobContentType(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerTallPhotoBlob(DEFAULT_BANNER_TALL_PHOTO_BLOB)
            .bannerTallPhotoBlobContentType(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerWidePhotoBlob(DEFAULT_BANNER_WIDE_PHOTO_BLOB)
            .bannerWidePhotoBlobContentType(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)
            .circlePhotoBlob(DEFAULT_CIRCLE_PHOTO_BLOB)
            .circlePhotoBlobContentType(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)
            .sharpenedPhotoBlob(DEFAULT_SHARPENED_PHOTO_BLOB)
            .sharpenedPhotoBlobContentType(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)
            .squarePhotoBlob(DEFAULT_SQUARE_PHOTO_BLOB)
            .squarePhotoBlobContentType(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE)
            .watermarkPhotoBlob(DEFAULT_WATERMARK_PHOTO_BLOB)
            .watermarkPhotoBlobContentType(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)
            .priority(DEFAULT_PRIORITY)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .deleteToken(DEFAULT_DELETE_TOKEN);
        return photos;
    }

    @Before
    public void initTest() {
        photos = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhotos() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);
        restPhotosMockMvc.perform(post("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isCreated());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate + 1);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getThumbnailPhoto()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO);
        assertThat(testPhotos.getOriginalPhoto()).isEqualTo(DEFAULT_ORIGINAL_PHOTO);
        assertThat(testPhotos.getBannerTallPhoto()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO);
        assertThat(testPhotos.getBannerWidePhoto()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO);
        assertThat(testPhotos.getCirclePhoto()).isEqualTo(DEFAULT_CIRCLE_PHOTO);
        assertThat(testPhotos.getSharpenedPhoto()).isEqualTo(DEFAULT_SHARPENED_PHOTO);
        assertThat(testPhotos.getSquarePhoto()).isEqualTo(DEFAULT_SQUARE_PHOTO);
        assertThat(testPhotos.getWatermarkPhoto()).isEqualTo(DEFAULT_WATERMARK_PHOTO);
        assertThat(testPhotos.getThumbnailPhotoBlob()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO_BLOB);
        assertThat(testPhotos.getThumbnailPhotoBlobContentType()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getOriginalPhotoBlob()).isEqualTo(DEFAULT_ORIGINAL_PHOTO_BLOB);
        assertThat(testPhotos.getOriginalPhotoBlobContentType()).isEqualTo(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getBannerTallPhotoBlob()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO_BLOB);
        assertThat(testPhotos.getBannerTallPhotoBlobContentType()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getBannerWidePhotoBlob()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO_BLOB);
        assertThat(testPhotos.getBannerWidePhotoBlobContentType()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getCirclePhotoBlob()).isEqualTo(DEFAULT_CIRCLE_PHOTO_BLOB);
        assertThat(testPhotos.getCirclePhotoBlobContentType()).isEqualTo(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getSharpenedPhotoBlob()).isEqualTo(DEFAULT_SHARPENED_PHOTO_BLOB);
        assertThat(testPhotos.getSharpenedPhotoBlobContentType()).isEqualTo(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getSquarePhotoBlob()).isEqualTo(DEFAULT_SQUARE_PHOTO_BLOB);
        assertThat(testPhotos.getSquarePhotoBlobContentType()).isEqualTo(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getWatermarkPhotoBlob()).isEqualTo(DEFAULT_WATERMARK_PHOTO_BLOB);
        assertThat(testPhotos.getWatermarkPhotoBlobContentType()).isEqualTo(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPhotos.getDeleteToken()).isEqualTo(DEFAULT_DELETE_TOKEN);
    }

    @Test
    @Transactional
    public void createPhotosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // Create the Photos with an existing ID
        photos.setId(1L);
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotosMockMvc.perform(post("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnailPhoto").value(hasItem(DEFAULT_THUMBNAIL_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].originalPhoto").value(hasItem(DEFAULT_ORIGINAL_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].bannerTallPhoto").value(hasItem(DEFAULT_BANNER_TALL_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].bannerWidePhoto").value(hasItem(DEFAULT_BANNER_WIDE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].circlePhoto").value(hasItem(DEFAULT_CIRCLE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].sharpenedPhoto").value(hasItem(DEFAULT_SHARPENED_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].squarePhoto").value(hasItem(DEFAULT_SQUARE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].watermarkPhoto").value(hasItem(DEFAULT_WATERMARK_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].thumbnailPhotoBlobContentType").value(hasItem(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnailPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMBNAIL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].originalPhotoBlobContentType").value(hasItem(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].originalPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORIGINAL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].bannerTallPhotoBlobContentType").value(hasItem(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerTallPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_TALL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].bannerWidePhotoBlobContentType").value(hasItem(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerWidePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_WIDE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].circlePhotoBlobContentType").value(hasItem(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].circlePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_CIRCLE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].sharpenedPhotoBlobContentType").value(hasItem(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sharpenedPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SHARPENED_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].squarePhotoBlobContentType").value(hasItem(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].squarePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SQUARE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].watermarkPhotoBlobContentType").value(hasItem(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].watermarkPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_WATERMARK_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].deleteToken").value(hasItem(DEFAULT_DELETE_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", photos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(photos.getId().intValue()))
            .andExpect(jsonPath("$.thumbnailPhoto").value(DEFAULT_THUMBNAIL_PHOTO.toString()))
            .andExpect(jsonPath("$.originalPhoto").value(DEFAULT_ORIGINAL_PHOTO.toString()))
            .andExpect(jsonPath("$.bannerTallPhoto").value(DEFAULT_BANNER_TALL_PHOTO.toString()))
            .andExpect(jsonPath("$.bannerWidePhoto").value(DEFAULT_BANNER_WIDE_PHOTO.toString()))
            .andExpect(jsonPath("$.circlePhoto").value(DEFAULT_CIRCLE_PHOTO.toString()))
            .andExpect(jsonPath("$.sharpenedPhoto").value(DEFAULT_SHARPENED_PHOTO.toString()))
            .andExpect(jsonPath("$.squarePhoto").value(DEFAULT_SQUARE_PHOTO.toString()))
            .andExpect(jsonPath("$.watermarkPhoto").value(DEFAULT_WATERMARK_PHOTO.toString()))
            .andExpect(jsonPath("$.thumbnailPhotoBlobContentType").value(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbnailPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_THUMBNAIL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.originalPhotoBlobContentType").value(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.originalPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_ORIGINAL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.bannerTallPhotoBlobContentType").value(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerTallPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_BANNER_TALL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.bannerWidePhotoBlobContentType").value(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerWidePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_BANNER_WIDE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.circlePhotoBlobContentType").value(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.circlePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_CIRCLE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.sharpenedPhotoBlobContentType").value(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.sharpenedPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_SHARPENED_PHOTO_BLOB)))
            .andExpect(jsonPath("$.squarePhotoBlobContentType").value(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.squarePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_SQUARE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.watermarkPhotoBlobContentType").value(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.watermarkPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_WATERMARK_PHOTO_BLOB)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.deleteToken").value(DEFAULT_DELETE_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhotos() throws Exception {
        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos
        Photos updatedPhotos = photosRepository.findById(photos.getId()).get();
        // Disconnect from session so that the updates on updatedPhotos are not directly saved in db
        em.detach(updatedPhotos);
        updatedPhotos
            .thumbnailPhoto(UPDATED_THUMBNAIL_PHOTO)
            .originalPhoto(UPDATED_ORIGINAL_PHOTO)
            .bannerTallPhoto(UPDATED_BANNER_TALL_PHOTO)
            .bannerWidePhoto(UPDATED_BANNER_WIDE_PHOTO)
            .circlePhoto(UPDATED_CIRCLE_PHOTO)
            .sharpenedPhoto(UPDATED_SHARPENED_PHOTO)
            .squarePhoto(UPDATED_SQUARE_PHOTO)
            .watermarkPhoto(UPDATED_WATERMARK_PHOTO)
            .thumbnailPhotoBlob(UPDATED_THUMBNAIL_PHOTO_BLOB)
            .thumbnailPhotoBlobContentType(UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)
            .originalPhotoBlob(UPDATED_ORIGINAL_PHOTO_BLOB)
            .originalPhotoBlobContentType(UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerTallPhotoBlob(UPDATED_BANNER_TALL_PHOTO_BLOB)
            .bannerTallPhotoBlobContentType(UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerWidePhotoBlob(UPDATED_BANNER_WIDE_PHOTO_BLOB)
            .bannerWidePhotoBlobContentType(UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)
            .circlePhotoBlob(UPDATED_CIRCLE_PHOTO_BLOB)
            .circlePhotoBlobContentType(UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)
            .sharpenedPhotoBlob(UPDATED_SHARPENED_PHOTO_BLOB)
            .sharpenedPhotoBlobContentType(UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)
            .squarePhotoBlob(UPDATED_SQUARE_PHOTO_BLOB)
            .squarePhotoBlobContentType(UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE)
            .watermarkPhotoBlob(UPDATED_WATERMARK_PHOTO_BLOB)
            .watermarkPhotoBlobContentType(UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND)
            .deleteToken(UPDATED_DELETE_TOKEN);
        PhotosDTO photosDTO = photosMapper.toDto(updatedPhotos);

        restPhotosMockMvc.perform(put("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getThumbnailPhoto()).isEqualTo(UPDATED_THUMBNAIL_PHOTO);
        assertThat(testPhotos.getOriginalPhoto()).isEqualTo(UPDATED_ORIGINAL_PHOTO);
        assertThat(testPhotos.getBannerTallPhoto()).isEqualTo(UPDATED_BANNER_TALL_PHOTO);
        assertThat(testPhotos.getBannerWidePhoto()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO);
        assertThat(testPhotos.getCirclePhoto()).isEqualTo(UPDATED_CIRCLE_PHOTO);
        assertThat(testPhotos.getSharpenedPhoto()).isEqualTo(UPDATED_SHARPENED_PHOTO);
        assertThat(testPhotos.getSquarePhoto()).isEqualTo(UPDATED_SQUARE_PHOTO);
        assertThat(testPhotos.getWatermarkPhoto()).isEqualTo(UPDATED_WATERMARK_PHOTO);
        assertThat(testPhotos.getThumbnailPhotoBlob()).isEqualTo(UPDATED_THUMBNAIL_PHOTO_BLOB);
        assertThat(testPhotos.getThumbnailPhotoBlobContentType()).isEqualTo(UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getOriginalPhotoBlob()).isEqualTo(UPDATED_ORIGINAL_PHOTO_BLOB);
        assertThat(testPhotos.getOriginalPhotoBlobContentType()).isEqualTo(UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getBannerTallPhotoBlob()).isEqualTo(UPDATED_BANNER_TALL_PHOTO_BLOB);
        assertThat(testPhotos.getBannerTallPhotoBlobContentType()).isEqualTo(UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getBannerWidePhotoBlob()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO_BLOB);
        assertThat(testPhotos.getBannerWidePhotoBlobContentType()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getCirclePhotoBlob()).isEqualTo(UPDATED_CIRCLE_PHOTO_BLOB);
        assertThat(testPhotos.getCirclePhotoBlobContentType()).isEqualTo(UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getSharpenedPhotoBlob()).isEqualTo(UPDATED_SHARPENED_PHOTO_BLOB);
        assertThat(testPhotos.getSharpenedPhotoBlobContentType()).isEqualTo(UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getSquarePhotoBlob()).isEqualTo(UPDATED_SQUARE_PHOTO_BLOB);
        assertThat(testPhotos.getSquarePhotoBlobContentType()).isEqualTo(UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getWatermarkPhotoBlob()).isEqualTo(UPDATED_WATERMARK_PHOTO_BLOB);
        assertThat(testPhotos.getWatermarkPhotoBlobContentType()).isEqualTo(UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testPhotos.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPhotos.getDeleteToken()).isEqualTo(UPDATED_DELETE_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotosMockMvc.perform(put("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeDelete = photosRepository.findAll().size();

        // Delete the photos
        restPhotosMockMvc.perform(delete("/api/photos/{id}", photos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Photos.class);
        Photos photos1 = new Photos();
        photos1.setId(1L);
        Photos photos2 = new Photos();
        photos2.setId(photos1.getId());
        assertThat(photos1).isEqualTo(photos2);
        photos2.setId(2L);
        assertThat(photos1).isNotEqualTo(photos2);
        photos1.setId(null);
        assertThat(photos1).isNotEqualTo(photos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotosDTO.class);
        PhotosDTO photosDTO1 = new PhotosDTO();
        photosDTO1.setId(1L);
        PhotosDTO photosDTO2 = new PhotosDTO();
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO2.setId(photosDTO1.getId());
        assertThat(photosDTO1).isEqualTo(photosDTO2);
        photosDTO2.setId(2L);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO1.setId(null);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(photosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(photosMapper.fromId(null)).isNull();
    }
}
