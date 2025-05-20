package me.mattpitts.jobber.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class JobberSpringAppTest {

    @Test
    void contextLoads() {
        // This test simply checks if the application context loads successfully.
        // If the context fails to load, Spring Boot will throw an exception, and the test will fail.
        // If the context loads without exceptions, the test will pass.
        assertDoesNotThrow(() -> {
            // No code needed here, the assertion is that no exception is thrown.
        });
    }
}