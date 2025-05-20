package me.mattpitts.jobber.domain.jobs;

import lombok.NonNull;
import java.util.List;

public interface JobPostingRepository<T extends JobPosting> {
    @NonNull
    public <S extends T> S save(@NonNull S posting);

    public List<T> findAll();
}
