package me.mattpitts.jobber.application.config;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.function.BiFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import io.awspring.cloud.dynamodb.DynamoDbOperations;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import me.mattpitts.jobber.controller.JobPostingController;
import me.mattpitts.jobber.domain.jobs.JobPostingDataExtractor;
import me.mattpitts.jobber.domain.jobs.JobPostingRepository;
import me.mattpitts.jobber.repository.dynamodb.DyDbJobPosting;
import me.mattpitts.jobber.repository.dynamodb.DyDbJobPostingData;
import me.mattpitts.jobber.repository.dynamodb.DyDbJobPostingRepository;
import me.mattpitts.jobber.repository.dynamodb.SimpleDynamoDbCrudRepository;
import me.mattpitts.jobber.usecases.jobs.ConvertUrlToDocument;
import me.mattpitts.jobber.usecases.jobs.CreateJobPosting;
import me.mattpitts.jobber.usecases.jobs.ExtractJobData;
import me.mattpitts.jobber.usecases.jobs.ListJobPostings;
import me.mattpitts.jobber.usecases.jobs.StoreJobPosting;
import me.mattpitts.jobber.util.UrlToPdfConverter;

@Configuration
public class DynamoDbJobPostingWirings {
    @Bean
    public JobPostingController<DyDbJobPosting, DyDbJobPostingData> jobPostingController(
            ConvertUrlToDocument convertUrlToDocument,
            ExtractJobData<DyDbJobPostingData> extractJobData,
            CreateJobPosting<DyDbJobPosting, DyDbJobPostingData> createJobPosting,
            ListJobPostings<DyDbJobPosting> listJobPostings) {
        return JobPostingController.<DyDbJobPosting, DyDbJobPostingData>builder()
                .convertUrlToDocument(convertUrlToDocument).extractJobData(extractJobData)
                .createJobPosting(createJobPosting).listJobPostings(listJobPostings).build();
    }

    @Bean
    public ConvertUrlToDocument convertUrlToDocument() {
        return ConvertUrlToDocument.builder().urlToPdfConverter(new UrlToPdfConverter()).build();
    }

    @Bean
    public ExtractJobData<DyDbJobPostingData> extractJobData(
            JobPostingDataExtractor<DyDbJobPostingData> jobPostingDataExtractor) {
        return ExtractJobData.<DyDbJobPostingData>builder()
                .jobDataExtractor(jobPostingDataExtractor).build();
    }

    @Bean
    public StoreJobPosting<DyDbJobPosting> storeJobPosting(
            JobPostingRepository<DyDbJobPosting> jobRepository) {
        return StoreJobPosting.<DyDbJobPosting>builder().jobRepository(jobRepository).build();
    }

    @Bean
    public ListJobPostings<DyDbJobPosting> listJobPostings(
            JobPostingRepository<DyDbJobPosting> jobRepository) {
        return ListJobPostings.<DyDbJobPosting>builder().jobRepository(jobRepository).build();
    }

    @Bean
    public CreateJobPosting<DyDbJobPosting, DyDbJobPostingData> createJobPosting(
            ConvertUrlToDocument convertUrlToDocument,
            ExtractJobData<DyDbJobPostingData> extractJobData,
            StoreJobPosting<DyDbJobPosting> storeJobPosting,
            BiFunction<String, DyDbJobPostingData, DyDbJobPosting> createJobPosting) {
        return CreateJobPosting.<DyDbJobPosting, DyDbJobPostingData>builder()
                .convertUrlToDocument(convertUrlToDocument).extractJobData(extractJobData)
                .storeJobPosting(storeJobPosting).createJobPosting(createJobPosting).build();
    }

    @Bean
    public BiFunction<String, DyDbJobPostingData, DyDbJobPosting> createJobPosting() {
        return (url, data) -> DyDbJobPosting.builder()
                .key(Base64.getEncoder().encodeToString(url.getBytes(Charset.defaultCharset())))
                .url(url).data(data).build();
    }

    @Bean
    public JobPostingDataExtractor<DyDbJobPostingData> jobPostingDataExtractor() {
        return (byte[] data) -> DyDbJobPostingData.builder().build();
    }

    @Bean
    public CrudRepository<DyDbJobPosting, String> dynamoDbCrudRepository(
            DynamoDbOperations dynamoDbTemplate) {
        return new SimpleDynamoDbCrudRepository<>(dynamoDbTemplate, DyDbJobPosting.class);
    }

    @Bean
    public JobPostingRepository<DyDbJobPosting> jobPostingRepository(
            CrudRepository<DyDbJobPosting, String> repository) {
        return DyDbJobPostingRepository.builder().crudRepository(repository).build();
    }

    @Bean
    public DynamoDbTableNameResolver tableNameResolver() {
        return new DynamoDbTableNameResolver() {
            @Override
            public <T> String resolve(Class<T> entityClass) {
                if (entityClass.equals(DyDbJobPosting.class)) {
                    return "jobber_job_postings";
                }
                return null;
            }
        };
    }
}
