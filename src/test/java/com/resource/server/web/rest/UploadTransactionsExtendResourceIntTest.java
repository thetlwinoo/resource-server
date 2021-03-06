package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.UploadTransactionsExtendService;
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
 * Test class for the UploadTransactionsExtendResource REST controller.
 *
 * @see UploadTransactionsExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class UploadTransactionsExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final UploadTransactionsExtendService uploadTransactionsExtendService;

    public UploadTransactionsExtendResourceIntTest(UploadTransactionsExtendService uploadTransactionsExtendService) {
        this.uploadTransactionsExtendService = uploadTransactionsExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        UploadTransactionsExtendResource uploadTransactionsExtendResource = new UploadTransactionsExtendResource(uploadTransactionsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(uploadTransactionsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/upload-transactions-extend/default-action"))
            .andExpect(status().isOk());
    }
}
