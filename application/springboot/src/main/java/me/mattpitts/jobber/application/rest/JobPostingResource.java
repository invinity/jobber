package me.mattpitts.jobber.application.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.mattpitts.jobber.controller.JobPostingController;

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

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        return jobPostingController.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new job posting.
     *
     * @param url the URL of the job posting to create
     * @return a ResponseEntity containing the created job posting
     * @throws Exception if an error occurs during the creation process
     */
    @PostMapping
    public ResponseEntity<Object> createJobPosting(@RequestParam("url") String url)
            throws Exception {
        var created = jobPostingController.create(url);
        var uri = linkTo(methodOn(JobPostingResource.class).getById(created.getKey())).toUri();
        return ResponseEntity.created(uri).build();
    }
}
