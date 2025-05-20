package me.mattpitts.jobber.usecases.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;

@ExtendWith(MockitoExtension.class)
class ListJobPostingsTest {

    @Mock
    private JobPostingRepository<JobPosting> jobRepository;

    private ListJobPostings<JobPosting> underTest;

    @BeforeEach
    void setUp() {
        underTest = ListJobPostings.<JobPosting>builder()
                .jobRepository(jobRepository)
                .build();
    }

    @Test
    @DisplayName("execute should return all job postings")
    void execute_shouldReturnAllJobPostings() {
        JobPosting job1 = mock(JobPosting.class);
        JobPosting job2 = mock(JobPosting.class);
        List<JobPosting> jobs = Arrays.asList(job1, job2);

        when(jobRepository.findAll()).thenReturn(jobs);

        List<JobPosting> result = underTest.execute();

        assertEquals(jobs, result);
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("execute should return empty list when no jobs are found")
    void execute_shouldReturnEmptyListWhenNoJobs() {
        when(jobRepository.findAll()).thenReturn(Collections.emptyList());

        List<JobPosting> result = underTest.execute();

        assertTrue(result.isEmpty());
        verify(jobRepository, times(1)).findAll();
    }
}