package me.mattpitts.jobber.pipeline.jobdataextractor.gcp;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPostingData;

class GcpJobDataExtractorTest {

    @Test
    void testExtract_success() throws Exception {
        // Arrange
        GcpJobDataExtractor<DefaultJobPostingData> underTest = GcpJobDataExtractor.<DefaultJobPostingData>builder().build();
        byte[] testData = "This is some test data representing a job posting".getBytes();

        // Act
        DefaultJobPostingData extractedData = underTest.extract(testData);

        // Assert
        // Since the current implementation returns an empty DefaultJobPostingData, we'll check for that.
        //  You'll need to update these assertions once you implement the actual extraction logic.
        assertThat(extractedData, is(notNullValue()));
    }
}