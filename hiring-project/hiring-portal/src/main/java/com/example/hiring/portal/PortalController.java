package com.example.hiring.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortalController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("jobs", jobService.getJobs());
        return "index";
    }

    @GetMapping("/apply/{title}")
    public String applyForm(@PathVariable String title, Model model) {
        Application application = new Application();
        application.setJobTitle(title);
        model.addAttribute("application", application);
        model.addAttribute("jobTitle", title);
        return "apply";
    }

    @PostMapping("/apply")
    public String apply(@ModelAttribute Application application) {
        application.setStatus("APPLIED");
        jobService.apply(application);
        return "redirect:/?success";
    }

    @GetMapping("/hr/dashboard")
    public String hrDashboard(Model model) {
        model.addAttribute("applications", jobService.fetchApplications());
        return "hr-dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
