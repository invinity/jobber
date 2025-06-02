package me.mattpitts.jobber.application.rest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import me.mattpitts.jobber.controller.JobPostingController;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPosting;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPostingData;

@WebMvcTest(JobPostingResource.class)
class JobPostingResourceTest {

    @MockitoBean
    JobPostingController<DefaultJobPosting, DefaultJobPostingData> mockController;

    @InjectMocks
    JobPostingResource underTest;

    @Autowired
    MockMvc mockMvc;

    /**
     * Tests the search method of JobPostingResource. Verifies that the method returns a
     * ResponseEntity with OK status and the result of jobPostingController.findAll().
     */
    @Test
    void GET_to_job_postings_should_return_all_job_postings() throws Exception {
        when(mockController.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/job-postings")).andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(mockController).findAll();
    }

    /**
     * Test case for createJobPosting method with an empty URL. This test verifies that the method
     * handles empty input correctly.
     */
    @Test
    void POST_to_job_postings_should_return_bad_request_when_url_is_empty() throws Exception {
        when(mockController.create(anyString()))
                .thenThrow(new IllegalArgumentException("URL cannot be empty"));

        mockMvc.perform(post("/job-postings").param("url", "")).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("URL cannot be empty"))
                .andExpect(jsonPath("$.errorType")
                        .value(IllegalArgumentException.class.getSimpleName()));
    }

    /**
     * Test case for createJobPosting method when an exception occurs during creation. This test
     * verifies that the method handles exceptions thrown by the controller.
     */
    @Test
    void POST_to_job_postings_should_return_server_error_when_controller_throws_exception()
            throws Exception {
        when(mockController.create(anyString()))
                .thenThrow(new RuntimeException("Error creating job posting"));

        mockMvc.perform(post("/job-postings").param("url", "https://example.com/job/12345"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Error creating job posting"))
                .andExpect(jsonPath("$.errorType").value(RuntimeException.class.getSimpleName()));
    }

    /**
     * Test case for createJobPosting method when a valid URL is provided. It verifies that the
     * method returns a ResponseEntity with OK status and the created job posting object from the
     * controller.
     */
    @Test
    void POST_to_job_postings_should_return_a_created_response_with_a_valid_request()
            throws Exception {
        String url = "https://example.com/job/12345";
        DefaultJobPosting createdJobPosting = DefaultJobPosting.builder().url(url).key("mykey").build();
        when(mockController.create(url)).thenReturn(createdJobPosting);
        mockMvc.perform(post("/job-postings").param("url", url)).andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        "http://localhost/job-postings/" + createdJobPosting.getKey()));
    }

}
