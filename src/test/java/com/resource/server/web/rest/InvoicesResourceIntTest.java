package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Invoices;
import com.resource.server.repository.InvoicesRepository;
import com.resource.server.service.InvoicesService;
import com.resource.server.service.dto.InvoicesDTO;
import com.resource.server.service.mapper.InvoicesMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvoicesResource REST controller.
 *
 * @see InvoicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class InvoicesResourceIntTest {

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CREDIT_NOTE = false;
    private static final Boolean UPDATED_IS_CREDIT_NOTE = true;

    private static final String DEFAULT_CREDIT_NOTE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_NOTE_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_DRY_ITEMS = 1;
    private static final Integer UPDATED_TOTAL_DRY_ITEMS = 2;

    private static final Integer DEFAULT_TOTAL_CHILLER_ITEMS = 1;
    private static final Integer UPDATED_TOTAL_CHILLER_ITEMS = 2;

    private static final String DEFAULT_DELIVERY_RUN = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_RUN = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_RUN_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_RETURNED_DELIVERY_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RETURNED_DELIVERY_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONFIRMED_DELIVERY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMED_DELIVERY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONFIRMED_RECEIVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMED_RECEIVED_BY = "BBBBBBBBBB";

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private InvoicesMapper invoicesMapper;

    @Autowired
    private InvoicesService invoicesService;

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

    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoicesResource invoicesResource = new InvoicesResource(invoicesService);
        this.restInvoicesMockMvc = MockMvcBuilders.standaloneSetup(invoicesResource)
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
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .customerPurchaseOrderNumber(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .isCreditNote(DEFAULT_IS_CREDIT_NOTE)
            .creditNoteReason(DEFAULT_CREDIT_NOTE_REASON)
            .comments(DEFAULT_COMMENTS)
            .deliveryInstructions(DEFAULT_DELIVERY_INSTRUCTIONS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .totalDryItems(DEFAULT_TOTAL_DRY_ITEMS)
            .totalChillerItems(DEFAULT_TOTAL_CHILLER_ITEMS)
            .deliveryRun(DEFAULT_DELIVERY_RUN)
            .runPosition(DEFAULT_RUN_POSITION)
            .returnedDeliveryData(DEFAULT_RETURNED_DELIVERY_DATA)
            .confirmedDeliveryTime(DEFAULT_CONFIRMED_DELIVERY_TIME)
            .confirmedReceivedBy(DEFAULT_CONFIRMED_RECEIVED_BY);
        return invoices;
    }

    @Before
    public void initTest() {
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getCustomerPurchaseOrderNumber()).isEqualTo(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testInvoices.isIsCreditNote()).isEqualTo(DEFAULT_IS_CREDIT_NOTE);
        assertThat(testInvoices.getCreditNoteReason()).isEqualTo(DEFAULT_CREDIT_NOTE_REASON);
        assertThat(testInvoices.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInvoices.getDeliveryInstructions()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTIONS);
        assertThat(testInvoices.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testInvoices.getTotalDryItems()).isEqualTo(DEFAULT_TOTAL_DRY_ITEMS);
        assertThat(testInvoices.getTotalChillerItems()).isEqualTo(DEFAULT_TOTAL_CHILLER_ITEMS);
        assertThat(testInvoices.getDeliveryRun()).isEqualTo(DEFAULT_DELIVERY_RUN);
        assertThat(testInvoices.getRunPosition()).isEqualTo(DEFAULT_RUN_POSITION);
        assertThat(testInvoices.getReturnedDeliveryData()).isEqualTo(DEFAULT_RETURNED_DELIVERY_DATA);
        assertThat(testInvoices.getConfirmedDeliveryTime()).isEqualTo(DEFAULT_CONFIRMED_DELIVERY_TIME);
        assertThat(testInvoices.getConfirmedReceivedBy()).isEqualTo(DEFAULT_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void createInvoicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices with an existing ID
        invoices.setId(1L);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvoiceDate(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCreditNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setIsCreditNote(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDryItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setTotalDryItems(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalChillerItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setTotalChillerItems(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].creditNoteReason").value(hasItem(DEFAULT_CREDIT_NOTE_REASON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].totalDryItems").value(hasItem(DEFAULT_TOTAL_DRY_ITEMS)))
            .andExpect(jsonPath("$.[*].totalChillerItems").value(hasItem(DEFAULT_TOTAL_CHILLER_ITEMS)))
            .andExpect(jsonPath("$.[*].deliveryRun").value(hasItem(DEFAULT_DELIVERY_RUN.toString())))
            .andExpect(jsonPath("$.[*].runPosition").value(hasItem(DEFAULT_RUN_POSITION.toString())))
            .andExpect(jsonPath("$.[*].returnedDeliveryData").value(hasItem(DEFAULT_RETURNED_DELIVERY_DATA.toString())))
            .andExpect(jsonPath("$.[*].confirmedDeliveryTime").value(hasItem(DEFAULT_CONFIRMED_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].confirmedReceivedBy").value(hasItem(DEFAULT_CONFIRMED_RECEIVED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.customerPurchaseOrderNumber").value(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.isCreditNote").value(DEFAULT_IS_CREDIT_NOTE.booleanValue()))
            .andExpect(jsonPath("$.creditNoteReason").value(DEFAULT_CREDIT_NOTE_REASON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.deliveryInstructions").value(DEFAULT_DELIVERY_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.totalDryItems").value(DEFAULT_TOTAL_DRY_ITEMS))
            .andExpect(jsonPath("$.totalChillerItems").value(DEFAULT_TOTAL_CHILLER_ITEMS))
            .andExpect(jsonPath("$.deliveryRun").value(DEFAULT_DELIVERY_RUN.toString()))
            .andExpect(jsonPath("$.runPosition").value(DEFAULT_RUN_POSITION.toString()))
            .andExpect(jsonPath("$.returnedDeliveryData").value(DEFAULT_RETURNED_DELIVERY_DATA.toString()))
            .andExpect(jsonPath("$.confirmedDeliveryTime").value(DEFAULT_CONFIRMED_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.confirmedReceivedBy").value(DEFAULT_CONFIRMED_RECEIVED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findById(invoices.getId()).get();
        // Disconnect from session so that the updates on updatedInvoices are not directly saved in db
        em.detach(updatedInvoices);
        updatedInvoices
            .invoiceDate(UPDATED_INVOICE_DATE)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .isCreditNote(UPDATED_IS_CREDIT_NOTE)
            .creditNoteReason(UPDATED_CREDIT_NOTE_REASON)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .totalDryItems(UPDATED_TOTAL_DRY_ITEMS)
            .totalChillerItems(UPDATED_TOTAL_CHILLER_ITEMS)
            .deliveryRun(UPDATED_DELIVERY_RUN)
            .runPosition(UPDATED_RUN_POSITION)
            .returnedDeliveryData(UPDATED_RETURNED_DELIVERY_DATA)
            .confirmedDeliveryTime(UPDATED_CONFIRMED_DELIVERY_TIME)
            .confirmedReceivedBy(UPDATED_CONFIRMED_RECEIVED_BY);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(updatedInvoices);

        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getCustomerPurchaseOrderNumber()).isEqualTo(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testInvoices.isIsCreditNote()).isEqualTo(UPDATED_IS_CREDIT_NOTE);
        assertThat(testInvoices.getCreditNoteReason()).isEqualTo(UPDATED_CREDIT_NOTE_REASON);
        assertThat(testInvoices.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInvoices.getDeliveryInstructions()).isEqualTo(UPDATED_DELIVERY_INSTRUCTIONS);
        assertThat(testInvoices.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testInvoices.getTotalDryItems()).isEqualTo(UPDATED_TOTAL_DRY_ITEMS);
        assertThat(testInvoices.getTotalChillerItems()).isEqualTo(UPDATED_TOTAL_CHILLER_ITEMS);
        assertThat(testInvoices.getDeliveryRun()).isEqualTo(UPDATED_DELIVERY_RUN);
        assertThat(testInvoices.getRunPosition()).isEqualTo(UPDATED_RUN_POSITION);
        assertThat(testInvoices.getReturnedDeliveryData()).isEqualTo(UPDATED_RETURNED_DELIVERY_DATA);
        assertThat(testInvoices.getConfirmedDeliveryTime()).isEqualTo(UPDATED_CONFIRMED_DELIVERY_TIME);
        assertThat(testInvoices.getConfirmedReceivedBy()).isEqualTo(UPDATED_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Delete the invoices
        restInvoicesMockMvc.perform(delete("/api/invoices/{id}", invoices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoices.class);
        Invoices invoices1 = new Invoices();
        invoices1.setId(1L);
        Invoices invoices2 = new Invoices();
        invoices2.setId(invoices1.getId());
        assertThat(invoices1).isEqualTo(invoices2);
        invoices2.setId(2L);
        assertThat(invoices1).isNotEqualTo(invoices2);
        invoices1.setId(null);
        assertThat(invoices1).isNotEqualTo(invoices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoicesDTO.class);
        InvoicesDTO invoicesDTO1 = new InvoicesDTO();
        invoicesDTO1.setId(1L);
        InvoicesDTO invoicesDTO2 = new InvoicesDTO();
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO2.setId(invoicesDTO1.getId());
        assertThat(invoicesDTO1).isEqualTo(invoicesDTO2);
        invoicesDTO2.setId(2L);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO1.setId(null);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoicesMapper.fromId(null)).isNull();
    }
}
