package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductChoiceExtendService;
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
 * Test class for the ProductChoiceExtendResource REST controller.
 *
 * @see ProductChoiceExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductChoiceExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ProductChoiceExtendService productChoiceExtendService;

    public ProductChoiceExtendResourceIntTest(ProductChoiceExtendService productChoiceExtendService) {
        this.productChoiceExtendService = productChoiceExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductChoiceExtendResource productChoiceExtendResource = new ProductChoiceExtendResource(productChoiceExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productChoiceExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-choice-extend/default-action"))
            .andExpect(status().isOk());
    }
}
