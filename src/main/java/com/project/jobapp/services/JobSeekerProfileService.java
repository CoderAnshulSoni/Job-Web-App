package com.project.jobapp.services;

import com.project.jobapp.entity.JobSeekerProfile;
import com.project.jobapp.entity.RecruiterProfile;
import com.project.jobapp.entity.Users;
import com.project.jobapp.repository.JobSeekerProfileRepository;
import com.project.jobapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Optional<JobSeekerProfile> getOne(Integer id){
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public JobSeekerProfile getCurrentSeekerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("could not found the user..."));

            Optional<JobSeekerProfile> jobSeekerProfile = getOne(users.getUserId());
            return jobSeekerProfile.orElse(null);
        }
        else{
            return null;
        }
    }
}
