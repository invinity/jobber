package me.mattpitts.jobber.controller;

import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.usecases.jobs.ConvertUrlToDocument;
import me.mattpitts.jobber.usecases.jobs.CreateJobPosting;
import me.mattpitts.jobber.usecases.jobs.ExtractJobData;
import me.mattpitts.jobber.usecases.jobs.ListJobPostings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostingControllerTest {

    @Mock
    private ConvertUrlToDocument convertUrlToDocument;

    @Mock
    private ExtractJobData<JobPostingData> extractJobData;

    @Mock
    private CreateJobPosting<JobPosting, JobPostingData> createJobPosting;

    @Mock
    private ListJobPostings<JobPosting> listJobPostings;

    private JobPostingController<JobPosting, JobPostingData> underTest;

    @BeforeEach
    void setUp() {
        underTest = JobPostingController.<JobPosting, JobPostingData>builder()
                .convertUrlToDocument(convertUrlToDocument)
                .extractJobData(extractJobData)
                .createJobPosting(createJobPosting)
                .listJobPostings(listJobPostings)
                .build();
    }

    @Test
    @DisplayName("findAll should call listJobPostings.execute and return the result")
    void findAll_shouldCallListJobPostingsExecuteAndReturnResult() {
        // Arrange
        JobPosting job1 = mock(JobPosting.class);
        JobPosting job2 = mock(JobPosting.class);
        List<JobPosting> expectedJobs = Arrays.asList(job1, job2);
        when(listJobPostings.execute()).thenReturn(expectedJobs);

        // Act
        List<JobPosting> actualJobs = underTest.findAll();

        // Assert
        assertEquals(expectedJobs, actualJobs);
        verify(listJobPostings, times(1)).execute();
    }

    @Test
    @DisplayName("create should call createJobPosting.execute and return the result")
    void create_shouldCallCreateJobPostingExecuteAndReturnResult() throws Exception {
        // Arrange
        String testUrl = "http://example.com/job";
        JobPosting expectedJob = mock(JobPosting.class);
        when(createJobPosting.execute(testUrl)).thenReturn(expectedJob);

        // Act
        JobPosting actualJob = underTest.create(testUrl);

        // Assert
        assertEquals(expectedJob, actualJob);
        verify(createJobPosting, times(1)).execute(testUrl);
    }

    @Test
    @DisplayName("create should throw exception if createJobPosting.execute throws")
    void create_shouldThrowExceptionIfCreateJobPostingExecuteThrows() throws Exception {
        String testUrl = "http://example.com/job";
        when(createJobPosting.execute(testUrl)).thenThrow(new RuntimeException("Creation failed"));
        assertThrows(RuntimeException.class, () -> underTest.create(testUrl));
    }
}