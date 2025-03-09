package com.project.jobapp.services;

import com.project.jobapp.entity.JobPostActivity;
import com.project.jobapp.entity.JobSeekerProfile;
import com.project.jobapp.entity.JobSeekerSave;
import com.project.jobapp.repository.JobSeekerSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    @Autowired
    private JobSeekerSaveRepository jobSeekerSaveRepository;

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
