package me.mattpitts.jobber.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class UrlToPdfConverter {
    public byte[] convert(URL url) throws IOException, InterruptedException {
        Path tempFile = Files.createTempFile("jobber-print-to-pdf", ".pdf");
        String chromePath = ChromePathFinder.findChromePath().orElseThrow(() -> new IllegalStateException("Unable to find chrome executable"));
        ProcessBuilder processBuilder = new ProcessBuilder(chromePath, "--headless", "--run-all-compositor-stages-before-draw", "--print-to-pdf=" + tempFile.toString(), url.toString());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
        byte[]  fileData = Files.readAllBytes(tempFile);
        Files.delete(tempFile);
        return fileData;
    }

}
