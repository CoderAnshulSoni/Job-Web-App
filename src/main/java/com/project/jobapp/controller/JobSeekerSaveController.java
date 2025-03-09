package com.project.jobapp.controller;

import com.project.jobapp.entity.JobPostActivity;
import com.project.jobapp.entity.JobSeekerProfile;
import com.project.jobapp.entity.JobSeekerSave;
import com.project.jobapp.entity.Users;
import com.project.jobapp.services.JobPostActivityService;
import com.project.jobapp.services.JobSeekerProfileService;
import com.project.jobapp.services.JobSeekerSaveService;
import com.project.jobapp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.preprocessor.IPreProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JobSeekerProfileService jobSeekerProfileService;

    @Autowired
    private JobPostActivityService jobPostActivityService;

    @Autowired
    private JobSeekerSaveService jobSeekerSaveService;

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, JobSeekerSave jobSeekerSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersService.findByEmail(currentUsername);

            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(users.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if(jobSeekerProfile.isPresent() && jobPostActivity != null){
                jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(jobSeekerProfile.get());
            }
            else{
                throw new RuntimeException("User not found...");
            }

            jobSeekerSaveService.addNew(jobSeekerSave);
        }

        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model){

        List<JobPostActivity> jobPostActivities = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
            jobPostActivities.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost", jobPostActivities);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}
