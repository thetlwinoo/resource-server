package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.PurchaseOrders;
import com.resource.server.repository.PurchaseOrdersRepository;
import com.resource.server.service.PurchaseOrdersService;
import com.resource.server.service.dto.PurchaseOrdersDTO;
import com.resource.server.service.mapper.PurchaseOrdersMapper;
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
 * Test class for the PurchaseOrdersResource REST controller.
 *
 * @see PurchaseOrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class PurchaseOrdersResourceIntTest {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPECTED_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SUPPLIER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_REFERENCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ORDER_FINALIZED = false;
    private static final Boolean UPDATED_IS_ORDER_FINALIZED = true;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private PurchaseOrdersMapper purchaseOrdersMapper;

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;

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

    private MockMvc restPurchaseOrdersMockMvc;

    private PurchaseOrders purchaseOrders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrdersResource purchaseOrdersResource = new PurchaseOrdersResource(purchaseOrdersService);
        this.restPurchaseOrdersMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrdersResource)
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
    public static PurchaseOrders createEntity(EntityManager em) {
        PurchaseOrders purchaseOrders = new PurchaseOrders()
            .orderDate(DEFAULT_ORDER_DATE)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .supplierReference(DEFAULT_SUPPLIER_REFERENCE)
            .isOrderFinalized(DEFAULT_IS_ORDER_FINALIZED)
            .comments(DEFAULT_COMMENTS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS);
        return purchaseOrders;
    }

    @Before
    public void initTest() {
        purchaseOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrders() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testPurchaseOrders.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrders.getSupplierReference()).isEqualTo(DEFAULT_SUPPLIER_REFERENCE);
        assertThat(testPurchaseOrders.isIsOrderFinalized()).isEqualTo(DEFAULT_IS_ORDER_FINALIZED);
        assertThat(testPurchaseOrders.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPurchaseOrders.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void createPurchaseOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders with an existing ID
        purchaseOrders.setId(1L);
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrdersRepository.findAll().size();
        // set the field null
        purchaseOrders.setOrderDate(null);

        // Create the PurchaseOrders, which fails.
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].isOrderFinalized").value(hasItem(DEFAULT_IS_ORDER_FINALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE.toString()))
            .andExpect(jsonPath("$.isOrderFinalized").value(DEFAULT_IS_ORDER_FINALIZED.booleanValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrders() throws Exception {
        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Update the purchaseOrders
        PurchaseOrders updatedPurchaseOrders = purchaseOrdersRepository.findById(purchaseOrders.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrders are not directly saved in db
        em.detach(updatedPurchaseOrders);
        updatedPurchaseOrders
            .orderDate(UPDATED_ORDER_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .isOrderFinalized(UPDATED_IS_ORDER_FINALIZED)
            .comments(UPDATED_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS);
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(updatedPurchaseOrders);

        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testPurchaseOrders.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrders.getSupplierReference()).isEqualTo(UPDATED_SUPPLIER_REFERENCE);
        assertThat(testPurchaseOrders.isIsOrderFinalized()).isEqualTo(UPDATED_IS_ORDER_FINALIZED);
        assertThat(testPurchaseOrders.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPurchaseOrders.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrders() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeDelete = purchaseOrdersRepository.findAll().size();

        // Delete the purchaseOrders
        restPurchaseOrdersMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrders.class);
        PurchaseOrders purchaseOrders1 = new PurchaseOrders();
        purchaseOrders1.setId(1L);
        PurchaseOrders purchaseOrders2 = new PurchaseOrders();
        purchaseOrders2.setId(purchaseOrders1.getId());
        assertThat(purchaseOrders1).isEqualTo(purchaseOrders2);
        purchaseOrders2.setId(2L);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
        purchaseOrders1.setId(null);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrdersDTO.class);
        PurchaseOrdersDTO purchaseOrdersDTO1 = new PurchaseOrdersDTO();
        purchaseOrdersDTO1.setId(1L);
        PurchaseOrdersDTO purchaseOrdersDTO2 = new PurchaseOrdersDTO();
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO2.setId(purchaseOrdersDTO1.getId());
        assertThat(purchaseOrdersDTO1).isEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO2.setId(2L);
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO1.setId(null);
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrdersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrdersMapper.fromId(null)).isNull();
    }
}
