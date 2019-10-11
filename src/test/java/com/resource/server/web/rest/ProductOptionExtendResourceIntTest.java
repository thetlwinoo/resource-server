package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductOptionExtendService;
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
 * Test class for the ProductOptionExtendResource REST controller.
 *
 * @see ProductOptionExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductOptionExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ProductOptionExtendService productOptionExtendService;

    public ProductOptionExtendResourceIntTest(ProductOptionExtendService productOptionExtendService) {
        this.productOptionExtendService = productOptionExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductOptionExtendResource productOptionExtendResource = new ProductOptionExtendResource(productOptionExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productOptionExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-option-extend/default-action"))
            .andExpect(status().isOk());
    }
}
