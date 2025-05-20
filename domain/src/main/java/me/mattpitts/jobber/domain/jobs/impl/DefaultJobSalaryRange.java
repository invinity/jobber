package me.mattpitts.jobber.domain.jobs.impl;

import me.mattpitts.jobber.domain.jobs.JobSalaryRange;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class DefaultJobSalaryRange implements JobSalaryRange {
    private final Integer minPay;
    private final Integer maxPay;
    private final String location;
}
