package me.mattpitts.jobber.domain.jobs;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;

public interface JobPostingRepository<T extends JobPosting> {
    @NonNull
    public <S extends T> S save(@NonNull S posting);

    public List<T> findAll();

    @NonNull
    public Optional<T> findById(@NonNull String id);
}
