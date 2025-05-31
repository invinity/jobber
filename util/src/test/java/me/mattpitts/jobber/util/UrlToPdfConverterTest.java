package me.mattpitts.jobber.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the UrlToPdfConverter class.
 */
class UrlToPdfConverterTest {

    @TempDir
    Path tempDir;

    @Test
    void convert_should_convert_a_real_url_to_pdf() throws Exception {
        UrlToPdfConverter converter = new UrlToPdfConverter();
        URL testUrl = URI.create("https://explore.jobs.netflix.net/careers/job/790301668836?utm_source=LinkedIn&domain=netflix.com").toURL();

        byte[] pdfData = converter.convert(testUrl);

        assertNotNull(pdfData, "PDF data should not be null");
        assertTrue(pdfData.length > 0, "PDF data should not be empty");

        // Optionally, write the PDF to a temp file for manual inspection
        Path tempPdf = tempDir.resolve("output.pdf");
        Files.write(tempPdf, pdfData);
        assertTrue(Files.exists(tempPdf), "PDF file should be created");
    }

    @Test
    void convert_should_throw_exception_for_invalid_url() {
        UrlToPdfConverter converter = new UrlToPdfConverter();
        assertThrows(IOException.class, () -> {
            URL invalidUrl = new URL("invalid-url");
            converter.convert(invalidUrl);
        }, "Should throw IOException for invalid URL");
    }

    @Test
    void convert_should_throw_a_null_pointer_exception_for_null_url() {
        UrlToPdfConverter converter = new UrlToPdfConverter();
        assertThrows(NullPointerException.class, () -> {
            converter.convert(null);
        }, "Should throw NullPointerException for null URL");
    }
}