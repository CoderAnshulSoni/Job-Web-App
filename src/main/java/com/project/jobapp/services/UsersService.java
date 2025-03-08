package com.project.jobapp.services;

import com.project.jobapp.entity.JobSeekerProfile;
import com.project.jobapp.entity.RecruiterProfile;
import com.project.jobapp.entity.Users;
import com.project.jobapp.repository.JobSeekerProfileRepository;
import com.project.jobapp.repository.RecruiterProfileRepository;
import com.project.jobapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    public Users addNew(Users user){
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepo.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if(userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users users = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Could not found user.."));
            int userID = users.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                return recruiterProfileRepository.findById(userID).orElse(new RecruiterProfile());
            }
            else{
                return jobSeekerProfileRepository.findById(userID).orElse(new JobSeekerProfile());
            }
        }

        return null;
    }

    public Users getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){

            String username = authentication.getName();
            Users users = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Could not find the user.."));

            return users;
        }

        return null;
    }
}
