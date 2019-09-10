package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Orders;
import com.resource.server.domain.Reviews;
import com.resource.server.domain.OrderLines;
import com.resource.server.domain.Customers;
import com.resource.server.domain.Addresses;
import com.resource.server.domain.ShipMethod;
import com.resource.server.domain.CurrencyRate;
import com.resource.server.domain.PaymentTransactions;
import com.resource.server.domain.SpecialDeals;
import com.resource.server.repository.OrdersRepository;
import com.resource.server.service.OrdersService;
import com.resource.server.service.dto.OrdersDTO;
import com.resource.server.service.mapper.OrdersMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.OrdersCriteria;
import com.resource.server.service.OrdersQueryService;

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
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class OrdersResourceIntTest {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SHIP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PAYMENT_STATUS = 1;
    private static final Integer UPDATED_PAYMENT_STATUS = 2;

    private static final Integer DEFAULT_ORDER_FLAG = 1;
    private static final Integer UPDATED_ORDER_FLAG = 2;

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_SUB_TOTAL = 1F;
    private static final Float UPDATED_SUB_TOTAL = 2F;

    private static final Float DEFAULT_TAX_AMOUNT = 1F;
    private static final Float UPDATED_TAX_AMOUNT = 2F;

    private static final Float DEFAULT_FRIEIGHT = 1F;
    private static final Float UPDATED_FRIEIGHT = 2F;

    private static final Float DEFAULT_TOTAL_DUE = 1F;
    private static final Float UPDATED_TOTAL_DUE = 2F;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PICKING_COMPLETED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKING_COMPLETED_WHEN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersQueryService ordersQueryService;

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

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdersResource ordersResource = new OrdersResource(ordersService, ordersQueryService);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
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
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderDate(DEFAULT_ORDER_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .shipDate(DEFAULT_SHIP_DATE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .orderFlag(DEFAULT_ORDER_FLAG)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .subTotal(DEFAULT_SUB_TOTAL)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .frieight(DEFAULT_FRIEIGHT)
            .totalDue(DEFAULT_TOTAL_DUE)
            .comments(DEFAULT_COMMENTS)
            .deliveryInstructions(DEFAULT_DELIVERY_INSTRUCTIONS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .pickingCompletedWhen(DEFAULT_PICKING_COMPLETED_WHEN);
        return orders;
    }

    @Before
    public void initTest() {
        orders = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrders.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testOrders.getShipDate()).isEqualTo(DEFAULT_SHIP_DATE);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrders.getOrderFlag()).isEqualTo(DEFAULT_ORDER_FLAG);
        assertThat(testOrders.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrders.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testOrders.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testOrders.getFrieight()).isEqualTo(DEFAULT_FRIEIGHT);
        assertThat(testOrders.getTotalDue()).isEqualTo(DEFAULT_TOTAL_DUE);
        assertThat(testOrders.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testOrders.getDeliveryInstructions()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTIONS);
        assertThat(testOrders.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testOrders.getPickingCompletedWhen()).isEqualTo(DEFAULT_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(1L);
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setOrderDate(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].shipDate").value(hasItem(DEFAULT_SHIP_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].orderFlag").value(hasItem(DEFAULT_ORDER_FLAG)))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].frieight").value(hasItem(DEFAULT_FRIEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDue").value(hasItem(DEFAULT_TOTAL_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.shipDate").value(DEFAULT_SHIP_DATE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS))
            .andExpect(jsonPath("$.orderFlag").value(DEFAULT_ORDER_FLAG))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.frieight").value(DEFAULT_FRIEIGHT.doubleValue()))
            .andExpect(jsonPath("$.totalDue").value(DEFAULT_TOTAL_DUE.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.deliveryInstructions").value(DEFAULT_DELIVERY_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.pickingCompletedWhen").value(DEFAULT_PICKING_COMPLETED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate equals to DEFAULT_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the ordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the ordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate is not null
        defaultOrdersShouldBeFound("orderDate.specified=true");

        // Get all the ordersList where orderDate is null
        defaultOrdersShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate greater than or equals to DEFAULT_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.greaterOrEqualThan=" + DEFAULT_ORDER_DATE);

        // Get all the ordersList where orderDate greater than or equals to UPDATED_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.greaterOrEqualThan=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate less than or equals to DEFAULT_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.lessThan=" + DEFAULT_ORDER_DATE);

        // Get all the ordersList where orderDate less than or equals to UPDATED_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.lessThan=" + UPDATED_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where dueDate equals to DEFAULT_DUE_DATE
        defaultOrdersShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the ordersList where dueDate equals to UPDATED_DUE_DATE
        defaultOrdersShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultOrdersShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the ordersList where dueDate equals to UPDATED_DUE_DATE
        defaultOrdersShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where dueDate is not null
        defaultOrdersShouldBeFound("dueDate.specified=true");

        // Get all the ordersList where dueDate is null
        defaultOrdersShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where dueDate greater than or equals to DEFAULT_DUE_DATE
        defaultOrdersShouldBeFound("dueDate.greaterOrEqualThan=" + DEFAULT_DUE_DATE);

        // Get all the ordersList where dueDate greater than or equals to UPDATED_DUE_DATE
        defaultOrdersShouldNotBeFound("dueDate.greaterOrEqualThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where dueDate less than or equals to DEFAULT_DUE_DATE
        defaultOrdersShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the ordersList where dueDate less than or equals to UPDATED_DUE_DATE
        defaultOrdersShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByShipDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where shipDate equals to DEFAULT_SHIP_DATE
        defaultOrdersShouldBeFound("shipDate.equals=" + DEFAULT_SHIP_DATE);

        // Get all the ordersList where shipDate equals to UPDATED_SHIP_DATE
        defaultOrdersShouldNotBeFound("shipDate.equals=" + UPDATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByShipDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where shipDate in DEFAULT_SHIP_DATE or UPDATED_SHIP_DATE
        defaultOrdersShouldBeFound("shipDate.in=" + DEFAULT_SHIP_DATE + "," + UPDATED_SHIP_DATE);

        // Get all the ordersList where shipDate equals to UPDATED_SHIP_DATE
        defaultOrdersShouldNotBeFound("shipDate.in=" + UPDATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByShipDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where shipDate is not null
        defaultOrdersShouldBeFound("shipDate.specified=true");

        // Get all the ordersList where shipDate is null
        defaultOrdersShouldNotBeFound("shipDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByShipDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where shipDate greater than or equals to DEFAULT_SHIP_DATE
        defaultOrdersShouldBeFound("shipDate.greaterOrEqualThan=" + DEFAULT_SHIP_DATE);

        // Get all the ordersList where shipDate greater than or equals to UPDATED_SHIP_DATE
        defaultOrdersShouldNotBeFound("shipDate.greaterOrEqualThan=" + UPDATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByShipDateIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where shipDate less than or equals to DEFAULT_SHIP_DATE
        defaultOrdersShouldNotBeFound("shipDate.lessThan=" + DEFAULT_SHIP_DATE);

        // Get all the ordersList where shipDate less than or equals to UPDATED_SHIP_DATE
        defaultOrdersShouldBeFound("shipDate.lessThan=" + UPDATED_SHIP_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus is not null
        defaultOrdersShouldBeFound("paymentStatus.specified=true");

        // Get all the ordersList where paymentStatus is null
        defaultOrdersShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus greater than or equals to DEFAULT_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.greaterOrEqualThan=" + DEFAULT_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus greater than or equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.greaterOrEqualThan=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus less than or equals to DEFAULT_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.lessThan=" + DEFAULT_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus less than or equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.lessThan=" + UPDATED_PAYMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderFlag equals to DEFAULT_ORDER_FLAG
        defaultOrdersShouldBeFound("orderFlag.equals=" + DEFAULT_ORDER_FLAG);

        // Get all the ordersList where orderFlag equals to UPDATED_ORDER_FLAG
        defaultOrdersShouldNotBeFound("orderFlag.equals=" + UPDATED_ORDER_FLAG);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderFlagIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderFlag in DEFAULT_ORDER_FLAG or UPDATED_ORDER_FLAG
        defaultOrdersShouldBeFound("orderFlag.in=" + DEFAULT_ORDER_FLAG + "," + UPDATED_ORDER_FLAG);

        // Get all the ordersList where orderFlag equals to UPDATED_ORDER_FLAG
        defaultOrdersShouldNotBeFound("orderFlag.in=" + UPDATED_ORDER_FLAG);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderFlag is not null
        defaultOrdersShouldBeFound("orderFlag.specified=true");

        // Get all the ordersList where orderFlag is null
        defaultOrdersShouldNotBeFound("orderFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderFlag greater than or equals to DEFAULT_ORDER_FLAG
        defaultOrdersShouldBeFound("orderFlag.greaterOrEqualThan=" + DEFAULT_ORDER_FLAG);

        // Get all the ordersList where orderFlag greater than or equals to UPDATED_ORDER_FLAG
        defaultOrdersShouldNotBeFound("orderFlag.greaterOrEqualThan=" + UPDATED_ORDER_FLAG);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderFlag less than or equals to DEFAULT_ORDER_FLAG
        defaultOrdersShouldNotBeFound("orderFlag.lessThan=" + DEFAULT_ORDER_FLAG);

        // Get all the ordersList where orderFlag less than or equals to UPDATED_ORDER_FLAG
        defaultOrdersShouldBeFound("orderFlag.lessThan=" + UPDATED_ORDER_FLAG);
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderNumber equals to DEFAULT_ORDER_NUMBER
        defaultOrdersShouldBeFound("orderNumber.equals=" + DEFAULT_ORDER_NUMBER);

        // Get all the ordersList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("orderNumber.equals=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderNumber in DEFAULT_ORDER_NUMBER or UPDATED_ORDER_NUMBER
        defaultOrdersShouldBeFound("orderNumber.in=" + DEFAULT_ORDER_NUMBER + "," + UPDATED_ORDER_NUMBER);

        // Get all the ordersList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("orderNumber.in=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderNumber is not null
        defaultOrdersShouldBeFound("orderNumber.specified=true");

        // Get all the ordersList where orderNumber is null
        defaultOrdersShouldNotBeFound("orderNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal equals to DEFAULT_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.equals=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal equals to UPDATED_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.equals=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal in DEFAULT_SUB_TOTAL or UPDATED_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.in=" + DEFAULT_SUB_TOTAL + "," + UPDATED_SUB_TOTAL);

        // Get all the ordersList where subTotal equals to UPDATED_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.in=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is not null
        defaultOrdersShouldBeFound("subTotal.specified=true");

        // Get all the ordersList where subTotal is null
        defaultOrdersShouldNotBeFound("subTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultOrdersShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the ordersList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultOrdersShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the ordersList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where taxAmount is not null
        defaultOrdersShouldBeFound("taxAmount.specified=true");

        // Get all the ordersList where taxAmount is null
        defaultOrdersShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByFrieightIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where frieight equals to DEFAULT_FRIEIGHT
        defaultOrdersShouldBeFound("frieight.equals=" + DEFAULT_FRIEIGHT);

        // Get all the ordersList where frieight equals to UPDATED_FRIEIGHT
        defaultOrdersShouldNotBeFound("frieight.equals=" + UPDATED_FRIEIGHT);
    }

    @Test
    @Transactional
    public void getAllOrdersByFrieightIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where frieight in DEFAULT_FRIEIGHT or UPDATED_FRIEIGHT
        defaultOrdersShouldBeFound("frieight.in=" + DEFAULT_FRIEIGHT + "," + UPDATED_FRIEIGHT);

        // Get all the ordersList where frieight equals to UPDATED_FRIEIGHT
        defaultOrdersShouldNotBeFound("frieight.in=" + UPDATED_FRIEIGHT);
    }

    @Test
    @Transactional
    public void getAllOrdersByFrieightIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where frieight is not null
        defaultOrdersShouldBeFound("frieight.specified=true");

        // Get all the ordersList where frieight is null
        defaultOrdersShouldNotBeFound("frieight.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue equals to DEFAULT_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.equals=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue equals to UPDATED_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.equals=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue in DEFAULT_TOTAL_DUE or UPDATED_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.in=" + DEFAULT_TOTAL_DUE + "," + UPDATED_TOTAL_DUE);

        // Get all the ordersList where totalDue equals to UPDATED_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.in=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is not null
        defaultOrdersShouldBeFound("totalDue.specified=true");

        // Get all the ordersList where totalDue is null
        defaultOrdersShouldNotBeFound("totalDue.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where comments equals to DEFAULT_COMMENTS
        defaultOrdersShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the ordersList where comments equals to UPDATED_COMMENTS
        defaultOrdersShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultOrdersShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the ordersList where comments equals to UPDATED_COMMENTS
        defaultOrdersShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where comments is not null
        defaultOrdersShouldBeFound("comments.specified=true");

        // Get all the ordersList where comments is null
        defaultOrdersShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryInstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where deliveryInstructions equals to DEFAULT_DELIVERY_INSTRUCTIONS
        defaultOrdersShouldBeFound("deliveryInstructions.equals=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the ordersList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrdersShouldNotBeFound("deliveryInstructions.equals=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryInstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where deliveryInstructions in DEFAULT_DELIVERY_INSTRUCTIONS or UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrdersShouldBeFound("deliveryInstructions.in=" + DEFAULT_DELIVERY_INSTRUCTIONS + "," + UPDATED_DELIVERY_INSTRUCTIONS);

        // Get all the ordersList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrdersShouldNotBeFound("deliveryInstructions.in=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryInstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where deliveryInstructions is not null
        defaultOrdersShouldBeFound("deliveryInstructions.specified=true");

        // Get all the ordersList where deliveryInstructions is null
        defaultOrdersShouldNotBeFound("deliveryInstructions.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultOrdersShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the ordersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultOrdersShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrdersByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultOrdersShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the ordersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultOrdersShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrdersByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where internalComments is not null
        defaultOrdersShouldBeFound("internalComments.specified=true");

        // Get all the ordersList where internalComments is null
        defaultOrdersShouldNotBeFound("internalComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByPickingCompletedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where pickingCompletedWhen equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrdersShouldBeFound("pickingCompletedWhen.equals=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the ordersList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrdersShouldNotBeFound("pickingCompletedWhen.equals=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByPickingCompletedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where pickingCompletedWhen in DEFAULT_PICKING_COMPLETED_WHEN or UPDATED_PICKING_COMPLETED_WHEN
        defaultOrdersShouldBeFound("pickingCompletedWhen.in=" + DEFAULT_PICKING_COMPLETED_WHEN + "," + UPDATED_PICKING_COMPLETED_WHEN);

        // Get all the ordersList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrdersShouldNotBeFound("pickingCompletedWhen.in=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByPickingCompletedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where pickingCompletedWhen is not null
        defaultOrdersShouldBeFound("pickingCompletedWhen.specified=true");

        // Get all the ordersList where pickingCompletedWhen is null
        defaultOrdersShouldNotBeFound("pickingCompletedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByPickingCompletedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where pickingCompletedWhen greater than or equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrdersShouldBeFound("pickingCompletedWhen.greaterOrEqualThan=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the ordersList where pickingCompletedWhen greater than or equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrdersShouldNotBeFound("pickingCompletedWhen.greaterOrEqualThan=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByPickingCompletedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where pickingCompletedWhen less than or equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrdersShouldNotBeFound("pickingCompletedWhen.lessThan=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the ordersList where pickingCompletedWhen less than or equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrdersShouldBeFound("pickingCompletedWhen.lessThan=" + UPDATED_PICKING_COMPLETED_WHEN);
    }


    @Test
    @Transactional
    public void getAllOrdersByReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        Reviews review = ReviewsResourceIntTest.createEntity(em);
        em.persist(review);
        em.flush();
        orders.setReview(review);
        ordersRepository.saveAndFlush(orders);
        Long reviewId = review.getId();

        // Get all the ordersList where review equals to reviewId
        defaultOrdersShouldBeFound("reviewId.equals=" + reviewId);

        // Get all the ordersList where review equals to reviewId + 1
        defaultOrdersShouldNotBeFound("reviewId.equals=" + (reviewId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        OrderLines orderLineList = OrderLinesResourceIntTest.createEntity(em);
        em.persist(orderLineList);
        em.flush();
        orders.addOrderLineList(orderLineList);
        ordersRepository.saveAndFlush(orders);
        Long orderLineListId = orderLineList.getId();

        // Get all the ordersList where orderLineList equals to orderLineListId
        defaultOrdersShouldBeFound("orderLineListId.equals=" + orderLineListId);

        // Get all the ordersList where orderLineList equals to orderLineListId + 1
        defaultOrdersShouldNotBeFound("orderLineListId.equals=" + (orderLineListId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        Customers customer = CustomersResourceIntTest.createEntity(em);
        em.persist(customer);
        em.flush();
        orders.setCustomer(customer);
        ordersRepository.saveAndFlush(orders);
        Long customerId = customer.getId();

        // Get all the ordersList where customer equals to customerId
        defaultOrdersShouldBeFound("customerId.equals=" + customerId);

        // Get all the ordersList where customer equals to customerId + 1
        defaultOrdersShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByShipToAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        Addresses shipToAddress = AddressesResourceIntTest.createEntity(em);
        em.persist(shipToAddress);
        em.flush();
        orders.setShipToAddress(shipToAddress);
        ordersRepository.saveAndFlush(orders);
        Long shipToAddressId = shipToAddress.getId();

        // Get all the ordersList where shipToAddress equals to shipToAddressId
        defaultOrdersShouldBeFound("shipToAddressId.equals=" + shipToAddressId);

        // Get all the ordersList where shipToAddress equals to shipToAddressId + 1
        defaultOrdersShouldNotBeFound("shipToAddressId.equals=" + (shipToAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByBillToAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        Addresses billToAddress = AddressesResourceIntTest.createEntity(em);
        em.persist(billToAddress);
        em.flush();
        orders.setBillToAddress(billToAddress);
        ordersRepository.saveAndFlush(orders);
        Long billToAddressId = billToAddress.getId();

        // Get all the ordersList where billToAddress equals to billToAddressId
        defaultOrdersShouldBeFound("billToAddressId.equals=" + billToAddressId);

        // Get all the ordersList where billToAddress equals to billToAddressId + 1
        defaultOrdersShouldNotBeFound("billToAddressId.equals=" + (billToAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByShipMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        ShipMethod shipMethod = ShipMethodResourceIntTest.createEntity(em);
        em.persist(shipMethod);
        em.flush();
        orders.setShipMethod(shipMethod);
        ordersRepository.saveAndFlush(orders);
        Long shipMethodId = shipMethod.getId();

        // Get all the ordersList where shipMethod equals to shipMethodId
        defaultOrdersShouldBeFound("shipMethodId.equals=" + shipMethodId);

        // Get all the ordersList where shipMethod equals to shipMethodId + 1
        defaultOrdersShouldNotBeFound("shipMethodId.equals=" + (shipMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        CurrencyRate currencyRate = CurrencyRateResourceIntTest.createEntity(em);
        em.persist(currencyRate);
        em.flush();
        orders.setCurrencyRate(currencyRate);
        ordersRepository.saveAndFlush(orders);
        Long currencyRateId = currencyRate.getId();

        // Get all the ordersList where currencyRate equals to currencyRateId
        defaultOrdersShouldBeFound("currencyRateId.equals=" + currencyRateId);

        // Get all the ordersList where currencyRate equals to currencyRateId + 1
        defaultOrdersShouldNotBeFound("currencyRateId.equals=" + (currencyRateId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByPaymentTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        PaymentTransactions paymentTransaction = PaymentTransactionsResourceIntTest.createEntity(em);
        em.persist(paymentTransaction);
        em.flush();
        orders.setPaymentTransaction(paymentTransaction);
        paymentTransaction.setOrder(orders);
        ordersRepository.saveAndFlush(orders);
        Long paymentTransactionId = paymentTransaction.getId();

        // Get all the ordersList where paymentTransaction equals to paymentTransactionId
        defaultOrdersShouldBeFound("paymentTransactionId.equals=" + paymentTransactionId);

        // Get all the ordersList where paymentTransaction equals to paymentTransactionId + 1
        defaultOrdersShouldNotBeFound("paymentTransactionId.equals=" + (paymentTransactionId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersBySpecialDealsIsEqualToSomething() throws Exception {
        // Initialize the database
        SpecialDeals specialDeals = SpecialDealsResourceIntTest.createEntity(em);
        em.persist(specialDeals);
        em.flush();
        orders.setSpecialDeals(specialDeals);
        ordersRepository.saveAndFlush(orders);
        Long specialDealsId = specialDeals.getId();

        // Get all the ordersList where specialDeals equals to specialDealsId
        defaultOrdersShouldBeFound("specialDealsId.equals=" + specialDealsId);

        // Get all the ordersList where specialDeals equals to specialDealsId + 1
        defaultOrdersShouldNotBeFound("specialDealsId.equals=" + (specialDealsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOrdersShouldBeFound(String filter) throws Exception {
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].shipDate").value(hasItem(DEFAULT_SHIP_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].orderFlag").value(hasItem(DEFAULT_ORDER_FLAG)))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].frieight").value(hasItem(DEFAULT_FRIEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDue").value(hasItem(DEFAULT_TOTAL_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())));

        // Check, that the count call also returns 1
        restOrdersMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOrdersShouldNotBeFound(String filter) throws Exception {
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdersMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findById(orders.getId()).get();
        // Disconnect from session so that the updates on updatedOrders are not directly saved in db
        em.detach(updatedOrders);
        updatedOrders
            .orderDate(UPDATED_ORDER_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .shipDate(UPDATED_SHIP_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .orderFlag(UPDATED_ORDER_FLAG)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .subTotal(UPDATED_SUB_TOTAL)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .frieight(UPDATED_FRIEIGHT)
            .totalDue(UPDATED_TOTAL_DUE)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrders.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testOrders.getShipDate()).isEqualTo(UPDATED_SHIP_DATE);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrders.getOrderFlag()).isEqualTo(UPDATED_ORDER_FLAG);
        assertThat(testOrders.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrders.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testOrders.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testOrders.getFrieight()).isEqualTo(UPDATED_FRIEIGHT);
        assertThat(testOrders.getTotalDue()).isEqualTo(UPDATED_TOTAL_DUE);
        assertThat(testOrders.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrders.getDeliveryInstructions()).isEqualTo(UPDATED_DELIVERY_INSTRUCTIONS);
        assertThat(testOrders.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testOrders.getPickingCompletedWhen()).isEqualTo(UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Delete the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orders.class);
        Orders orders1 = new Orders();
        orders1.setId(1L);
        Orders orders2 = new Orders();
        orders2.setId(orders1.getId());
        assertThat(orders1).isEqualTo(orders2);
        orders2.setId(2L);
        assertThat(orders1).isNotEqualTo(orders2);
        orders1.setId(null);
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersDTO.class);
        OrdersDTO ordersDTO1 = new OrdersDTO();
        ordersDTO1.setId(1L);
        OrdersDTO ordersDTO2 = new OrdersDTO();
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO2.setId(ordersDTO1.getId());
        assertThat(ordersDTO1).isEqualTo(ordersDTO2);
        ordersDTO2.setId(2L);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO1.setId(null);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordersMapper.fromId(null)).isNull();
    }
}
