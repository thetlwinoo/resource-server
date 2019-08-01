package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.CompareExtendService;
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
 * Test class for the CompareExtendResource REST controller.
 *
 * @see CompareExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CompareExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final CompareExtendService compareExtendService;

    public CompareExtendResourceIntTest(CompareExtendService compareExtendService) {
        this.compareExtendService = compareExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CompareExtendResource compareExtendResource = new CompareExtendResource(compareExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(compareExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/compare-extend/default-action"))
            .andExpect(status().isOk());
    }
}
