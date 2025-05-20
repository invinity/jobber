package me.mattpitts.jobber.repository.dynamodb;

import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.data.repository.CrudRepository;
import lombok.NonNull;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;

@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor
@lombok.Data
public class DyDbJobPostingRepository implements JobPostingRepository<DyDbJobPosting> {
    private final CrudRepository<DyDbJobPosting, String> crudRepository;

    @Override
    public <S extends DyDbJobPosting> @NonNull S save(@NonNull S posting) {
        return this.crudRepository.save(posting);
    }

    @Override
    public List<DyDbJobPosting> findAll() {
        return StreamSupport.stream(crudRepository.findAll().spliterator(), false)
                .toList();
    }

    
}
