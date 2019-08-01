package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ReviewsExtendService;
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
 * Test class for the ReviewsExtendResource REST controller.
 *
 * @see ReviewsExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ReviewsExtendResourceIntTest {

    private MockMvc restMockMvc;
    private final ReviewsExtendService reviewsExtendService;

    public ReviewsExtendResourceIntTest(ReviewsExtendService reviewsExtendService) {
        this.reviewsExtendService = reviewsExtendService;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ReviewsExtendResource reviewsExtendResource = new ReviewsExtendResource(reviewsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(reviewsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/reviews-extend/default-action"))
            .andExpect(status().isOk());
    }
}
