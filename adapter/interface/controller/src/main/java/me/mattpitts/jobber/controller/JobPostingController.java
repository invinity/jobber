package me.mattpitts.jobber.controller;

import java.util.List;
import java.util.Optional;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.usecases.jobs.ConvertUrlToDocument;
import me.mattpitts.jobber.usecases.jobs.CreateJobPosting;
import me.mattpitts.jobber.usecases.jobs.ExtractJobData;
import me.mattpitts.jobber.usecases.jobs.ListJobPostings;
import me.mattpitts.jobber.usecases.jobs.LookupJobPosting;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class JobPostingController<T extends JobPosting, D extends JobPostingData> {
    private final ConvertUrlToDocument convertUrlToDocument;
    private final ExtractJobData<D> extractJobData;
    private final CreateJobPosting<T, D> createJobPosting;
    private final ListJobPostings<T> listJobPostings;
    private final LookupJobPosting<T> lookupJobPosting;
    
    public List<T> findAll() {
       return listJobPostings.execute();
    }

    public Optional<T> findById(String id) {
        return lookupJobPosting.execute(id);
    }

    public T create(String url) throws Exception {
        return createJobPosting.execute(url);
    }
}
