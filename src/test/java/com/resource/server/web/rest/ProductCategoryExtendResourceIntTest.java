package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductCategoryExtendService;
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
 * Test class for the ProductCategoryExtendResource REST controller.
 *
 * @see ProductCategoryExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductCategoryExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ProductCategoryExtendService productCategoryExtendService;

    public ProductCategoryExtendResourceIntTest(ProductCategoryExtendService productCategoryExtendService) {
        this.productCategoryExtendService = productCategoryExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductCategoryExtendResource productCategoryExtendResource = new ProductCategoryExtendResource(productCategoryExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productCategoryExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-category-extend/default-action"))
            .andExpect(status().isOk());
    }
}
