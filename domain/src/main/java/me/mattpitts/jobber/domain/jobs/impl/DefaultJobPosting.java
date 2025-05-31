package me.mattpitts.jobber.domain.jobs.impl;

import me.mattpitts.jobber.domain.jobs.JobPosting;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class DefaultJobPosting implements JobPosting {
    private final String key;
    private final String url;
    private final DefaultJobPostingData data;
}
