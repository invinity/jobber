package me.mattpitts.jobber.domain.applications;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class JobApplicationTest {

    @Test
    void jobApplicationStatusEnumExists() {
        assertNotNull(JobApplication.JobApplicationStatus.APPLIED);
        assertNotNull(JobApplication.JobApplicationStatus.WITHDRAWN);
        assertNotNull(JobApplication.JobApplicationStatus.INTERVIEWING);
        assertNotNull(JobApplication.JobApplicationStatus.OFFER);
        assertNotNull(JobApplication.JobApplicationStatus.REJECTED);
    }

    @Test
    void jobApplicationInterfaceExists() {
        JobApplication application = mock(JobApplication.class);
        assertNotNull(application);
    }

    @Test
    void jobApplicationInterfaceHasGetJobPostingMethod() {
        JobApplication application = mock(JobApplication.class);
        assertDoesNotThrow(application::getJobPosting);
    }

    @Test
    void jobApplicationInterfaceHasGetCoverLetterMethod() {
        JobApplication application = mock(JobApplication.class);
        assertDoesNotThrow(application::getCoverLetter);
    }

    @Test
    void jobApplicationInterfaceHasGetResumeMethod() {
        JobApplication application = mock(JobApplication.class);
        assertDoesNotThrow(application::getResume);
    }

    @Test
    void jobApplicationInterfaceHasGetStatusMethod() {
        JobApplication application = mock(JobApplication.class);
        assertDoesNotThrow(application::getStatus);
    }
}
