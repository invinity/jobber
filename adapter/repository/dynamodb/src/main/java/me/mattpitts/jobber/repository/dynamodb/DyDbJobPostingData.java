package me.mattpitts.jobber.repository.dynamodb;

import java.util.List;
import me.mattpitts.jobber.domain.jobs.JobPostingData;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@lombok.Builder(builderClassName = "Builder")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DyDbJobPostingData implements JobPostingData {
    private String companyName;
    private String title;
    private String summary;
    private List<String> requiredQualifications;
    private List<String> desiredQualifications;
    private List<String> responsibilities;
    private List<String> locations;
    private String description;
    private String datePosted;
    private String jobType;
    private List<DyDbJobSalaryRange> salaryRanges;
    private String applicationUrl;
}
