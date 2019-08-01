package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.AddressesExtendService;
import com.resource.server.service.AddressesService;
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
 * Test class for the AddressesExtendResource REST controller.
 *
 * @see AddressesExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class AddressesExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final AddressesService addressesService;
    private final AddressesExtendService addressesExtendService;

    public AddressesExtendResourceIntTest(AddressesService addressesService, AddressesExtendService addressesExtendService) {
        this.addressesService = addressesService;
        this.addressesExtendService = addressesExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        AddressesExtendResource addressesExtendResource = new AddressesExtendResource(addressesExtendService, addressesService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(addressesExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/addresses-extend/default-action"))
            .andExpect(status().isOk());
    }
}
