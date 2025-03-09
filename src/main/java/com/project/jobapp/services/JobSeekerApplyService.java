package com.project.jobapp.services;

import com.project.jobapp.entity.JobPostActivity;
import com.project.jobapp.entity.JobSeekerApply;
import com.project.jobapp.entity.JobSeekerProfile;
import com.project.jobapp.repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {

    @Autowired
    private JobSeekerApplyRepository jobSeekerApplyRepository;

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity jobs){
        return jobSeekerApplyRepository.findByJob(jobs);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
