package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
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
 * Test class for the ShoppingCartsExtendResource REST controller.
 *
 * @see ShoppingCartsExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ShoppingCartsExtendResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ShoppingCartsExtendResource shoppingCartsExtendResource = new ShoppingCartsExtendResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(shoppingCartsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/shopping-carts-extend/default-action"))
            .andExpect(status().isOk());
    }
}
