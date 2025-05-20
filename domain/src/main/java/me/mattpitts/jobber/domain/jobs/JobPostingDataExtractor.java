package me.mattpitts.jobber.domain.jobs;

/**
 * Interface for extracting job posting data from a byte array representing the job posting as a document.
 * <p>
 * Implementations of this interface should provide the logic to parse the byte array and extract relevant job posting information.
 * </p>
 * <p>
 * Primarily this is targetted for AI-based document summarization.
 * </p>
 */
public interface JobPostingDataExtractor<T extends JobPostingData> {
    T extract(byte[] data) throws Exception;
}
