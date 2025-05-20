package me.mattpitts.jobber.domain.jobs;

import java.util.List;

public interface JobPostingData {
    String getCompanyName();
    String getTitle();
    String getSummary();
    List<? extends JobSalaryRange> getSalaryRanges();
    List<String> getLocations();
    List<String> getRequiredQualifications();
    List<String> getDesiredQualifications();
    List<String> getResponsibilities();
}