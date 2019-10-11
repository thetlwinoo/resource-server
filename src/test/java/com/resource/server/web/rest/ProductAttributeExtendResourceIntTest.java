package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductAttributeExtendService;
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
 * Test class for the ProductAttributeExtendResource REST controller.
 *
 * @see ProductAttributeExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductAttributeExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ProductAttributeExtendService productAttributeExtendService;

    public ProductAttributeExtendResourceIntTest(ProductAttributeExtendService productAttributeExtendService) {
        this.productAttributeExtendService = productAttributeExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductAttributeExtendResource productAttributeExtendResource = new ProductAttributeExtendResource(productAttributeExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productAttributeExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-attribute-extend/default-action"))
            .andExpect(status().isOk());
    }
}
