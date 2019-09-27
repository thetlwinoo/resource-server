package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.StockItemsExtendService;
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
 * Test class for the StockItemsExtendResource REST controller.
 *
 * @see StockItemsExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemsExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final StockItemsExtendService stockItemsExtendService;

    public StockItemsExtendResourceIntTest(StockItemsExtendService stockItemsExtendService) {
        this.stockItemsExtendService = stockItemsExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StockItemsExtendResource stockItemsExtendResource = new StockItemsExtendResource(stockItemsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stockItemsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stock-items-extend/default-action"))
            .andExpect(status().isOk());
    }
}
