package me.mattpitts.jobber.usecases.jobs;

import me.mattpitts.jobber.util.UrlToPdfConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConvertUrlToDocumentTest {

    @Mock
    UrlToPdfConverter mockConverter;

    @Test
    @DisplayName("execute should call UrlToPdfConverter and return PDF bytes")
    void execute_shouldCallUrlToPdfConverterAndReturnPDFBytes() throws Exception {
        // Arrange
        String testUrl = "https://example.com";
        byte[] expectedBytes = new byte[] {1, 2, 3};
        when(mockConverter.convert(any(URL.class))).thenReturn(expectedBytes);

        ConvertUrlToDocument convertUrlToDocument =
                ConvertUrlToDocument.builder().urlToPdfConverter(mockConverter).build();

        // Act
        byte[] result = convertUrlToDocument.execute(testUrl);

        // Assert
        assertArrayEquals(expectedBytes, result);
        verify(mockConverter, times(1)).convert(new URL(testUrl));
    }

    @Test
    @DisplayName("execute should throw Exception if UrlToPdfConverter throws")
    void execute_shouldThrowExceptionIfUrlToPdfConverterThrows() throws Exception {
        // Arrange
        String testUrl = "https://example.com";
        when(mockConverter.convert(any(URL.class)))
                .thenThrow(new RuntimeException("Conversion failed"));

        ConvertUrlToDocument convertUrlToDocument =
                ConvertUrlToDocument.builder().urlToPdfConverter(mockConverter).build();

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            convertUrlToDocument.execute(testUrl);
        });
        assertEquals("Conversion failed", exception.getMessage());
    }

    @Test
    @DisplayName("execute should throw IllegalArgumentException for invalid URL")
    void testExecuteWithInvalidUrl() {
        // Arrange
        ConvertUrlToDocument convertUrlToDocument =
                ConvertUrlToDocument.builder().urlToPdfConverter(mockConverter).build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            convertUrlToDocument.execute("not-a-valid-url");
        });
    }
}
