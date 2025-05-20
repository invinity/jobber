package me.mattpitts.jobber.domain.jobs;

public interface JobPosting {
    String getKey();
    String getUrl();
    <T extends JobPostingData> T getData();
}
