package me.mattpitts.jobber.usecases.jobs;

import java.util.List;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;

@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor
public class ListJobPostings<T extends JobPosting> {
    private final JobPostingRepository<T> jobRepository;

    public List<T> execute() {
        return jobRepository.findAll();
    }
}