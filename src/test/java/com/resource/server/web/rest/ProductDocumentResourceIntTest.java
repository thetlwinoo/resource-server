package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ProductDocument;
import com.resource.server.repository.ProductDocumentRepository;
import com.resource.server.service.ProductDocumentService;
import com.resource.server.service.dto.ProductDocumentDTO;
import com.resource.server.service.mapper.ProductDocumentMapper;
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
 * Test class for the ProductDocumentResource REST controller.
 *
 * @see ProductDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductDocumentResourceIntTest {

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_CARE_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FABRIC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_FEATURES = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENUINE_AND_LEGAL = false;
    private static final Boolean UPDATED_GENUINE_AND_LEGAL = true;

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_AND_SIDE_EFFECTS = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_AND_SIDE_EFFECTS = "BBBBBBBBBB";

    private static final String DEFAULT_SAFETY_WARNNING = "AAAAAAAAAA";
    private static final String UPDATED_SAFETY_WARNNING = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @Autowired
    private ProductDocumentMapper productDocumentMapper;

    @Autowired
    private ProductDocumentService productDocumentService;

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

    private MockMvc restProductDocumentMockMvc;

    private ProductDocument productDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductDocumentResource productDocumentResource = new ProductDocumentResource(productDocumentService);
        this.restProductDocumentMockMvc = MockMvcBuilders.standaloneSetup(productDocumentResource)
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
    public static ProductDocument createEntity(EntityManager em) {
        ProductDocument productDocument = new ProductDocument()
            .videoUrl(DEFAULT_VIDEO_URL)
            .highlights(DEFAULT_HIGHLIGHTS)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .description(DEFAULT_DESCRIPTION)
            .careInstructions(DEFAULT_CARE_INSTRUCTIONS)
            .productType(DEFAULT_PRODUCT_TYPE)
            .modelName(DEFAULT_MODEL_NAME)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .fabricType(DEFAULT_FABRIC_TYPE)
            .specialFeatures(DEFAULT_SPECIAL_FEATURES)
            .productComplianceCertificate(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(DEFAULT_GENUINE_AND_LEGAL)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(DEFAULT_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(DEFAULT_SAFETY_WARNNING)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY);
        return productDocument;
    }

    @Before
    public void initTest() {
        productDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDocument() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();

        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);
        restProductDocumentMockMvc.perform(post("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
        assertThat(testProductDocument.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProductDocument.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testProductDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductDocument.getCareInstructions()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS);
        assertThat(testProductDocument.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(DEFAULT_FABRIC_TYPE);
        assertThat(testProductDocument.getSpecialFeatures()).isEqualTo(DEFAULT_SPECIAL_FEATURES);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getUsageAndSideEffects()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocument.getSafetyWarnning()).isEqualTo(DEFAULT_SAFETY_WARNNING);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void createProductDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();

        // Create the ProductDocument with an existing ID
        productDocument.setId(1L);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDocumentMockMvc.perform(post("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductDocuments() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL.toString())))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].specialFeatures").value(hasItem(DEFAULT_SPECIAL_FEATURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY.toString())));
    }
    
    @Test
    @Transactional
    public void getProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", productDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDocument.getId().intValue()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL.toString()))
            .andExpect(jsonPath("$.highlights").value(DEFAULT_HIGHLIGHTS.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.careInstructions").value(DEFAULT_CARE_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME.toString()))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER.toString()))
            .andExpect(jsonPath("$.fabricType").value(DEFAULT_FABRIC_TYPE.toString()))
            .andExpect(jsonPath("$.specialFeatures").value(DEFAULT_SPECIAL_FEATURES.toString()))
            .andExpect(jsonPath("$.productComplianceCertificate").value(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.genuineAndLegal").value(DEFAULT_GENUINE_AND_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN.toString()))
            .andExpect(jsonPath("$.usageAndSideEffects").value(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString()))
            .andExpect(jsonPath("$.safetyWarnning").value(DEFAULT_SAFETY_WARNNING.toString()))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD.toString()))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductDocument() throws Exception {
        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Update the productDocument
        ProductDocument updatedProductDocument = productDocumentRepository.findById(productDocument.getId()).get();
        // Disconnect from session so that the updates on updatedProductDocument are not directly saved in db
        em.detach(updatedProductDocument);
        updatedProductDocument
            .videoUrl(UPDATED_VIDEO_URL)
            .highlights(UPDATED_HIGHLIGHTS)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .description(UPDATED_DESCRIPTION)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .productType(UPDATED_PRODUCT_TYPE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .fabricType(UPDATED_FABRIC_TYPE)
            .specialFeatures(UPDATED_SPECIAL_FEATURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(updatedProductDocument);

        restProductDocumentMockMvc.perform(put("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
        assertThat(testProductDocument.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProductDocument.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testProductDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductDocument.getCareInstructions()).isEqualTo(UPDATED_CARE_INSTRUCTIONS);
        assertThat(testProductDocument.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(UPDATED_FABRIC_TYPE);
        assertThat(testProductDocument.getSpecialFeatures()).isEqualTo(UPDATED_SPECIAL_FEATURES);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getUsageAndSideEffects()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocument.getSafetyWarnning()).isEqualTo(UPDATED_SAFETY_WARNNING);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDocument() throws Exception {
        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDocumentMockMvc.perform(put("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeDelete = productDocumentRepository.findAll().size();

        // Delete the productDocument
        restProductDocumentMockMvc.perform(delete("/api/product-documents/{id}", productDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocument.class);
        ProductDocument productDocument1 = new ProductDocument();
        productDocument1.setId(1L);
        ProductDocument productDocument2 = new ProductDocument();
        productDocument2.setId(productDocument1.getId());
        assertThat(productDocument1).isEqualTo(productDocument2);
        productDocument2.setId(2L);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
        productDocument1.setId(null);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentDTO.class);
        ProductDocumentDTO productDocumentDTO1 = new ProductDocumentDTO();
        productDocumentDTO1.setId(1L);
        ProductDocumentDTO productDocumentDTO2 = new ProductDocumentDTO();
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(productDocumentDTO1.getId());
        assertThat(productDocumentDTO1).isEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(2L);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO1.setId(null);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productDocumentMapper.fromId(null)).isNull();
    }
}
