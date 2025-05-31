package me.mattpitts.jobber.application.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import me.mattpitts.jobber.controller.JobPostingController;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPosting;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPostingData;

@WebMvcTest(JobPostingResource.class)
@SpringBootTest
public class JobPostingResourceTest {

    @MockitoBean
    JobPostingController<DefaultJobPosting, DefaultJobPostingData> mockController;

    @InjectMocks
    private JobPostingResource underTest;

    private MockMvc mockMvc;

    /**
     * Tests the search method of JobPostingResource. Verifies that the method returns a
     * ResponseEntity with OK status and the result of jobPostingController.findAll().
     */
    @Test
    public void testSearchReturnsAllJobPostings() throws Exception {
        when(mockController.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/job-postings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));

        verify(mockController).findAll();
    }

    /**
     * Test case for createJobPosting method with an empty URL. This test verifies that the method
     * handles empty input correctly.
     */
    @Test
    public void test_createJobPosting_emptyUrl() throws Exception {
        when(mockController.create(anyString()))
                .thenThrow(new IllegalArgumentException("URL cannot be empty"));

        ResponseEntity<Object> response = underTest.createJobPosting("");

        assertEquals(400, response.getStatusCode());
        assertEquals("URL cannot be empty", response.getBody());
    }

    /**
     * Test case for createJobPosting method when an exception occurs during creation. This test
     * verifies that the method handles exceptions thrown by the controller.
     */
    @Test
    public void test_createJobPosting_exceptionDuringCreation() throws Exception {
        when(mockController.create(anyString()))
                .thenThrow(new RuntimeException("Error creating job posting"));

        ResponseEntity<Object> response = underTest.createJobPosting("https://example.com/job");

        assertEquals(500, response.getStatusCode());
        assertEquals("Error creating job posting", response.getBody());
    }

    /**
     * Test case for createJobPosting method when a valid URL is provided. It verifies that the
     * method returns a ResponseEntity with OK status and the created job posting object from the
     * controller.
     */
    @Test
    public void test_createJobPosting_returnsCreatedJobPosting() throws Exception {
        // Arrange
        JobPostingResource resource = new JobPostingResource(mockController);
        String url = "https://example.com/job";
        DefaultJobPosting createdJobPosting = DefaultJobPosting.builder().build();
        when(mockController.create(url)).thenReturn(createdJobPosting);

        // Act
        ResponseEntity<Object> response = resource.createJobPosting(url);

        // Assert
        assertEquals(ResponseEntity.ok(createdJobPosting), response);
    }

}
