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

    private static final byte[] DEFAULT_DOCUMENT_NODE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_NODE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_NODE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_NODE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS = "BBBBBBBBBB";

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
            .documentNode(DEFAULT_DOCUMENT_NODE)
            .documentNodeContentType(DEFAULT_DOCUMENT_NODE_CONTENT_TYPE)
            .videoUrl(DEFAULT_VIDEO_URL)
            .highlights(DEFAULT_HIGHLIGHTS);
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
        assertThat(testProductDocument.getDocumentNode()).isEqualTo(DEFAULT_DOCUMENT_NODE);
        assertThat(testProductDocument.getDocumentNodeContentType()).isEqualTo(DEFAULT_DOCUMENT_NODE_CONTENT_TYPE);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
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
            .andExpect(jsonPath("$.[*].documentNodeContentType").value(hasItem(DEFAULT_DOCUMENT_NODE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentNode").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_NODE))))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL.toString())))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())));
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
            .andExpect(jsonPath("$.documentNodeContentType").value(DEFAULT_DOCUMENT_NODE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentNode").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_NODE)))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL.toString()))
            .andExpect(jsonPath("$.highlights").value(DEFAULT_HIGHLIGHTS.toString()));
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
            .documentNode(UPDATED_DOCUMENT_NODE)
            .documentNodeContentType(UPDATED_DOCUMENT_NODE_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .highlights(UPDATED_HIGHLIGHTS);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(updatedProductDocument);

        restProductDocumentMockMvc.perform(put("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getDocumentNode()).isEqualTo(UPDATED_DOCUMENT_NODE);
        assertThat(testProductDocument.getDocumentNodeContentType()).isEqualTo(UPDATED_DOCUMENT_NODE_CONTENT_TYPE);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
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
