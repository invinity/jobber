package me.mattpitts.jobber.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ChromePathFinder {

    private ChromePathFinder() {
        // Prevent instantiation
    }

    public static String findChromePath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return findWindowsChromePath();
        } else if (os.contains("linux")) {
            return findLinuxChromePath();
        } else {
            return null;
        }
    }

    private static String findWindowsChromePath() {
        String[] paths = {
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
            System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\Application\\chrome.exe"
        };

        for (String path : paths) {
            if (checkPath(path)) {
                return path;
            }
        }
        return null;
    }

    private static String findLinuxChromePath() {
        String[] paths = {
            "/usr/bin/google-chrome",
            "/usr/bin/chromium-browser",
            "/opt/google/chrome/google-chrome"
        };

        for (String path : paths) {
           if (checkPath(path)) {
                return path;
            }
        }
         return null;
    }

    private static boolean checkPath(String path) {
        Path p = Paths.get(path);
        return Files.exists(p) && Files.isExecutable(p);
    }
}
