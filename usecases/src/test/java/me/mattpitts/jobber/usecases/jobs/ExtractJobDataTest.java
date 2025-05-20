package me.mattpitts.jobber.usecases.jobs;

import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.domain.jobs.JobPostingDataExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtractJobDataTest {

    @Mock
    private JobPostingDataExtractor<JobPostingData> mockExtractor;

    private ExtractJobData<JobPostingData> underTest;

    @BeforeEach
    void setup() {
        underTest = ExtractJobData.<JobPostingData>builder()
                .jobDataExtractor(mockExtractor)
                .build();
    }

    @Test
    @DisplayName("execute should call JobPostingDataExtractor and return extracted data")
    void execute_shouldCallJobPostingDataExtractorAndReturnExtractedData() throws Exception {
        byte[] testDocument = new byte[]{1, 2, 3};
        JobPostingData expectedData = mock(JobPostingData.class);
        when(mockExtractor.extract(testDocument)).thenReturn(expectedData);

        JobPostingData result = underTest.execute(testDocument);

        assertEquals(expectedData, result);
        verify(mockExtractor, times(1)).extract(testDocument);
    }

    @Test
    @DisplayName("execute should throw Exception if JobPostingDataExtractor throws")
    void execute_shouldThrowExceptionIfJobPostingDataExtractorThrows() throws Exception {
        byte[] testDocument = new byte[]{1, 2, 3};
        when(mockExtractor.extract(testDocument)).thenThrow(new RuntimeException("Extraction failed"));

        assertThrows(RuntimeException.class, () -> underTest.execute(testDocument));
        verify(mockExtractor, times(1)).extract(testDocument);
    }
}