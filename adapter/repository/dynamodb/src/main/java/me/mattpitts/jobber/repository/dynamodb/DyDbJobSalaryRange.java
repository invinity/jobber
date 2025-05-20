package me.mattpitts.jobber.repository.dynamodb;

import me.mattpitts.jobber.domain.jobs.JobSalaryRange;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@lombok.Builder(builderClassName = "Builder")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DyDbJobSalaryRange implements JobSalaryRange {
    private Integer minPay;
    private Integer maxPay;
    private String location;
}
