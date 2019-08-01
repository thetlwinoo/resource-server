package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.MembershipService;
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
 * Test class for the MembershipResource REST controller.
 *
 * @see MembershipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class MembershipResourceIntTest {

    private MockMvc restMockMvc;
    private final MembershipService membershipService;

    public MembershipResourceIntTest(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        MembershipResource membershipResource = new MembershipResource(membershipService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(membershipResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/membership/default-action"))
            .andExpect(status().isOk());
    }
}
