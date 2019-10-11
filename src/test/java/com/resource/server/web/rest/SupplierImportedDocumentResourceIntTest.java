package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.SupplierImportedDocument;
import com.resource.server.repository.SupplierImportedDocumentRepository;
import com.resource.server.service.SupplierImportedDocumentService;
import com.resource.server.service.dto.SupplierImportedDocumentDTO;
import com.resource.server.service.mapper.SupplierImportedDocumentMapper;
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
 * Test class for the SupplierImportedDocumentResource REST controller.
 *
 * @see SupplierImportedDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class SupplierImportedDocumentResourceIntTest {

    private static final byte[] DEFAULT_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/png";

    @Autowired
    private SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    @Autowired
    private SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    @Autowired
    private SupplierImportedDocumentService supplierImportedDocumentService;

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

    private MockMvc restSupplierImportedDocumentMockMvc;

    private SupplierImportedDocument supplierImportedDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierImportedDocumentResource supplierImportedDocumentResource = new SupplierImportedDocumentResource(supplierImportedDocumentService);
        this.restSupplierImportedDocumentMockMvc = MockMvcBuilders.standaloneSetup(supplierImportedDocumentResource)
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
    public static SupplierImportedDocument createEntity(EntityManager em) {
        SupplierImportedDocument supplierImportedDocument = new SupplierImportedDocument()
            .importedTemplate(DEFAULT_IMPORTED_TEMPLATE)
            .importedTemplateContentType(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(DEFAULT_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        return supplierImportedDocument;
    }

    @Before
    public void initTest() {
        supplierImportedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierImportedDocument() throws Exception {
        int databaseSizeBeforeCreate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);
        restSupplierImportedDocumentMockMvc.perform(post("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierImportedDocument testSupplierImportedDocument = supplierImportedDocumentList.get(supplierImportedDocumentList.size() - 1);
        assertThat(testSupplierImportedDocument.getImportedTemplate()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplate()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSupplierImportedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument with an existing ID
        supplierImportedDocument.setId(1L);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierImportedDocumentMockMvc.perform(post("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocuments() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierImportedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].importedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].importedFailedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedFailedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE))));
    }
    
    @Test
    @Transactional
    public void getSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/{id}", supplierImportedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierImportedDocument.getId().intValue()))
            .andExpect(jsonPath("$.importedTemplateContentType").value(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE)))
            .andExpect(jsonPath("$.importedFailedTemplateContentType").value(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedFailedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierImportedDocument() throws Exception {
        // Get the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        int databaseSizeBeforeUpdate = supplierImportedDocumentRepository.findAll().size();

        // Update the supplierImportedDocument
        SupplierImportedDocument updatedSupplierImportedDocument = supplierImportedDocumentRepository.findById(supplierImportedDocument.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierImportedDocument are not directly saved in db
        em.detach(updatedSupplierImportedDocument);
        updatedSupplierImportedDocument
            .importedTemplate(UPDATED_IMPORTED_TEMPLATE)
            .importedTemplateContentType(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(UPDATED_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(updatedSupplierImportedDocument);

        restSupplierImportedDocumentMockMvc.perform(put("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeUpdate);
        SupplierImportedDocument testSupplierImportedDocument = supplierImportedDocumentList.get(supplierImportedDocumentList.size() - 1);
        assertThat(testSupplierImportedDocument.getImportedTemplate()).isEqualTo(UPDATED_IMPORTED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplate()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierImportedDocument() throws Exception {
        int databaseSizeBeforeUpdate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierImportedDocumentMockMvc.perform(put("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        int databaseSizeBeforeDelete = supplierImportedDocumentRepository.findAll().size();

        // Delete the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(delete("/api/supplier-imported-documents/{id}", supplierImportedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierImportedDocument.class);
        SupplierImportedDocument supplierImportedDocument1 = new SupplierImportedDocument();
        supplierImportedDocument1.setId(1L);
        SupplierImportedDocument supplierImportedDocument2 = new SupplierImportedDocument();
        supplierImportedDocument2.setId(supplierImportedDocument1.getId());
        assertThat(supplierImportedDocument1).isEqualTo(supplierImportedDocument2);
        supplierImportedDocument2.setId(2L);
        assertThat(supplierImportedDocument1).isNotEqualTo(supplierImportedDocument2);
        supplierImportedDocument1.setId(null);
        assertThat(supplierImportedDocument1).isNotEqualTo(supplierImportedDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierImportedDocumentDTO.class);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO1 = new SupplierImportedDocumentDTO();
        supplierImportedDocumentDTO1.setId(1L);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO2 = new SupplierImportedDocumentDTO();
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO2.setId(supplierImportedDocumentDTO1.getId());
        assertThat(supplierImportedDocumentDTO1).isEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO2.setId(2L);
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO1.setId(null);
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplierImportedDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplierImportedDocumentMapper.fromId(null)).isNull();
    }
}
