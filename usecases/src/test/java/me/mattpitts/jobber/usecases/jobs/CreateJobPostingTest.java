package me.mattpitts.jobber.usecases.jobs;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.function.BiFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingData;

@ExtendWith(MockitoExtension.class)
class CreateJobPostingTest {

    @Mock
    private ConvertUrlToDocument convertUrlToDocument;
    @Mock
    private ExtractJobData<JobPostingData> extractJobData;
    @Mock
    private StoreJobPosting<JobPosting> storeJobPosting;
    @Mock
    private BiFunction<String, JobPostingData, JobPosting> createJobPosting;
    private CreateJobPosting<JobPosting, JobPostingData> underTest;

    @BeforeEach
    void setUp() {
        underTest = CreateJobPosting.<JobPosting, JobPostingData>builder()
                .convertUrlToDocument(convertUrlToDocument).extractJobData(extractJobData)
                .storeJobPosting(storeJobPosting).createJobPosting(createJobPosting).build();
    }

    @Test
    @DisplayName("execute should call dependencies and return stored posting")
    void execute_shouldCallDependenciesAndReturnStoredPosting() throws Exception {
        String url = "http://example.com/job";
        byte[] document = new byte[] {1, 2, 3};
        JobPostingData jobPostingData = mock(JobPostingData.class);
        JobPosting posting = mock(JobPosting.class);
        JobPosting storedPosting = mock(JobPosting.class);

        when(convertUrlToDocument.execute(url)).thenReturn(document);
        when(extractJobData.execute(document)).thenReturn(jobPostingData);
        when(createJobPosting.apply(url, jobPostingData)).thenReturn(posting);
        when(storeJobPosting.execute(posting)).thenReturn(storedPosting);

        JobPosting result = underTest.execute(url);

        assertSame(storedPosting, result);
        verify(convertUrlToDocument).execute(url);
        verify(extractJobData).execute(document);
        verify(createJobPosting).apply(url, jobPostingData);
        verify(storeJobPosting).execute(posting);
    }

    @Test
    @DisplayName("execute should throw exception if convert fails")
    void execute_shouldThrowExceptionIfConvertFails() throws Exception {
        String url = "bad-url";
        when(convertUrlToDocument.execute(url)).thenThrow(new RuntimeException("fail"));

        assertThrows(RuntimeException.class, () -> underTest.execute(url));
        verify(convertUrlToDocument).execute(url);
        verifyNoMoreInteractions(extractJobData, createJobPosting, storeJobPosting);
    }
}
