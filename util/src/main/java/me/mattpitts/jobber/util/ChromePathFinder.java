package me.mattpitts.jobber.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public final class ChromePathFinder {

    public static final List<String> SEARCH_PATHS =
            List.of(
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
                System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\Application\\chrome.exe",
                "/usr/bin/google-chrome",
                "/usr/bin/chromium-browser", 
                "/opt/google/chrome/google-chrome");

    private ChromePathFinder() {
        // Prevent instantiation
    }

    public static Optional<String> findChromePath() {
        return SEARCH_PATHS.stream().filter(ChromePathFinder::checkPath).findFirst();
    }

    private static boolean checkPath(String path) {
        Path p = Paths.get(path);
        return Files.exists(p) && Files.isExecutable(p);
    }
}
