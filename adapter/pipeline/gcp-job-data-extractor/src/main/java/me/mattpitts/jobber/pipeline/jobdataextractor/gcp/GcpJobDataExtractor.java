package me.mattpitts.jobber.pipeline.jobdataextractor.gcp;

import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.domain.jobs.JobPostingDataExtractor;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPostingData;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class GcpJobDataExtractor<T extends JobPostingData> implements JobPostingDataExtractor<T> {
    @Override
    public T extract(byte[] data) throws Exception {
        return (T) DefaultJobPostingData.builder().build();
    }
}
