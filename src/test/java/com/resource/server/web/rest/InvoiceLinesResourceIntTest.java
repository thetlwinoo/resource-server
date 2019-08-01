package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.InvoiceLines;
import com.resource.server.repository.InvoiceLinesRepository;
import com.resource.server.service.InvoiceLinesService;
import com.resource.server.service.dto.InvoiceLinesDTO;
import com.resource.server.service.mapper.InvoiceLinesMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvoiceLinesResource REST controller.
 *
 * @see InvoiceLinesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class InvoiceLinesResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LINE_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LINE_PROFIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTENDED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_PRICE = new BigDecimal(2);

    @Autowired
    private InvoiceLinesRepository invoiceLinesRepository;

    @Autowired
    private InvoiceLinesMapper invoiceLinesMapper;

    @Autowired
    private InvoiceLinesService invoiceLinesService;

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

    private MockMvc restInvoiceLinesMockMvc;

    private InvoiceLines invoiceLines;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLinesResource invoiceLinesResource = new InvoiceLinesResource(invoiceLinesService);
        this.restInvoiceLinesMockMvc = MockMvcBuilders.standaloneSetup(invoiceLinesResource)
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
    public static InvoiceLines createEntity(EntityManager em) {
        InvoiceLines invoiceLines = new InvoiceLines()
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .taxRate(DEFAULT_TAX_RATE)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .lineProfit(DEFAULT_LINE_PROFIT)
            .extendedPrice(DEFAULT_EXTENDED_PRICE);
        return invoiceLines;
    }

    @Before
    public void initTest() {
        invoiceLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLines() throws Exception {
        int databaseSizeBeforeCreate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);
        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLines testInvoiceLines = invoiceLinesList.get(invoiceLinesList.size() - 1);
        assertThat(testInvoiceLines.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLines.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceLines.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testInvoiceLines.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testInvoiceLines.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testInvoiceLines.getLineProfit()).isEqualTo(DEFAULT_LINE_PROFIT);
        assertThat(testInvoiceLines.getExtendedPrice()).isEqualTo(DEFAULT_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void createInvoiceLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines with an existing ID
        invoiceLines.setId(1L);
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setDescription(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setQuantity(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setTaxRate(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setTaxAmount(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLineProfitIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setLineProfit(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtendedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setExtendedPrice(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lineProfit").value(hasItem(DEFAULT_LINE_PROFIT.intValue())))
            .andExpect(jsonPath("$.[*].extendedPrice").value(hasItem(DEFAULT_EXTENDED_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get the invoiceLines
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/{id}", invoiceLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLines.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.lineProfit").value(DEFAULT_LINE_PROFIT.intValue()))
            .andExpect(jsonPath("$.extendedPrice").value(DEFAULT_EXTENDED_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceLines() throws Exception {
        // Get the invoiceLines
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        int databaseSizeBeforeUpdate = invoiceLinesRepository.findAll().size();

        // Update the invoiceLines
        InvoiceLines updatedInvoiceLines = invoiceLinesRepository.findById(invoiceLines.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLines are not directly saved in db
        em.detach(updatedInvoiceLines);
        updatedInvoiceLines
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .taxRate(UPDATED_TAX_RATE)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .lineProfit(UPDATED_LINE_PROFIT)
            .extendedPrice(UPDATED_EXTENDED_PRICE);
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(updatedInvoiceLines);

        restInvoiceLinesMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLines testInvoiceLines = invoiceLinesList.get(invoiceLinesList.size() - 1);
        assertThat(testInvoiceLines.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLines.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLines.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testInvoiceLines.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testInvoiceLines.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testInvoiceLines.getLineProfit()).isEqualTo(UPDATED_LINE_PROFIT);
        assertThat(testInvoiceLines.getExtendedPrice()).isEqualTo(UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLines() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLinesMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        int databaseSizeBeforeDelete = invoiceLinesRepository.findAll().size();

        // Delete the invoiceLines
        restInvoiceLinesMockMvc.perform(delete("/api/invoice-lines/{id}", invoiceLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLines.class);
        InvoiceLines invoiceLines1 = new InvoiceLines();
        invoiceLines1.setId(1L);
        InvoiceLines invoiceLines2 = new InvoiceLines();
        invoiceLines2.setId(invoiceLines1.getId());
        assertThat(invoiceLines1).isEqualTo(invoiceLines2);
        invoiceLines2.setId(2L);
        assertThat(invoiceLines1).isNotEqualTo(invoiceLines2);
        invoiceLines1.setId(null);
        assertThat(invoiceLines1).isNotEqualTo(invoiceLines2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLinesDTO.class);
        InvoiceLinesDTO invoiceLinesDTO1 = new InvoiceLinesDTO();
        invoiceLinesDTO1.setId(1L);
        InvoiceLinesDTO invoiceLinesDTO2 = new InvoiceLinesDTO();
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO2.setId(invoiceLinesDTO1.getId());
        assertThat(invoiceLinesDTO1).isEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO2.setId(2L);
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO1.setId(null);
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceLinesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceLinesMapper.fromId(null)).isNull();
    }
}
