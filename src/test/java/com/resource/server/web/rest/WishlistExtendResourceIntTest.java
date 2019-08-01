package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.WishlistExtendService;
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
 * Test class for the WishlistExtendResource REST controller.
 *
 * @see WishlistExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class WishlistExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final WishlistExtendService wishlistExtendService;

    public WishlistExtendResourceIntTest(WishlistExtendService wishlistExtendService) {
        this.wishlistExtendService = wishlistExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        WishlistExtendResource wishlistExtendResource = new WishlistExtendResource(wishlistExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(wishlistExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/wishlist-extend/default-action"))
            .andExpect(status().isOk());
    }
}
