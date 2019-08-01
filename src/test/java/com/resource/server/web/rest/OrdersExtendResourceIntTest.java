package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.repository.OrdersExtendRepository;
import com.resource.server.service.OrdersExtendService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the OrdersExtendResource REST controller.
 *
 * @see OrdersExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class OrdersExtendResourceIntTest {

    private MockMvc restMockMvc;
    private OrdersExtendService ordersExtendService;
    private final OrdersExtendRepository ordersExtendRepository;

    public OrdersExtendResourceIntTest(OrdersExtendService ordersExtendService, OrdersExtendRepository ordersExtendRepository) {
        this.ordersExtendService = ordersExtendService;
        this.ordersExtendRepository = ordersExtendRepository;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrdersExtendResource ordersExtendResource = new OrdersExtendResource(ordersExtendService, ordersExtendRepository);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(ordersExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/orders-extend/default-action"))
            .andExpect(status().isOk());
    }
}
