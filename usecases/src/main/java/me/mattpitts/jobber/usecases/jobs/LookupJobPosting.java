package me.mattpitts.jobber.usecases.jobs;

import java.util.Optional;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;

@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor
public class LookupJobPosting<T extends JobPosting> {
    private final JobPostingRepository<T> jobRepository;

    public Optional<T> execute(String id) {
        return jobRepository.findById(id);
    }
}
