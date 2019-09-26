package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.repository.ProductsRepository;
import com.resource.server.service.ProductPhotoService;
import com.resource.server.service.ProductsExtendService;
import com.resource.server.service.ProductsQueryService;
import com.resource.server.service.ProductsService;
import com.resource.server.service.mapper.ProductsMapper;
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
 * Test class for the ProductsExtendResource REST controller.
 *
 * @see ProductsExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ProductsExtendResourceIntTest {

    private MockMvc restMockMvc;
    private ProductsExtendService productExtendedService;
    private ProductsService productsService;
    private ProductPhotoService productPhotoService;
    private ProductsQueryService productsQueryService;
    private final ProductsMapper productsMapper;
    private final ProductsExtendRepository productsExtendRepository;

    public ProductsExtendResourceIntTest(ProductsExtendService productExtendedService, ProductsService productsService, ProductPhotoService productPhotoService, ProductsQueryService productsQueryService, ProductsMapper productsMapper, ProductsExtendRepository productsExtendRepository) {
        this.productExtendedService = productExtendedService;
        this.productsService = productsService;
        this.productPhotoService = productPhotoService;
        this.productsQueryService = productsQueryService;
        this.productsMapper = productsMapper;
        this.productsExtendRepository = productsExtendRepository;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductsExtendResource productsExtendResource = new ProductsExtendResource(productExtendedService, productsService, productPhotoService, productsQueryService, productsExtendRepository, productsMapper);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/products-extend/default-action"))
            .andExpect(status().isOk());
    }
}
