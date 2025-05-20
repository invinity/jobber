package me.mattpitts.jobber.usecases.jobs;

import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreJobPostingTest {

    @Mock
    private JobPostingRepository<JobPosting> mockJobRepository;

    @Mock
    private JobPosting mockJobPosting;

    private StoreJobPosting<JobPosting> underTest;

    @BeforeEach
    void setup() {
        this.underTest = StoreJobPosting.<JobPosting>builder()
                .jobRepository(mockJobRepository)
                .build();
    }

    @Test
    @DisplayName("execute should call jobRepository.save and return the saved job posting")
    void execute_shouldCallJobRepositorySaveAndReturnSavedJobPosting() {
        // Arrange
        when(mockJobRepository.save(mockJobPosting)).thenReturn(mockJobPosting);

        // Act
        JobPosting result = underTest.execute(mockJobPosting);

        // Assert
        assertEquals(mockJobPosting, result);
        verify(mockJobRepository, times(1)).save(mockJobPosting);
    }
}