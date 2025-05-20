package me.mattpitts.jobber.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChromePathFinderTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void findChromePath_windows_returnsPathIfFound() throws IOException {
        try (var mockedStatic = mockStatic(Files.class)) {
            Path fakePath = Paths.get("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            mockedStatic.when(() -> Files.exists(fakePath)).thenReturn(true);
            mockedStatic.when(() -> Files.isExecutable(fakePath)).thenReturn(true);

            String path = ChromePathFinder.findChromePath();
            assertEquals("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", path);
        }
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void findChromePath_linux_returnsPathIfFound() throws IOException {
        try (var mockedStatic = mockStatic(Files.class)) {
            Path fakePath = Paths.get("/usr/bin/google-chrome");
            mockedStatic.when(() -> Files.exists(fakePath)).thenReturn(true);
            mockedStatic.when(() -> Files.isExecutable(fakePath)).thenReturn(true);

            String path = ChromePathFinder.findChromePath();
            assertEquals("/usr/bin/google-chrome", path);
        }
    }

    @Test
    void findChromePath_returnsNullIfNotFound() throws IOException {
        try (var mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            mockedStatic.when(() -> Files.isExecutable(any(Path.class))).thenReturn(false);

            String path = ChromePathFinder.findChromePath();
            assertNull(path);
        }
    }
}