package me.mattpitts.jobber.domain.jobs.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import me.mattpitts.jobber.domain.jobs.JobSalaryRange;

class DefaultJobPostingDataTest {

    @Test
    void testGetCompanyName() {
        String companyName = "Acme Corp";
        DefaultJobPostingData data =
                DefaultJobPostingData.builder().companyName(companyName).build();
        assertThat(data.getCompanyName(), is(companyName));
    }

    @Test
    void testGetTitle() {
        String title = "Software Engineer";
        DefaultJobPostingData data = DefaultJobPostingData.builder().title(title).build();
        assertThat(data.getTitle(), is(title));
    }

    @Test
    void testGetSummary() {
        String summary = "Exciting opportunity...";
        DefaultJobPostingData data = DefaultJobPostingData.builder().summary(summary).build();
        assertThat(data.getSummary(), is(summary));
    }

    @Test
    void testGetLocations() {
        List<String> locations = Arrays.asList("New York", "London");
        DefaultJobPostingData data = DefaultJobPostingData.builder().locations(locations).build();
        assertThat(data.getLocations(), is(locations));
    }

    @Test
    void testGetRequiredQualifications() {
        List<String> qualifications = Arrays.asList("Java", "Spring");
        DefaultJobPostingData data =
                DefaultJobPostingData.builder().requiredQualifications(qualifications).build();
        assertThat(data.getRequiredQualifications(), is(qualifications));
    }

    @Test
    void testGetDesiredQualifications() {
        List<String> qualifications = Arrays.asList("AWS", "Docker");
        DefaultJobPostingData data =
                DefaultJobPostingData.builder().desiredQualifications(qualifications).build();
        assertThat(data.getDesiredQualifications(), is(qualifications));
    }

    @Test
    void testGetResponsibilities() {
        List<String> responsibilities = Arrays.asList("Develop", "Test");
        DefaultJobPostingData data =
                DefaultJobPostingData.builder().responsibilities(responsibilities).build();
        assertThat(data.getResponsibilities(), is(responsibilities));
    }

    @Test
    void testGetSalaryRanges() {
        List<JobSalaryRange> salaryRanges =
                Arrays.asList(DefaultJobSalaryRange.builder().minPay(50000).maxPay(70000).build(),
                        DefaultJobSalaryRange.builder().minPay(60000).maxPay(80000).build());
        DefaultJobPostingData data =
                DefaultJobPostingData.builder().salaryRanges(salaryRanges).build();
        assertThat(data.getSalaryRanges(), is(salaryRanges));
    }

    @Test
    void testBuilderWithAllFields() {
        List<String> locations = Arrays.asList("New York", "London");
        List<String> requiredQualifications = Arrays.asList("Java", "Spring");
        List<String> desiredQualifications = Arrays.asList("AWS", "Docker");
        List<String> responsibilities = Arrays.asList("Develop", "Test");
        List<JobSalaryRange> salaryRanges =
                Arrays.asList(DefaultJobSalaryRange.builder().minPay(50000).maxPay(70000).build());

        DefaultJobPostingData data = DefaultJobPostingData.builder().companyName("Acme Corp")
                .title("Software Engineer").summary("Exciting opportunity...").locations(locations)
                .requiredQualifications(requiredQualifications)
                .desiredQualifications(desiredQualifications).responsibilities(responsibilities)
                .salaryRanges(salaryRanges).build();

        assertThat(data.getCompanyName(), is("Acme Corp"));
        assertThat(data.getTitle(), is("Software Engineer"));
        assertThat(data.getSummary(), is("Exciting opportunity..."));
        assertThat(data.getLocations(), is(locations));
        assertThat(data.getRequiredQualifications(), is(requiredQualifications));
        assertThat(data.getDesiredQualifications(), is(desiredQualifications));
        assertThat(data.getResponsibilities(), is(responsibilities));
        assertThat(data.getSalaryRanges(), is(salaryRanges));
    }
}
