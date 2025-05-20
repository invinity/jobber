package me.mattpitts.jobber.repository.dynamodb;

import me.mattpitts.jobber.domain.jobs.JobPosting;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@lombok.Builder(builderClassName = "Builder")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DyDbJobPosting implements JobPosting {
    private String key;
    private String url;
    private DyDbJobPostingData data;

    @DynamoDbPartitionKey
    public String getKey() {
        return this.key;
    }
}
