package com.project.jobapp.controller;

import com.project.jobapp.entity.*;
import com.project.jobapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {

    @Autowired
    private JobPostActivityService jobPostActivityService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JobSeekerApplyService jobSeekerApplyService;

    @Autowired
    private JobSeekerSaveService jobSeekerSaveService;

    @Autowired
    private RecruiterProfileService recruiterProfileService;

    @Autowired
    private JobSeekerProfileService jobSeekerProfileService;

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileService.getCurrentRecruiterProfile();
                if (recruiterProfile != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            }
                else{
                    JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.getCurrentSeekerProfile();
                    if(jobSeekerProfile != null){
                        boolean exist = false;
                        boolean saved = false;

                        for(JobSeekerApply jobSeekerApply : jobSeekerApplyList){
                            if(jobSeekerApply.getUserId().getUserAccountId() == jobSeekerProfile.getUserAccountId()){
                                exist = true;
                                break;
                            }
                        }
                        for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
                            if(jobSeekerSave.getUserId().getUserAccountId() == jobSeekerProfile.getUserAccountId()){
                                saved = true;
                                break;
                            }
                        }

                        model.addAttribute("alreadyApplied", exist);
                        model.addAttribute("alreadySaved", saved);

                }
            }
        }


        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "job-details";
    }

    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersService.findByEmail(currentUsername);

            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(users.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if (jobSeekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setUserId(jobSeekerProfile.get());
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found...");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }
        return "redirect:/dashboard/";
    }
}
