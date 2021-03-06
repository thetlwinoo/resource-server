package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.StripeClientService;
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
 * Test class for the StripeClientResource REST controller.
 *
 * @see StripeClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StripeClientResourceIntTest {

    private MockMvc restMockMvc;
    private final StripeClientService stripeClientService;

    public StripeClientResourceIntTest(StripeClientService stripeClientService) {
        this.stripeClientService = stripeClientService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StripeClientResource stripeClientResource = new StripeClientResource(stripeClientService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stripeClientResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stripe-client/default-action"))
            .andExpect(status().isOk());
    }
}
