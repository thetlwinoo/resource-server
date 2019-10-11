package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.LastestMerchantUploadedDocument;
import com.resource.server.repository.LastestMerchantUploadedDocumentRepository;
import com.resource.server.service.LastestMerchantUploadedDocumentService;
import com.resource.server.service.dto.LastestMerchantUploadedDocumentDTO;
import com.resource.server.service.mapper.LastestMerchantUploadedDocumentMapper;
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
 * Test class for the LastestMerchantUploadedDocumentResource REST controller.
 *
 * @see LastestMerchantUploadedDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class LastestMerchantUploadedDocumentResourceIntTest {

    private static final byte[] DEFAULT_PRODUCT_CREATE_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRODUCT_CREATE_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE = "image/png";

    @Autowired
    private LastestMerchantUploadedDocumentRepository lastestMerchantUploadedDocumentRepository;

    @Autowired
    private LastestMerchantUploadedDocumentMapper lastestMerchantUploadedDocumentMapper;

    @Autowired
    private LastestMerchantUploadedDocumentService lastestMerchantUploadedDocumentService;

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

    private MockMvc restLastestMerchantUploadedDocumentMockMvc;

    private LastestMerchantUploadedDocument lastestMerchantUploadedDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LastestMerchantUploadedDocumentResource lastestMerchantUploadedDocumentResource = new LastestMerchantUploadedDocumentResource(lastestMerchantUploadedDocumentService);
        this.restLastestMerchantUploadedDocumentMockMvc = MockMvcBuilders.standaloneSetup(lastestMerchantUploadedDocumentResource)
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
    public static LastestMerchantUploadedDocument createEntity(EntityManager em) {
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument = new LastestMerchantUploadedDocument()
            .productCreateTemplate(DEFAULT_PRODUCT_CREATE_TEMPLATE)
            .productCreateTemplateContentType(DEFAULT_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE);
        return lastestMerchantUploadedDocument;
    }

    @Before
    public void initTest() {
        lastestMerchantUploadedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createLastestMerchantUploadedDocument() throws Exception {
        int databaseSizeBeforeCreate = lastestMerchantUploadedDocumentRepository.findAll().size();

        // Create the LastestMerchantUploadedDocument
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO = lastestMerchantUploadedDocumentMapper.toDto(lastestMerchantUploadedDocument);
        restLastestMerchantUploadedDocumentMockMvc.perform(post("/api/lastest-merchant-uploaded-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastestMerchantUploadedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the LastestMerchantUploadedDocument in the database
        List<LastestMerchantUploadedDocument> lastestMerchantUploadedDocumentList = lastestMerchantUploadedDocumentRepository.findAll();
        assertThat(lastestMerchantUploadedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        LastestMerchantUploadedDocument testLastestMerchantUploadedDocument = lastestMerchantUploadedDocumentList.get(lastestMerchantUploadedDocumentList.size() - 1);
        assertThat(testLastestMerchantUploadedDocument.getProductCreateTemplate()).isEqualTo(DEFAULT_PRODUCT_CREATE_TEMPLATE);
        assertThat(testLastestMerchantUploadedDocument.getProductCreateTemplateContentType()).isEqualTo(DEFAULT_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLastestMerchantUploadedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lastestMerchantUploadedDocumentRepository.findAll().size();

        // Create the LastestMerchantUploadedDocument with an existing ID
        lastestMerchantUploadedDocument.setId(1L);
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO = lastestMerchantUploadedDocumentMapper.toDto(lastestMerchantUploadedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLastestMerchantUploadedDocumentMockMvc.perform(post("/api/lastest-merchant-uploaded-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastestMerchantUploadedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LastestMerchantUploadedDocument in the database
        List<LastestMerchantUploadedDocument> lastestMerchantUploadedDocumentList = lastestMerchantUploadedDocumentRepository.findAll();
        assertThat(lastestMerchantUploadedDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLastestMerchantUploadedDocuments() throws Exception {
        // Initialize the database
        lastestMerchantUploadedDocumentRepository.saveAndFlush(lastestMerchantUploadedDocument);

        // Get all the lastestMerchantUploadedDocumentList
        restLastestMerchantUploadedDocumentMockMvc.perform(get("/api/lastest-merchant-uploaded-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lastestMerchantUploadedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].productCreateTemplateContentType").value(hasItem(DEFAULT_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productCreateTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_CREATE_TEMPLATE))));
    }
    
    @Test
    @Transactional
    public void getLastestMerchantUploadedDocument() throws Exception {
        // Initialize the database
        lastestMerchantUploadedDocumentRepository.saveAndFlush(lastestMerchantUploadedDocument);

        // Get the lastestMerchantUploadedDocument
        restLastestMerchantUploadedDocumentMockMvc.perform(get("/api/lastest-merchant-uploaded-documents/{id}", lastestMerchantUploadedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lastestMerchantUploadedDocument.getId().intValue()))
            .andExpect(jsonPath("$.productCreateTemplateContentType").value(DEFAULT_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.productCreateTemplate").value(Base64Utils.encodeToString(DEFAULT_PRODUCT_CREATE_TEMPLATE)));
    }

    @Test
    @Transactional
    public void getNonExistingLastestMerchantUploadedDocument() throws Exception {
        // Get the lastestMerchantUploadedDocument
        restLastestMerchantUploadedDocumentMockMvc.perform(get("/api/lastest-merchant-uploaded-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLastestMerchantUploadedDocument() throws Exception {
        // Initialize the database
        lastestMerchantUploadedDocumentRepository.saveAndFlush(lastestMerchantUploadedDocument);

        int databaseSizeBeforeUpdate = lastestMerchantUploadedDocumentRepository.findAll().size();

        // Update the lastestMerchantUploadedDocument
        LastestMerchantUploadedDocument updatedLastestMerchantUploadedDocument = lastestMerchantUploadedDocumentRepository.findById(lastestMerchantUploadedDocument.getId()).get();
        // Disconnect from session so that the updates on updatedLastestMerchantUploadedDocument are not directly saved in db
        em.detach(updatedLastestMerchantUploadedDocument);
        updatedLastestMerchantUploadedDocument
            .productCreateTemplate(UPDATED_PRODUCT_CREATE_TEMPLATE)
            .productCreateTemplateContentType(UPDATED_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE);
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO = lastestMerchantUploadedDocumentMapper.toDto(updatedLastestMerchantUploadedDocument);

        restLastestMerchantUploadedDocumentMockMvc.perform(put("/api/lastest-merchant-uploaded-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastestMerchantUploadedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the LastestMerchantUploadedDocument in the database
        List<LastestMerchantUploadedDocument> lastestMerchantUploadedDocumentList = lastestMerchantUploadedDocumentRepository.findAll();
        assertThat(lastestMerchantUploadedDocumentList).hasSize(databaseSizeBeforeUpdate);
        LastestMerchantUploadedDocument testLastestMerchantUploadedDocument = lastestMerchantUploadedDocumentList.get(lastestMerchantUploadedDocumentList.size() - 1);
        assertThat(testLastestMerchantUploadedDocument.getProductCreateTemplate()).isEqualTo(UPDATED_PRODUCT_CREATE_TEMPLATE);
        assertThat(testLastestMerchantUploadedDocument.getProductCreateTemplateContentType()).isEqualTo(UPDATED_PRODUCT_CREATE_TEMPLATE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLastestMerchantUploadedDocument() throws Exception {
        int databaseSizeBeforeUpdate = lastestMerchantUploadedDocumentRepository.findAll().size();

        // Create the LastestMerchantUploadedDocument
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO = lastestMerchantUploadedDocumentMapper.toDto(lastestMerchantUploadedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLastestMerchantUploadedDocumentMockMvc.perform(put("/api/lastest-merchant-uploaded-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastestMerchantUploadedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LastestMerchantUploadedDocument in the database
        List<LastestMerchantUploadedDocument> lastestMerchantUploadedDocumentList = lastestMerchantUploadedDocumentRepository.findAll();
        assertThat(lastestMerchantUploadedDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLastestMerchantUploadedDocument() throws Exception {
        // Initialize the database
        lastestMerchantUploadedDocumentRepository.saveAndFlush(lastestMerchantUploadedDocument);

        int databaseSizeBeforeDelete = lastestMerchantUploadedDocumentRepository.findAll().size();

        // Delete the lastestMerchantUploadedDocument
        restLastestMerchantUploadedDocumentMockMvc.perform(delete("/api/lastest-merchant-uploaded-documents/{id}", lastestMerchantUploadedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LastestMerchantUploadedDocument> lastestMerchantUploadedDocumentList = lastestMerchantUploadedDocumentRepository.findAll();
        assertThat(lastestMerchantUploadedDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LastestMerchantUploadedDocument.class);
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument1 = new LastestMerchantUploadedDocument();
        lastestMerchantUploadedDocument1.setId(1L);
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument2 = new LastestMerchantUploadedDocument();
        lastestMerchantUploadedDocument2.setId(lastestMerchantUploadedDocument1.getId());
        assertThat(lastestMerchantUploadedDocument1).isEqualTo(lastestMerchantUploadedDocument2);
        lastestMerchantUploadedDocument2.setId(2L);
        assertThat(lastestMerchantUploadedDocument1).isNotEqualTo(lastestMerchantUploadedDocument2);
        lastestMerchantUploadedDocument1.setId(null);
        assertThat(lastestMerchantUploadedDocument1).isNotEqualTo(lastestMerchantUploadedDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LastestMerchantUploadedDocumentDTO.class);
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO1 = new LastestMerchantUploadedDocumentDTO();
        lastestMerchantUploadedDocumentDTO1.setId(1L);
        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO2 = new LastestMerchantUploadedDocumentDTO();
        assertThat(lastestMerchantUploadedDocumentDTO1).isNotEqualTo(lastestMerchantUploadedDocumentDTO2);
        lastestMerchantUploadedDocumentDTO2.setId(lastestMerchantUploadedDocumentDTO1.getId());
        assertThat(lastestMerchantUploadedDocumentDTO1).isEqualTo(lastestMerchantUploadedDocumentDTO2);
        lastestMerchantUploadedDocumentDTO2.setId(2L);
        assertThat(lastestMerchantUploadedDocumentDTO1).isNotEqualTo(lastestMerchantUploadedDocumentDTO2);
        lastestMerchantUploadedDocumentDTO1.setId(null);
        assertThat(lastestMerchantUploadedDocumentDTO1).isNotEqualTo(lastestMerchantUploadedDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lastestMerchantUploadedDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lastestMerchantUploadedDocumentMapper.fromId(null)).isNull();
    }
}
