package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.SuppliersExtendService;
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
 * Test class for the SuppliersExtendResource REST controller.
 *
 * @see SuppliersExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class SuppliersExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final SuppliersExtendService suppliersExtendService;

    public SuppliersExtendResourceIntTest(SuppliersExtendService suppliersExtendService) {
        this.suppliersExtendService = suppliersExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        SuppliersExtendResource suppliersExtendResource = new SuppliersExtendResource(suppliersExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(suppliersExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/suppliers-extend/default-action"))
            .andExpect(status().isOk());
    }
}
