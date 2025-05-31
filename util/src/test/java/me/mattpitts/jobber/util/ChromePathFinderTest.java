package me.mattpitts.jobber.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChromePathFinderTest {

    @Test
    void findChromePath_should_return_a_valid_window_path_if_found() throws IOException {
        try (var mockFiles = mockStatic(Files.class)) {
            Path fakePath = Paths.get("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            mockFiles.when(() -> Files.exists(fakePath)).thenReturn(true);
            mockFiles.when(() -> Files.isExecutable(fakePath)).thenReturn(true);
            Optional<String> path = ChromePathFinder.findChromePath();
            assertThat(path,
                    is(Optional.of("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe")));
        }
    }

    @Test
    void findChromePath_should_return_a_valid_linux_path_if_found() throws IOException {
        try (var mockFiles = mockStatic(Files.class)) {
            Path fakePath = Paths.get("/usr/bin/google-chrome");
            mockFiles.when(() -> Files.exists(fakePath)).thenReturn(true);
            mockFiles.when(() -> Files.isExecutable(fakePath)).thenReturn(true);
            Optional<String> path = ChromePathFinder.findChromePath();
            assertThat(path, is(Optional.of("/usr/bin/google-chrome")));
        }
    }

    @Test
    void findChromePath_should_return_empty_if_not_found() throws IOException {
        try (var mockFiles = mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            mockFiles.when(() -> Files.isExecutable(any(Path.class))).thenReturn(false);

            Optional<String> path = ChromePathFinder.findChromePath();
            assertThat(path, is(Optional.empty()));
        }
    }
}
