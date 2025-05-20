package me.mattpitts.jobber.usecases.jobs;

import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;

@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor
public class StoreJobPosting<T extends JobPosting> {
    private final JobPostingRepository<T> jobRepository;

    public T execute(T jobPosting) {
        return jobRepository.save(jobPosting);   
    }
}
