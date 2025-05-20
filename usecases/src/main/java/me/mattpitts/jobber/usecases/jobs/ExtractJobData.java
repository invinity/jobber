package me.mattpitts.jobber.usecases.jobs;

import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.domain.jobs.JobPostingDataExtractor;

@lombok.Builder(builderClassName = "Builder")
public class ExtractJobData<T extends JobPostingData> {
    private final JobPostingDataExtractor<T> jobDataExtractor;

    T execute(byte[] jobDocument) throws Exception{
        return jobDataExtractor.extract(jobDocument);
    }
}
