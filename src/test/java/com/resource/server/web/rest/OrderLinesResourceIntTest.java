package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.OrderLines;
import com.resource.server.repository.OrderLinesRepository;
import com.resource.server.service.OrderLinesService;
import com.resource.server.service.dto.OrderLinesDTO;
import com.resource.server.service.mapper.OrderLinesMapper;
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
 * Test class for the OrderLinesResource REST controller.
 *
 * @see OrderLinesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class OrderLinesResourceIntTest {

    private static final String DEFAULT_CARRIER_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Float DEFAULT_UNIT_PRICE_DISCOUNT = 1F;
    private static final Float UPDATED_UNIT_PRICE_DISCOUNT = 2F;

    private static final Float DEFAULT_LINE_TOTAL = 1F;
    private static final Float UPDATED_LINE_TOTAL = 2F;

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;

    private static final Integer DEFAULT_PICKED_QUANTITY = 1;
    private static final Integer UPDATED_PICKED_QUANTITY = 2;

    private static final LocalDate DEFAULT_PICKING_COMPLETED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKING_COMPLETED_WHEN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderLinesRepository orderLinesRepository;

    @Autowired
    private OrderLinesMapper orderLinesMapper;

    @Autowired
    private OrderLinesService orderLinesService;

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

    private MockMvc restOrderLinesMockMvc;

    private OrderLines orderLines;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderLinesResource orderLinesResource = new OrderLinesResource(orderLinesService);
        this.restOrderLinesMockMvc = MockMvcBuilders.standaloneSetup(orderLinesResource)
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
    public static OrderLines createEntity(EntityManager em) {
        OrderLines orderLines = new OrderLines()
            .carrierTrackingNumber(DEFAULT_CARRIER_TRACKING_NUMBER)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .unitPriceDiscount(DEFAULT_UNIT_PRICE_DISCOUNT)
            .lineTotal(DEFAULT_LINE_TOTAL)
            .taxRate(DEFAULT_TAX_RATE)
            .pickedQuantity(DEFAULT_PICKED_QUANTITY)
            .pickingCompletedWhen(DEFAULT_PICKING_COMPLETED_WHEN);
        return orderLines;
    }

    @Before
    public void initTest() {
        orderLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderLines() throws Exception {
        int databaseSizeBeforeCreate = orderLinesRepository.findAll().size();

        // Create the OrderLines
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);
        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeCreate + 1);
        OrderLines testOrderLines = orderLinesList.get(orderLinesList.size() - 1);
        assertThat(testOrderLines.getCarrierTrackingNumber()).isEqualTo(DEFAULT_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderLines.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderLines.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testOrderLines.getUnitPriceDiscount()).isEqualTo(DEFAULT_UNIT_PRICE_DISCOUNT);
        assertThat(testOrderLines.getLineTotal()).isEqualTo(DEFAULT_LINE_TOTAL);
        assertThat(testOrderLines.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testOrderLines.getPickedQuantity()).isEqualTo(DEFAULT_PICKED_QUANTITY);
        assertThat(testOrderLines.getPickingCompletedWhen()).isEqualTo(DEFAULT_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void createOrderLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderLinesRepository.findAll().size();

        // Create the OrderLines with an existing ID
        orderLines.setId(1L);
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLinesRepository.findAll().size();
        // set the field null
        orderLines.setQuantity(null);

        // Create the OrderLines, which fails.
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList
        restOrderLinesMockMvc.perform(get("/api/order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].carrierTrackingNumber").value(hasItem(DEFAULT_CARRIER_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPriceDiscount").value(hasItem(DEFAULT_UNIT_PRICE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lineTotal").value(hasItem(DEFAULT_LINE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].pickedQuantity").value(hasItem(DEFAULT_PICKED_QUANTITY)))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())));
    }

    @Test
    @Transactional
    public void getOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get the orderLines
        restOrderLinesMockMvc.perform(get("/api/order-lines/{id}", orderLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderLines.getId().intValue()))
            .andExpect(jsonPath("$.carrierTrackingNumber").value(DEFAULT_CARRIER_TRACKING_NUMBER))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.unitPriceDiscount").value(DEFAULT_UNIT_PRICE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.lineTotal").value(DEFAULT_LINE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.pickedQuantity").value(DEFAULT_PICKED_QUANTITY))
            .andExpect(jsonPath("$.pickingCompletedWhen").value(DEFAULT_PICKING_COMPLETED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderLines() throws Exception {
        // Get the orderLines
        restOrderLinesMockMvc.perform(get("/api/order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        int databaseSizeBeforeUpdate = orderLinesRepository.findAll().size();

        // Update the orderLines
        OrderLines updatedOrderLines = orderLinesRepository.findById(orderLines.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLines are not directly saved in db
        em.detach(updatedOrderLines);
        updatedOrderLines
            .carrierTrackingNumber(UPDATED_CARRIER_TRACKING_NUMBER)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .unitPriceDiscount(UPDATED_UNIT_PRICE_DISCOUNT)
            .lineTotal(UPDATED_LINE_TOTAL)
            .taxRate(UPDATED_TAX_RATE)
            .pickedQuantity(UPDATED_PICKED_QUANTITY)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN);
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(updatedOrderLines);

        restOrderLinesMockMvc.perform(put("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isOk());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeUpdate);
        OrderLines testOrderLines = orderLinesList.get(orderLinesList.size() - 1);
        assertThat(testOrderLines.getCarrierTrackingNumber()).isEqualTo(UPDATED_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderLines.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderLines.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderLines.getUnitPriceDiscount()).isEqualTo(UPDATED_UNIT_PRICE_DISCOUNT);
        assertThat(testOrderLines.getLineTotal()).isEqualTo(UPDATED_LINE_TOTAL);
        assertThat(testOrderLines.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testOrderLines.getPickedQuantity()).isEqualTo(UPDATED_PICKED_QUANTITY);
        assertThat(testOrderLines.getPickingCompletedWhen()).isEqualTo(UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderLines() throws Exception {
        int databaseSizeBeforeUpdate = orderLinesRepository.findAll().size();

        // Create the OrderLines
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLinesMockMvc.perform(put("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        int databaseSizeBeforeDelete = orderLinesRepository.findAll().size();

        // Delete the orderLines
        restOrderLinesMockMvc.perform(delete("/api/order-lines/{id}", orderLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLines.class);
        OrderLines orderLines1 = new OrderLines();
        orderLines1.setId(1L);
        OrderLines orderLines2 = new OrderLines();
        orderLines2.setId(orderLines1.getId());
        assertThat(orderLines1).isEqualTo(orderLines2);
        orderLines2.setId(2L);
        assertThat(orderLines1).isNotEqualTo(orderLines2);
        orderLines1.setId(null);
        assertThat(orderLines1).isNotEqualTo(orderLines2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLinesDTO.class);
        OrderLinesDTO orderLinesDTO1 = new OrderLinesDTO();
        orderLinesDTO1.setId(1L);
        OrderLinesDTO orderLinesDTO2 = new OrderLinesDTO();
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
        orderLinesDTO2.setId(orderLinesDTO1.getId());
        assertThat(orderLinesDTO1).isEqualTo(orderLinesDTO2);
        orderLinesDTO2.setId(2L);
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
        orderLinesDTO1.setId(null);
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderLinesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderLinesMapper.fromId(null)).isNull();
    }
}
