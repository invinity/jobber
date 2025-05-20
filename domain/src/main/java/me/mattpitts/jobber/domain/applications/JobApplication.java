package me.mattpitts.jobber.domain.applications;

import me.mattpitts.jobber.domain.jobs.JobPosting;

public interface JobApplication {
    public enum JobApplicationStatus {
        APPLIED,
        WITHDRAWN,
        INTERVIEWING,
        OFFER,
        REJECTED
    }

    JobPosting getJobPosting();
    String getCoverLetter();
    String getResume();
    JobApplicationStatus getStatus();
}
