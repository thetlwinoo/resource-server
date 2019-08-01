package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductPhotoExtendService;
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
 * Test class for the ProductPhotoExtendResource REST controller.
 *
 * @see ProductPhotoExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductPhotoExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ProductPhotoExtendService productPhotoExtendService;

    public ProductPhotoExtendResourceIntTest(ProductPhotoExtendService productPhotoExtendService) {
        this.productPhotoExtendService = productPhotoExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductPhotoExtendResource productPhotoExtendResource = new ProductPhotoExtendResource(productPhotoExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productPhotoExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-photo-extend/default-action"))
            .andExpect(status().isOk());
    }
}
