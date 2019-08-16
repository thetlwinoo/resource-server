package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PurchaseOrderLines;
import com.resource.server.repository.PurchaseOrderLinesRepository;
import com.resource.server.service.PurchaseOrderLinesService;
import com.resource.server.service.dto.PurchaseOrderLinesDTO;
import com.resource.server.service.mapper.PurchaseOrderLinesMapper;
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
 * Test class for the PurchaseOrderLinesResource REST controller.
 *
 * @see PurchaseOrderLinesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PurchaseOrderLinesResourceIntTest {

    private static final Integer DEFAULT_ORDERS_OUTERS = 1;
    private static final Integer UPDATED_ORDERS_OUTERS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECEIVED_OUTERS = 1;
    private static final Integer UPDATED_RECEIVED_OUTERS = 2;

    private static final Float DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER = 1F;
    private static final Float UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER = 2F;

    private static final LocalDate DEFAULT_LAST_RECEIPT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_RECEIPT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ORDER_LINE_FINALIZED = false;
    private static final Boolean UPDATED_IS_ORDER_LINE_FINALIZED = true;

    @Autowired
    private PurchaseOrderLinesRepository purchaseOrderLinesRepository;

    @Autowired
    private PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    @Autowired
    private PurchaseOrderLinesService purchaseOrderLinesService;

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

    private MockMvc restPurchaseOrderLinesMockMvc;

    private PurchaseOrderLines purchaseOrderLines;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderLinesResource purchaseOrderLinesResource = new PurchaseOrderLinesResource(purchaseOrderLinesService);
        this.restPurchaseOrderLinesMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderLinesResource)
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
    public static PurchaseOrderLines createEntity(EntityManager em) {
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines()
            .ordersOuters(DEFAULT_ORDERS_OUTERS)
            .description(DEFAULT_DESCRIPTION)
            .receivedOuters(DEFAULT_RECEIVED_OUTERS)
            .expectedUnitPricePerOuter(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER)
            .lastReceiptDate(DEFAULT_LAST_RECEIPT_DATE)
            .isOrderLineFinalized(DEFAULT_IS_ORDER_LINE_FINALIZED);
        return purchaseOrderLines;
    }

    @Before
    public void initTest() {
        purchaseOrderLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderLines() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);
        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderLines testPurchaseOrderLines = purchaseOrderLinesList.get(purchaseOrderLinesList.size() - 1);
        assertThat(testPurchaseOrderLines.getOrdersOuters()).isEqualTo(DEFAULT_ORDERS_OUTERS);
        assertThat(testPurchaseOrderLines.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPurchaseOrderLines.getReceivedOuters()).isEqualTo(DEFAULT_RECEIVED_OUTERS);
        assertThat(testPurchaseOrderLines.getExpectedUnitPricePerOuter()).isEqualTo(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);
        assertThat(testPurchaseOrderLines.getLastReceiptDate()).isEqualTo(DEFAULT_LAST_RECEIPT_DATE);
        assertThat(testPurchaseOrderLines.isIsOrderLineFinalized()).isEqualTo(DEFAULT_IS_ORDER_LINE_FINALIZED);
    }

    @Test
    @Transactional
    public void createPurchaseOrderLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines with an existing ID
        purchaseOrderLines.setId(1L);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrdersOutersIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrderLinesRepository.findAll().size();
        // set the field null
        purchaseOrderLines.setOrdersOuters(null);

        // Create the PurchaseOrderLines, which fails.
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordersOuters").value(hasItem(DEFAULT_ORDERS_OUTERS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].receivedOuters").value(hasItem(DEFAULT_RECEIVED_OUTERS)))
            .andExpect(jsonPath("$.[*].expectedUnitPricePerOuter").value(hasItem(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER.doubleValue())))
            .andExpect(jsonPath("$.[*].lastReceiptDate").value(hasItem(DEFAULT_LAST_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOrderLineFinalized").value(hasItem(DEFAULT_IS_ORDER_LINE_FINALIZED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/{id}", purchaseOrderLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderLines.getId().intValue()))
            .andExpect(jsonPath("$.ordersOuters").value(DEFAULT_ORDERS_OUTERS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.receivedOuters").value(DEFAULT_RECEIVED_OUTERS))
            .andExpect(jsonPath("$.expectedUnitPricePerOuter").value(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER.doubleValue()))
            .andExpect(jsonPath("$.lastReceiptDate").value(DEFAULT_LAST_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.isOrderLineFinalized").value(DEFAULT_IS_ORDER_LINE_FINALIZED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrderLines() throws Exception {
        // Get the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        int databaseSizeBeforeUpdate = purchaseOrderLinesRepository.findAll().size();

        // Update the purchaseOrderLines
        PurchaseOrderLines updatedPurchaseOrderLines = purchaseOrderLinesRepository.findById(purchaseOrderLines.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrderLines are not directly saved in db
        em.detach(updatedPurchaseOrderLines);
        updatedPurchaseOrderLines
            .ordersOuters(UPDATED_ORDERS_OUTERS)
            .description(UPDATED_DESCRIPTION)
            .receivedOuters(UPDATED_RECEIVED_OUTERS)
            .expectedUnitPricePerOuter(UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER)
            .lastReceiptDate(UPDATED_LAST_RECEIPT_DATE)
            .isOrderLineFinalized(UPDATED_IS_ORDER_LINE_FINALIZED);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(updatedPurchaseOrderLines);

        restPurchaseOrderLinesMockMvc.perform(put("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderLines testPurchaseOrderLines = purchaseOrderLinesList.get(purchaseOrderLinesList.size() - 1);
        assertThat(testPurchaseOrderLines.getOrdersOuters()).isEqualTo(UPDATED_ORDERS_OUTERS);
        assertThat(testPurchaseOrderLines.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchaseOrderLines.getReceivedOuters()).isEqualTo(UPDATED_RECEIVED_OUTERS);
        assertThat(testPurchaseOrderLines.getExpectedUnitPricePerOuter()).isEqualTo(UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
        assertThat(testPurchaseOrderLines.getLastReceiptDate()).isEqualTo(UPDATED_LAST_RECEIPT_DATE);
        assertThat(testPurchaseOrderLines.isIsOrderLineFinalized()).isEqualTo(UPDATED_IS_ORDER_LINE_FINALIZED);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrderLines() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderLinesMockMvc.perform(put("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        int databaseSizeBeforeDelete = purchaseOrderLinesRepository.findAll().size();

        // Delete the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(delete("/api/purchase-order-lines/{id}", purchaseOrderLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLines.class);
        PurchaseOrderLines purchaseOrderLines1 = new PurchaseOrderLines();
        purchaseOrderLines1.setId(1L);
        PurchaseOrderLines purchaseOrderLines2 = new PurchaseOrderLines();
        purchaseOrderLines2.setId(purchaseOrderLines1.getId());
        assertThat(purchaseOrderLines1).isEqualTo(purchaseOrderLines2);
        purchaseOrderLines2.setId(2L);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
        purchaseOrderLines1.setId(null);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLinesDTO.class);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO1 = new PurchaseOrderLinesDTO();
        purchaseOrderLinesDTO1.setId(1L);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO2 = new PurchaseOrderLinesDTO();
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(purchaseOrderLinesDTO1.getId());
        assertThat(purchaseOrderLinesDTO1).isEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(2L);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO1.setId(null);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrderLinesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrderLinesMapper.fromId(null)).isNull();
    }
}
