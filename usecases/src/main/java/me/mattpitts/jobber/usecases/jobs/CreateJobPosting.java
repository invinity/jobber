package me.mattpitts.jobber.usecases.jobs;

import java.util.function.BiFunction;
import me.mattpitts.jobber.domain.jobs.JobPosting;
import me.mattpitts.jobber.domain.jobs.JobPostingData;

@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor
public class CreateJobPosting<T extends JobPosting, D extends JobPostingData> {
    private final ConvertUrlToDocument convertUrlToDocument;
    private final ExtractJobData<D> extractJobData;
    private final StoreJobPosting<T> storeJobPosting;
    private final BiFunction<String, D, T> createJobPosting;

    public T execute(String url) throws Exception {
        byte[] document = convertUrlToDocument.execute(url);
        D jobPostingData = extractJobData.execute(document);
        T posting = createJobPosting.apply(url, jobPostingData);
        return storeJobPosting.execute(posting);
    }
}
