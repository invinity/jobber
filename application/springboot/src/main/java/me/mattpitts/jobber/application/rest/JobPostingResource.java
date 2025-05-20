package me.mattpitts.jobber.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.mattpitts.jobber.controller.JobPostingController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/job-postings")
@lombok.Builder(builderClassName = "Builder")
@lombok.RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobPostingResource {
    private final JobPostingController<?, ?> jobPostingController;
    
    @GetMapping
    public ResponseEntity<Object> search() {
        return ResponseEntity.ok(jobPostingController.findAll());
    }
    /**
     * Creates a new job posting.
     *
     * @param url the URL of the job posting to create
     * @return a ResponseEntity containing the created job posting
     * @throws Exception if an error occurs during the creation process
     */
    @PostMapping
    public ResponseEntity<Object> createJobPosting(@RequestParam("url") String url) throws Exception {
       return ResponseEntity.ok(jobPostingController.create(url));
    }
}
