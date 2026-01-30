package com.example.hiring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @PostMapping("/apply")
    public Application apply(@RequestBody Application application) {
        return applicationRepository.save(application);
    }

    @GetMapping("/applications")
    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }
}
