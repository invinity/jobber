package me.mattpitts.jobber.pipeline.jobdataextractor.gcp;

import com.google.cloud.documentai.v1.Document;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.DocumentProcessorServiceSettings;
import com.google.cloud.documentai.v1.ProcessRequest;
import com.google.cloud.documentai.v1.ProcessResponse;
import com.google.cloud.documentai.v1.ProcessorName;
import com.google.cloud.documentai.v1.RawDocument;
import com.google.protobuf.ByteString;
import java.io.IOException;
import me.mattpitts.jobber.domain.jobs.JobPostingData;
import me.mattpitts.jobber.domain.jobs.JobPostingDataExtractor;
import me.mattpitts.jobber.domain.jobs.impl.DefaultJobPostingData;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class GcpJobDataExtractor<T extends JobPostingData> implements JobPostingDataExtractor<T> {

    private final String projectId;
    private final String locationId; // e.g., "us" or "eu"
    private final String processorId;

    // The endpoint format for Document AI, e.g., "us-documentai.googleapis.com:443"
    private static final String GCP_ENDPOINT_FORMAT = "%s-documentai.googleapis.com:443";

    @Override
    public T extract(byte[] data) throws IOException {
        // The MIME type of the input data. This is crucial for Document AI.
        // For a robust solution, this should be determined dynamically (e.g., using Apache Tika)
        // or passed as a parameter alongside the byte array.
        // Common types for documents are "application/pdf", "image/jpeg", "image/png",
        // "image/tiff".
        // We'll assume "application/pdf" for this example.
        String mimeType = "application/pdf";

        DocumentProcessorServiceSettings settings = DocumentProcessorServiceSettings.newBuilder()
                .setEndpoint(String.format(GCP_ENDPOINT_FORMAT, locationId)).build();

        try (DocumentProcessorServiceClient client =
                DocumentProcessorServiceClient.create(settings)) {
            ProcessorName processorName = ProcessorName.newBuilder().setProject(projectId)
                    .setLocation(locationId).setProcessor(processorId).build();

            RawDocument rawDocument = RawDocument.newBuilder().setContent(ByteString.copyFrom(data))
                    .setMimeType(mimeType).build();

            ProcessRequest request = ProcessRequest.newBuilder().setName(processorName.toString())
                    .setRawDocument(rawDocument)
                    // Add SkipHumanReview if you don't want to trigger human review.
                    // .setSkipHumanReview(true)
                    .build();

            ProcessResponse result = client.processDocument(request);
            Document document = result.getDocument();

            // Populate JobPostingData from the extracted document content.
            // The DefaultJobPostingData class and its builder are assumed to have methods
            // like .title(String), .company(String), .location(String), .description(String).
            DefaultJobPostingData.Builder jobDataBuilder = DefaultJobPostingData.builder();
            // Map entities to specific fields if your processor extracts them
            mapDocumentToJobData(document, jobDataBuilder);

            @SuppressWarnings("unchecked")
            T jobPostingData = (T) jobDataBuilder.build();
            return jobPostingData;

        } catch (Exception e) {
            // It's good practice to log the error here
            // e.g., log.error("Failed to extract job posting data from document", e);
            throw new IOException(
                    "Failed to extract job posting data using Google Cloud Document AI: "
                            + e.getMessage(),
                    e);
        }
    }

    private void mapDocumentToJobData(Document document,
            DefaultJobPostingData.Builder jobDataBuilder) {
        for (Document.Entity entity : document.getEntitiesList()) {
            // This mapping is highly dependent on your Document AI processor's schema and entity
            // types.
            // Example:
            // if ("job_title".equals(entity.getType())) {
            // jobDataBuilder.title(entity.getMentionText());
            // } else if ("company_name".equals(entity.getType())) {
            // jobDataBuilder.company(entity.getMentionText());
            // } else if ("location".equals(entity.getType())) {
            // jobDataBuilder.location(entity.getMentionText());
            // }
            // Add more entity mappings as needed.
        }
    }
}
