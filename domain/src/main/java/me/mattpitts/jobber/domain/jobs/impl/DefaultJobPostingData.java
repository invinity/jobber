package me.mattpitts.jobber.domain.jobs.impl;

import java.util.List;
import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.domain.jobs.JobSalaryRange;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class DefaultJobPostingData implements JobPostingData {
    private final String companyName;
    private final String title;
    private final String summary;
    private final List<String> locations;
    private final List<String> requiredQualifications;
    private final List<String> desiredQualifications;
    private final List<String> responsibilities;
    private final List<JobSalaryRange> salaryRanges;
}
