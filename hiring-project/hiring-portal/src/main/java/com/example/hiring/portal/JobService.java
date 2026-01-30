package com.example.hiring.portal;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Job> getJobs() {
        String url = "http://localhost:8081/api/jobs";
        Job[] jobs = restTemplate.getForObject(url, Job[].class);
        if (jobs == null) {
            return List.of();
        }
        return Arrays.asList(jobs);
    }

    public void apply(Application application) {
        String url = "http://localhost:8081/api/jobs/apply";
        restTemplate.postForObject(url, application, Application.class);
    }

    public List<Application> fetchApplications() {
        String url = "http://localhost:8081/api/jobs/applications";
        Application[] applications = restTemplate.getForObject(url, Application[].class);
        if (applications == null) {
            return List.of();
        }
        return Arrays.asList(applications);
    }
}
