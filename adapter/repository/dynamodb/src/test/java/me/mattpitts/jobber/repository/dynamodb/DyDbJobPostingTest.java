package me.mattpitts.jobber.repository.dynamodb;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class DyDbJobPostingTest {

    @Test
    @DisplayName("builder based construction should set field values")
    void builder_based_construction_should_set_field_values() {
        // Arrange
        String key = "testKey";
        String url = "http://example.com/job";
        DyDbJobPostingData data =
                DyDbJobPostingData.builder().companyName("Test Company").title("Test Job").build();

        // Act
        DyDbJobPosting jobPosting = DyDbJobPosting.builder().key(key).url(url).data(data).build();

        // Assert
        assertThat(jobPosting.getKey(), is(key));
        assertThat(jobPosting.getUrl(), is(url));
        assertThat(jobPosting.getData(), is(data));
    }

    @Test
    @DisplayName("default constructor should set field values to null")
    void default_constructor_should_set_field_values_to_null() {
        // Act
        DyDbJobPosting jobPosting = new DyDbJobPosting();

        // Assert
        assertNull(jobPosting.getKey());
        assertNull(jobPosting.getUrl());
        assertNull(jobPosting.getData());
    }
}
